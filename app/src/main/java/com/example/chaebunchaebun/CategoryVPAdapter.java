package com.example.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryVPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> categorys;
    private ArrayList<String> categoryTabText = new ArrayList<String>();

    public CategoryVPAdapter(FragmentManager fm) {
        super(fm);
        categorys = new ArrayList<Fragment>();
        categorys.add(new CategoryOnionFragment());
        categorys.add(new CategoryGarlicFragment());
        categorys.add(new CategoryGreenonionFragment());
        categorys.add(new CategoryCarrotFragment());
        categorys.add(new CategoryMushroomFragment());
        categorys.add(new CategoryGreenvegeFragment());
        categorys.add(new CategoryCabbageFragment());
        categorys.add(new CategoryRadishFragment());
        categorys.add(new CategoryPotatoFragment());
        categorys.add(new CategorySweetpotatoFragment());
        categorys.add(new CategoryOtherFragment());

        categoryTabText.add("양파");
        categoryTabText.add("마늘");
        categoryTabText.add("파");
        categoryTabText.add("당근");
        categoryTabText.add("버섯");
        categoryTabText.add("쌈채소");
        categoryTabText.add("배추");
        categoryTabText.add("무");
        categoryTabText.add("감자");
        categoryTabText.add("고구마");
        categoryTabText.add("기타");
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoryTabText.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return categorys.get(position);
    }

    @Override
    public int getCount() {
        return categorys.size();
    }
}
