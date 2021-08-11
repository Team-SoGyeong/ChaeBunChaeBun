package com.example.chaebunchaebun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MypageMyHeartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageMyHeartFragment extends Fragment {

    // TODO: Rename and change types and number of parameters
    public static MypageMyHeartFragment newInstance(String param1, String param2) {
        MypageMyHeartFragment fragment = new MypageMyHeartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mypage_myheart, container, false);
        return v;
    }
}