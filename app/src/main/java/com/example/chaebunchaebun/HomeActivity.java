package com.example.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity{
    private Integer[] vegetableID = {R.drawable.onion, R.drawable.garlic, R.drawable.green_onion,
            R.drawable.carrot, R.drawable.mushroom, R.drawable.green_vege, R.drawable.cabbage,
            R.drawable.radish, R.drawable.potato, R.drawable.sweet_potato};
    ActionBar.Tab tabNew, tabSoon, tabLast, tabMy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        Gallery main_gallery = (Gallery) findViewById(R.id.main_gallery);
        MyGallaryAdapter galAdapter = new MyGallaryAdapter(this);
        main_gallery.setAdapter(galAdapter);

        ViewPager vp = findViewById(R.id.view_pager);
        MainVPAdapter adapter = new MainVPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(vp);
    }

    public class MyGallaryAdapter extends BaseAdapter {
        Context context;

        public MyGallaryAdapter(Context c) {
            context = c;
        }

        @Override
        public int getCount() {
            return vegetableID.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 122, getResources().getDisplayMetrics());
            final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 194, getResources().getDisplayMetrics());

            final int left = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
            final int top = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
            final int right = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
            final int bottom = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());

            ImageView imageview = new ImageView(context);
            imageview.setLayoutParams(new Gallery.LayoutParams(width, height));
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview.setPadding(left, top, right, bottom);
            imageview.setImageResource(vegetableID[position]);

            return imageview;
        }
    }
}