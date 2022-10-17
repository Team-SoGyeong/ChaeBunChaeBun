package com.E2I3.chaebunchaebun;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MypageReportDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    private TextView toastText;
    private Toast toast;

    String userId, postId = null;
    RadioButton radioButton;

    public MypageReportDialogFragment() {}
    public static MypageReportDialogFragment getInstance() {
        MypageReportDialogFragment e = new MypageReportDialogFragment();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View deleteDialog = inflater.inflate(R.layout.dialog_mypage_report, container);
        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) deleteDialog.findViewById(R.id.custom_toast_layout));

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton cancel = (ImageButton) deleteDialog.findViewById(R.id.mypage_delete_cancel);
        ImageButton report = (ImageButton) deleteDialog.findViewById(R.id.mypage_report);
        RadioGroup articleReport = (RadioGroup) deleteDialog.findViewById(R.id.mypage_report_radio);
        RadioButton money = (RadioButton) deleteDialog.findViewById(R.id.mypage_money);
        RadioButton noMatch = (RadioButton) deleteDialog.findViewById(R.id.mypage_no_match);
        RadioButton etc = (RadioButton) deleteDialog.findViewById(R.id.mypage_etc);
        EditText reason = (EditText) deleteDialog.findViewById(R.id.mypage_report_reason);

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");
        postId = mArgs.getString("postId");

        articleReport.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton = (RadioButton) deleteDialog.findViewById(i);
                if(radioButton == etc) {
                    reason.setVisibility(View.VISIBLE);
                } else {
                    reason.setVisibility(View.GONE);
                }
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioButton == money){
                    setReport(userId, postId, 1, null);
                } else if(radioButton == noMatch) {
                    setReport(userId, postId, 2, null);
                } else if(radioButton == etc) {
                    String reasonString = reason.getText().toString();
                    if(reasonString.isEmpty()){
                        toastText.setText("사유를 적어주세요!");
                        toast.show();
                    } else {
                        setReport(userId, postId, 3, reasonString);
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        setCancelable(false);

        return deleteDialog;
    }

    private void setReport(String userId, String postId, int reasonId, String reason) {
        PostTask postTask = new PostTask();
        JSONObject jsonPostTransfer = new JSONObject();
        try {
            jsonPostTransfer.put("author_id", Integer.parseInt(userId));
            jsonPostTransfer.put("cmt_id", null);
            jsonPostTransfer.put("post_id", Integer.parseInt(postId));
            jsonPostTransfer.put("reason", reason);
            jsonPostTransfer.put("reason_num", reasonId);

            System.out.println(postId);

            String jsonString = jsonPostTransfer.toString();
            String response = postTask.execute("posts/report", jsonString).get();

            JSONObject jsonObject = new JSONObject(response);
            int responseCode = jsonObject.getInt("code");
            if(responseCode == 200){
                toastText.setText("신고가 접수되었어요!");
                toast.show();
                dismiss();
            } else {
                Toast.makeText(getContext(),"잘못 된 접근입니다.",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
