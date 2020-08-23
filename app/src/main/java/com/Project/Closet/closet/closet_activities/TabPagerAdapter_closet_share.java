package com.Project.Closet.closet.closet_activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabPagerAdapter_closet_share extends FragmentStatePagerAdapter {

    Fragment fragment;

    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter_closet_share(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return TabFragment_Clothes_inClosetShare.newInstance("public","share","small");
            case 1:
                return TabFragment_Clothes_inClosetShare.newInstance("public","상의","small");
            case 2:
                return TabFragment_Clothes_inClosetShare.newInstance("public","하의","small");
            case 3:
                return TabFragment_Clothes_inClosetShare.newInstance("public","한벌옷","small");
            case 4:
                return TabFragment_Clothes_inClosetShare.newInstance("public","외투","small");
            case 5:
                return TabFragment_Clothes_inClosetShare.newInstance("public","신발","small");
            case 6:
                return TabFragment_Clothes_inClosetShare.newInstance("public","가방","small");
            case 7:
                return TabFragment_Clothes_inClosetShare.newInstance("public","액세서리","small");
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
