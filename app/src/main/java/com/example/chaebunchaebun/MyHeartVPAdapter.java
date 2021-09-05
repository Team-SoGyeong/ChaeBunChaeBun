package com.example.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyHeartVPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> itext = new ArrayList<String>();

    public MyHeartVPAdapter(@NonNull @NotNull FragmentManager fm, String userId) {
        super(fm);
        items = new ArrayList<Fragment>();
        MypageMyHeartFragment mypageMyHeartFragment = new MypageMyHeartFragment();
        mypageMyHeartFragment.getUserId(userId);
        items.add(mypageMyHeartFragment);
        items.add(new MyCommunityHeartFragment());

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
