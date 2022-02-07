package com.E2I3.chaebunchaebun;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class QuestionCompleteDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";
    String userId, title, nickname = null;
    int postId = 0;

    public QuestionCompleteDialogFragment() {}
    public static QuestionCompleteDialogFragment getInstance() {
        QuestionCompleteDialogFragment e = new QuestionCompleteDialogFragment();

        return e;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View completDialog = inflater.inflate(R.layout.dialog_home_complete, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton complete = (ImageButton) completDialog.findViewById(R.id.btn_complete);
        ImageButton NotComplete = (ImageButton) completDialog.findViewById(R.id.btn_noComplete);
        TextView completeNickname = (TextView) completDialog.findViewById(R.id.complete_nickname);
        TextView completeTitle = (TextView) completDialog.findViewById(R.id.complete_title);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");
        title = mArgs.getString("title");
        nickname = mArgs.getString("nickname");
        postId = mArgs.getInt("postId");

        completeNickname.setText("어서오세요, " + nickname + "님!");
        completeTitle.setText("벌써 \"" + title + "\" 글이 올라간지 일주일이\n되었네요! 소분이 완료 되었으면 소분 완료\n버튼을 눌러 주세요!");

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("userId", userId);
                args.putString("title", title);
                args.putString("nickname", nickname);
                args.putInt("postId", postId);
                RealCompleteDialogFragment e = RealCompleteDialogFragment.getInstance();
                e.setArguments(args);
                e.show(getChildFragmentManager(), RealCompleteDialogFragment.TAG_EVENT_DIALOG);
                onStop();
            }
        });

        NotComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("userId", userId);
                args.putString("title", title);
                args.putString("nickname", nickname);
                args.putInt("postId", postId);
                UncompleteDialogFragment e = UncompleteDialogFragment.getInstance();
                e.setArguments(args);
                e.show(getChildFragmentManager(), UncompleteDialogFragment.TAG_EVENT_DIALOG);
                onStop();
            }
        });
        setCancelable(false);

        return completDialog;
    }

}
