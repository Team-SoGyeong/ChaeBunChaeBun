package com.E2I3.chaebunchaebun;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class CategoryVPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> categorys;
    private ArrayList<String> categoryTabText = new ArrayList<String>();

    public CategoryVPAdapter(FragmentManager fm, String userId) {
        super(fm);
        categorys = new ArrayList<Fragment>();

        CategoryOnionFragment categoryOnionFragment = new CategoryOnionFragment();
        categoryOnionFragment.getUserId(userId);
        categorys.add(categoryOnionFragment);

        CategoryGarlicFragment categoryGarlicFragment = new CategoryGarlicFragment();
        categoryGarlicFragment.getUserId(userId);
        categorys.add(categoryGarlicFragment);

        CategoryGreenonionFragment categoryGreenonionFragment = new CategoryGreenonionFragment();
        categoryGreenonionFragment.getUserId(userId);
        categorys.add(categoryGreenonionFragment);

        CategoryCarrotFragment categoryCarrotFragment = new CategoryCarrotFragment();
        categoryCarrotFragment.getUserId(userId);
        categorys.add(categoryCarrotFragment);

        CategoryMushroomFragment categoryMushroomFragment = new CategoryMushroomFragment();
        categoryMushroomFragment.getUserId(userId);
        categorys.add(categoryMushroomFragment);

        CategoryGreenvegeFragment categoryGreenvegeFragment = new CategoryGreenvegeFragment();
        categoryGreenvegeFragment.getUserId(userId);
        categorys.add(categoryGreenvegeFragment);

        CategoryCabbageFragment categoryCabbageFragment = new CategoryCabbageFragment();
        categoryCabbageFragment.getUserId(userId);
        categorys.add(categoryCabbageFragment);

        CategoryRadishFragment categoryRadishFragment = new CategoryRadishFragment();
        categoryRadishFragment.getUserId(userId);
        categorys.add(categoryRadishFragment);

        CategoryPotatoFragment categoryPotatoFragment = new CategoryPotatoFragment();
        categoryPotatoFragment.getUserId(userId);
        categorys.add(categoryPotatoFragment);

        CategorySweetpotatoFragment categorySweetpotatoFragment = new CategorySweetpotatoFragment();
        categorySweetpotatoFragment.getUserId(userId);
        categorys.add(categorySweetpotatoFragment);

        CategoryOtherFragment categoryOtherFragment = new CategoryOtherFragment();
        categoryOtherFragment.getUserId(userId);
        categorys.add(categoryOtherFragment);

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
