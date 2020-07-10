package com.Project.Closet.board;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Project.Closet.fragment.TabFragment_Clothes_inCloset;

public class TabPagerAdapter_board extends FragmentStatePagerAdapter {

    Fragment fragment;

    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter_board(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return TabFragment_Clothes_inCloset.newInstance("all","small");
            case 1:
                return TabFragment_Clothes_inCloset.newInstance("top","small");
            case 2:
                return TabFragment_Clothes_inCloset.newInstance("bottom","small");
            case 3:
                return TabFragment_Clothes_inCloset.newInstance("suit","small");
            case 4:
                return TabFragment_Clothes_inCloset.newInstance("outer","small");
            case 5:
                return TabFragment_Clothes_inCloset.newInstance("shoes","small");
            case 6:
                return TabFragment_Clothes_inCloset.newInstance("bag","small");
            case 7:
                return TabFragment_Clothes_inCloset.newInstance("accessory","small");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
