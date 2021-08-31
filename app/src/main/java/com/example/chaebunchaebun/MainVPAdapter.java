package com.example.chaebunchaebun;

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
        mainNewFragment.getLocationCode(locationCode);
        items.add(mainNewFragment);

        MainSoonFragment mainSoonFragment = new MainSoonFragment();
        mainSoonFragment.getLocationCode(locationCode);
        items.add(mainSoonFragment);

        MainMyFragment mainMyFragment = new MainMyFragment();
        mainMyFragment.getUserId(userId);
        items.add(mainMyFragment);
        items.add(new MainWriteFragment());

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
