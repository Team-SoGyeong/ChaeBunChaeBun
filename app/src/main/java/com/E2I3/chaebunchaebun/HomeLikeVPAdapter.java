package com.E2I3.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class HomeLikeVPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> itext = new ArrayList<String>();

    public HomeLikeVPAdapter(@NonNull FragmentManager fm) {
        super(fm);

        items = new ArrayList<Fragment>();
        items.add(new HomeLikeIngFragment());
        items.add(new HomeLikeFinishFragment());

        itext.add("진행중인 채분");
        itext.add("완료된 채분");
    }

    @NonNull
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
