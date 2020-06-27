package com.Project.Closet.codi.addCodi;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    private int MAX_PAGES;
    ArrayList<Fragment> items = new ArrayList<Fragment>();

    public CustomFragmentPagerAdapter(FragmentManager fm, int maxPages) {
        super(fm);
        this.MAX_PAGES = maxPages;
    }

    public void addItem(Fragment item){
        items.add(item);
    }

    @Override
    public Fragment getItem(int position) {

        if(position < 0 || MAX_PAGES <= position) {
            return null;
        }

        return items.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return MAX_PAGES;
    }
}
