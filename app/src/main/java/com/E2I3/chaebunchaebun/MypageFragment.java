package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MypageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String userId;
    View mypage_main;
    ImageView mypage_profile;
    TextView mypage_nickname;
    TextView mypage_location;
    LinearLayout mypage_chaebun_record;
    LinearLayout mypage_community_record;
    LinearLayout mypage_inquire;
    LinearLayout mypage_share;


    private String mParam1;
    private String mParam2;

    public MypageFragment() {
        // Required empty public constructor
    }

    public static MypageFragment newInstance(String param1, String param2) {
        MypageFragment fragment = new MypageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void getUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mypage_main = inflater.inflate(R.layout.fragment_mypage, container, false);
        mypage_profile = mypage_main.findViewById(R.id.mypage_profile);
        mypage_nickname = mypage_main.findViewById(R.id.mypage_nickname);
        mypage_location = mypage_main.findViewById(R.id.mypage_location);
        mypage_chaebun_record = mypage_main.findViewById(R.id.mypage_btn_chaebun_record);
        mypage_community_record = mypage_main.findViewById(R.id.mypage_btn_community_record);
        mypage_inquire = mypage_main.findViewById(R.id.mypage_btn_inquire);
        mypage_share = mypage_main.findViewById(R.id.mypage_btn_share);

        //profile, nickname, location 서버에서 얻어서 세팅하기


        mypage_chaebun_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageMypostingActivity.class);
                intent.putExtra("userId", userId);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        mypage_community_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageCommunityPostingActivity.class);
                intent.putExtra("userId", userId);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
           }
        });
        mypage_inquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"dragoncat84@naver.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "[채분채분 문의하기]");
                email.putExtra(Intent.EXTRA_TEXT, "문의할 내용을 적어주세요!");
                startActivity(email);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });

        return mypage_main;
    }
}