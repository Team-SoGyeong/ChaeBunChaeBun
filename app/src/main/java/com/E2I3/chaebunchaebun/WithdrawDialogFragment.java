package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class WithdrawDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";
    String userId;

    public WithdrawDialogFragment() {}
    public static WithdrawDialogFragment getInstance() {
        WithdrawDialogFragment e = new WithdrawDialogFragment();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View withdrawDialog = inflater.inflate(R.layout.dialog_withdraw_pop, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton withdraw = (ImageButton) withdrawDialog.findViewById(R.id.btn_withdraw);
        ImageButton cancel = (ImageButton) withdrawDialog.findViewById(R.id.btn_cancel);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PutTask withDrawTask = new PutTask();
                withDrawTask.execute("mypage/profile/" + userId, userId);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setCancelable(false);

        return withdrawDialog;
    }
}
