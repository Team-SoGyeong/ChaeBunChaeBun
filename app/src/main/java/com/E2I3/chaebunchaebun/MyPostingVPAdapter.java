package com.E2I3.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyPostingVPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> itext = new ArrayList<String>();

    public MyPostingVPAdapter(@NonNull @NotNull FragmentManager fm, String userId) {
        super(fm);
        items = new ArrayList<Fragment>();
        MypageMypostingFragment mypageMypostingFragment = new MypageMypostingFragment();
        mypageMypostingFragment.getUserId(userId);
        items.add(mypageMypostingFragment);
        items.add(new MyCommunityPostingFragment());

        itext.add("채분");
        itext.add("댓글");
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
