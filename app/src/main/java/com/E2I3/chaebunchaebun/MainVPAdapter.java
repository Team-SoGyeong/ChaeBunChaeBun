package com.E2I3.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainVPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> itext = new ArrayList<String>();
    private String location = "";

    public MainVPAdapter(@NonNull FragmentManager fm, String locationCode, String userId) {
        super(fm);
        items = new ArrayList<Fragment>();
        MainNewFragment mainNewFragment = new MainNewFragment();
        mainNewFragment.getLocationCode(locationCode, userId);
        items.add(mainNewFragment);

        MainSoonFragment mainSoonFragment = new MainSoonFragment();
        mainSoonFragment.getLocationCode(locationCode, userId);
        items.add(mainSoonFragment);

        MainMyFragment mainMyFragment = new MainMyFragment();
        mainMyFragment.getUserId(userId);
        items.add(mainMyFragment);

        MainWriteFragment mainWriteFragment = new MainWriteFragment();
        mainWriteFragment.getUserId(userId);
        items.add(mainWriteFragment);

        itext.add("신규 채분");
        itext.add("마감 직전 채분");
        itext.add("찜한 채분");
        itext.add("내 채분");
    }

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
