package com.example.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyCommentVPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> itext = new ArrayList<String>();

    public MyCommentVPAdapter(@NonNull @NotNull FragmentManager fm, String userId) {
        super(fm);
        items = new ArrayList<Fragment>();

        MypageMyCommentFragment mypageMyCommentFragment = new MypageMyCommentFragment();

        items.add(mypageMyCommentFragment);
        mypageMyCommentFragment.getUserId(userId);
        items.add(new MyCommunityCommentFragment());

        itext.add("채분");
        itext.add("커뮤니티");
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public CharSequence getPageTitle(int position){
        return itext.get(position);
    }
}
