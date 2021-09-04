package com.example.chaebunchaebun;

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
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class CommentReportDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    private TextView toastText;
    private Toast toast;

    String userId, commentId, postId = null;
    RadioButton radioButton;

    public CommentReportDialogFragment() {}
    public static CommentReportDialogFragment getInstance() {
        CommentReportDialogFragment e = new CommentReportDialogFragment();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View reportDialog = inflater.inflate(R.layout.dialog_comment_report, container);
        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) reportDialog.findViewById(R.id.custom_toast_layout));
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton ok = (ImageButton) reportDialog.findViewById(R.id.btn_report);
        ImageButton cancel = (ImageButton) reportDialog.findViewById(R.id.btn_report_cancel);
        RadioGroup commentReport = (RadioGroup) reportDialog.findViewById(R.id.comment_report);
        RadioButton money = (RadioButton) reportDialog.findViewById(R.id.comment_money);
        RadioButton noMatch = (RadioButton) reportDialog.findViewById(R.id.comment_no_match);
        RadioButton etc = (RadioButton) reportDialog.findViewById(R.id.comment_etc);
        EditText reason = (EditText) reportDialog.findViewById(R.id.report_reason);

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");
        commentId = mArgs.getString("commentId");
        postId = mArgs.getString("postId");

        commentReport.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton = (RadioButton) commentReport.findViewById(i);
                System.out.println(radioButton);
                if(radioButton == etc) {
                    reason.setVisibility(View.VISIBLE);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioButton == money){
                    setCommentReport(userId, commentId, postId, 1, null);
                } else if(radioButton == noMatch) {
                    setCommentReport(userId, commentId, postId, 2, null);
                } else if(radioButton == etc) {
                    String reasonString = reason.getText().toString();
                    if(reasonString.isEmpty()){
                        toastText.setText("사유를 적어주세요!");
                        toast.show();
                    } else {
                        setCommentReport(userId, commentId, postId, 3, reasonString);
                    }
                }
            }
        });
        setCancelable(false);

        return reportDialog;
    }

    private void setCommentReport(String userId, String commentId, String postId, int reasonId, String reason) {
        PostTask postTask = new PostTask();
        JSONObject jsonPostTransfer = new JSONObject();
        try {
            jsonPostTransfer.put("author_id", Integer.parseInt(userId));
            jsonPostTransfer.put("cmt_id", Integer.parseInt(commentId));
            jsonPostTransfer.put("post_id", Integer.parseInt(postId));
            jsonPostTransfer.put("reason", reason);
            jsonPostTransfer.put("reason_num", reasonId);

            System.out.println(postId);

            String jsonString = jsonPostTransfer.toString();
            String response = postTask.execute("comments/report", jsonString).get();

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
