package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MyCommunityBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    public static MyCommunityBottomSheetDialog getInstance() {
        return new MyCommunityBottomSheetDialog();
    }

    private ImageButton modify;
    private ImageButton delete;

    String userId, postId = null;
    int categoryId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_mycommunity_modalbtn, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        modify = (ImageButton) view.findViewById(R.id.community_modal_modify);
        delete = (ImageButton) view.findViewById(R.id.community_modal_delete);

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
            case R.id.community_modal_modify:
                Intent intent = new Intent(getContext(), CommunityChangeActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("postId", postId);
                getActivity().startActivity(intent);
                break;
            case R.id.community_modal_delete:
                Bundle args = new Bundle();
                args.putString("userId", userId);
                args.putString("postId", postId);
                CommunityDeleteDialogFragment e = CommunityDeleteDialogFragment.getInstance();
                e.setArguments(args);
                e.show(getChildFragmentManager(), CommunityDeleteDialogFragment.TAG_EVENT_DIALOG);
                break;
        }
    }
}
