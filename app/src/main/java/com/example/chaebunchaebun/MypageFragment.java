package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MypageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageFragment extends Fragment {
    TextView MyProfile, MyPosting, MyComment, MyHeart, MyQuestion, MyShare;
    String userId = null;
    public MypageFragment() {
        // Required empty public constructor
    }

    public static MypageFragment newInstance(String param1, String param2) {
        MypageFragment fragment = new MypageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void getUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_mypage, null);

        MyProfile = (TextView) view.findViewById(R.id.my_profile);
        MyPosting = (TextView) view.findViewById(R.id.my_story);
        MyComment = (TextView) view.findViewById(R.id.my_comment);
        MyHeart = (TextView) view.findViewById(R.id.my_favorite);
        MyQuestion = (TextView) view.findViewById(R.id.my_question);
        MyShare = (TextView) view.findViewById(R.id.my_share);

        MyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPageProfileActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        MyPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageMypostingActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        MyComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(getActivity(), MypageMycommentActivity.class));
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        MyHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(getActivity(), MypageMyHeartActivity.class));
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        MyQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"dragoncat84@naver.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "[채분채분 문의하기]");
                email.putExtra(Intent.EXTRA_TEXT, "문의할 내용을 적어주세요!");
                startActivity(email);
            }
        });
        return view;
    }

}