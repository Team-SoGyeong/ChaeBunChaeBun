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

public class LocationQuestionDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    public LocationQuestionDialogFragment(){}
    public static LocationQuestionDialogFragment getInstance() {
        LocationQuestionDialogFragment e = new LocationQuestionDialogFragment();

        return e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View questionDialog = inflater.inflate(R.layout.dialog_location_question, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton later = (ImageButton) questionDialog.findViewById(R.id.btn_later);
        ImageButton ok = (ImageButton) questionDialog.findViewById(R.id.btn_user);

        Bundle mArgs = getArguments();

        String user_id = mArgs.getString("user_id");
        String kakao_email = mArgs.getString("kakao_email");
        String profile_img = mArgs.getString("profile_img");
        String gender = mArgs.getString("sex");
        String age_range = mArgs.getString("age_range");

        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("나중에");
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //오류난다면 fragment에서 activity로 데이터 넘기는 쪽 살펴보기
                Intent intent = new Intent(getActivity(), SetProfileActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("kakao_email", kakao_email);
                intent.putExtra("profile_img", profile_img);
                intent.putExtra("sex", gender);
                intent.putExtra("age_range", age_range);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                dismiss();
            }
        });

        setCancelable(false);

        return questionDialog;
    }

}
