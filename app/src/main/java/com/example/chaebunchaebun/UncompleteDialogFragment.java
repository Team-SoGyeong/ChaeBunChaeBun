package com.example.chaebunchaebun;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class UncompleteDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";
    private static final int TAG_INTENT = 300;
    String userId, title, nickname = null;
    int postId = 0;

    public UncompleteDialogFragment() {}
    public static UncompleteDialogFragment getInstance() {
        UncompleteDialogFragment e = new UncompleteDialogFragment();

        return e;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View unCompleteDialog = inflater.inflate(R.layout.dialog_home_uncomplete, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton fin = (ImageButton) unCompleteDialog.findViewById(R.id.btn_fin_now);
        ImageButton donate = (ImageButton) unCompleteDialog.findViewById(R.id.btn_donate);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");
        title = mArgs.getString("title");
        nickname = mArgs.getString("nickname");
        postId = mArgs.getInt("postId");

        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("userId", userId);
                args.putString("title", title);
                args.putString("nickname", nickname);
                args.putInt("postId", postId);
                UncompleteFinDialogFragment e = UncompleteFinDialogFragment.getInstance();
                e.setArguments(args);
                e.show(getChildFragmentManager(), UncompleteFinDialogFragment.TAG_EVENT_DIALOG);
            }
        });
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PutTask completeTask = new PutTask();
                completeTask.execute("common/donated/" + String.valueOf(postId) + "/" + userId, String.valueOf(postId), userId);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.foodbank1377.org/"));
                getActivity().startActivityForResult(intent, TAG_INTENT);
                getActivity().startActivity(new Intent(getActivity(), NavigationActivity.class));
            }
        });
        setCancelable(false);

        return unCompleteDialog;
    }
}
