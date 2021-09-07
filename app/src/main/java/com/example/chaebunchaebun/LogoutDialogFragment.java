package com.example.chaebunchaebun;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class LogoutDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";
    String userId = null;
    protected SessionCallback sessionCallback = new SessionCallback();
    Session session;

    public LogoutDialogFragment() {}
    public static LogoutDialogFragment getInstance() {
        LogoutDialogFragment e = new LogoutDialogFragment();

        return e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View logoutDialog = inflater.inflate(R.layout.profile_logout, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton logout = (ImageButton) logoutDialog.findViewById(R.id.btn_logout);
        ImageButton cancel = (ImageButton) logoutDialog.findViewById(R.id.btn_cancel);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");

        logout.setOnClickListener(v -> {
            Log.d(TAG, "onCreate:click ");
            UserManagement.getInstance()
                    .requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            super.onSessionClosed(errorResult);
                            Log.d(TAG, "onSessionClosed: " + errorResult.getErrorMessage());
                        }

                        @Override
                        public void onCompleteLogout() {
                            if (sessionCallback != null) {
                                Session.getCurrentSession().removeCallback(sessionCallback);

                                PutTask putTask = new PutTask();
                                JSONObject jsonCommentTransfer = new JSONObject();

                                try {
                                    jsonCommentTransfer.put("userId", userId);
                                    String jsonString = jsonCommentTransfer.toString();
                                    putTask.execute("auth2/signout/" + userId, jsonString);
                                    //로그인 화면으로 돌아가기
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    getActivity().startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Log.d(TAG, "onCompleteLogout:logout ");
                            System.out.println("컴플릿로그인 후 logout_userid: " + userId);
                        }
                    });
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setCancelable(false);

        return logoutDialog;
    }
}