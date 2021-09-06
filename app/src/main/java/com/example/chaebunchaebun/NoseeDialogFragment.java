package com.example.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class NoseeDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_nosee";

    String userId, postId = null;
    RadioButton radioButton;

    public NoseeDialogFragment() {}
    public static NoseeDialogFragment getInstance() {
        NoseeDialogFragment e = new NoseeDialogFragment();

        return e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View noseeDialog = inflater.inflate(R.layout.dialog_article_nosee, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button noseeDialogOk = (Button) noseeDialog.findViewById(R.id.location_dialog_ok);
        Button noseeDialogCancel = (Button) noseeDialog.findViewById(R.id.location_dialog_cancel);
        RadioGroup noseeRadioGroup = (RadioGroup) noseeDialog.findViewById(R.id.nosee_radiogroup);
        RadioButton partici = (RadioButton) noseeDialog.findViewById(R.id.partici);
        RadioButton manySee = (RadioButton) noseeDialog.findViewById(R.id.many_see);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");
        postId = mArgs.getString("postId");

        noseeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton = (RadioButton) noseeDialog.findViewById(i);
            }
        });

        noseeDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioButton == partici) {
                    setBlind(userId, postId, 1);
                } else if(radioButton == manySee) {
                    setBlind(userId, postId, 2);
                }
            }
        });

        noseeDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setCancelable(false);

        return noseeDialog;
    }

    private void setBlind(String userId, String postId, int reasonId) {
        PostTask postTask = new PostTask();
        JSONObject jsonPostTransfer = new JSONObject();
        try {
            jsonPostTransfer.put("author_id", Integer.parseInt(userId));
            jsonPostTransfer.put("cmt_id", null);
            jsonPostTransfer.put("post_id", Integer.parseInt(postId));
            jsonPostTransfer.put("reason", null);
            jsonPostTransfer.put("reason_id", reasonId);

            System.out.println(postId);

            String jsonString = jsonPostTransfer.toString();
            String response = postTask.execute("posts/blind", jsonString).get();

            JSONObject jsonObject = new JSONObject(response);
            int responseCode = jsonObject.getInt("code");
            if(responseCode == 200){
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("userId", userId);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
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
