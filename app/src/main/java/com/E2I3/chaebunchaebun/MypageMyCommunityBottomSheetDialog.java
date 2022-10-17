package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MypageMyCommunityBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    public static MypageMyCommunityBottomSheetDialog getInstance() {
        return new MypageMyCommunityBottomSheetDialog();
    }

    private ImageButton modify;
    private ImageButton delete;

    String userId, postId = null;
    boolean isMypage = true;
    int categoryId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_mypagemycommunity_modalbtn, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        modify = (ImageButton) view.findViewById(R.id.mypage_community_modal_modify);
        delete = (ImageButton) view.findViewById(R.id.mypage_community_modal_delete);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");
        postId = mArgs.getString("postId");

        modify.setOnClickListener(this);
        delete.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mypage_community_modal_modify:
                Intent intent = new Intent(getContext(), CommunityChangeActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("postId", postId);
                intent.putExtra("isMypage", isMypage);
                getActivity().startActivity(intent);
                break;
            case R.id.mypage_community_modal_delete:
                Bundle args = new Bundle();
                args.putString("userId", userId);
                args.putString("postId", postId);
                MypageCommunityDeleteDialogFragment e = MypageCommunityDeleteDialogFragment.getInstance();
                e.setArguments(args);
                e.show(getChildFragmentManager(), MypageCommunityDeleteDialogFragment.TAG_EVENT_DIALOG);
                break;
        }
    }
}