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

public class MyBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    public static MyBottomSheetDialog getInstance() {
        return new MyBottomSheetDialog();
    }

    private ImageButton modify;
    private ImageButton delete;

    String userId, postId = null;
    int categoryId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_my_modalbtn, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        modify = (ImageButton) view.findViewById(R.id.list_modal_modify);
        delete = (ImageButton) view.findViewById(R.id.list_modal_delete);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");
        postId = mArgs.getString("postId");
        categoryId = mArgs.getInt("categoryId");

        modify.setOnClickListener(this);
        delete.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.list_modal_modify:
                if(categoryId < 11) {
                    Intent intent = new Intent(getContext(), ChangeChaebunActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("postId", postId);
                    intent.putExtra("categoryId", categoryId);
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), ChangeChaebunEtcActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("postId", postId);
                    intent.putExtra("categoryId", categoryId);
                    getActivity().startActivity(intent);
                }
                Toast.makeText(getContext(),"수정",Toast.LENGTH_SHORT).show();
                break;
            case R.id.list_modal_delete:
                Bundle args = new Bundle();
                args.putString("userId", userId);
                args.putString("postId", postId);
                ArticleDeleteDialogFragment e = ArticleDeleteDialogFragment.getInstance();
                e.setArguments(args);
                e.show(getChildFragmentManager(), ArticleDeleteDialogFragment.TAG_EVENT_DIALOG);
                break;
        }
    }
}
