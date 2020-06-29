package com.Project.Closet.codi;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Project.Closet.fragment.TabFragment_Codi_large;

public class TabPagerAdapter_codi extends FragmentStatePagerAdapter {
    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter_codi(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return new TabFragment_Codi_large("all");
            case 1:
                return new TabFragment_Codi_large("spring");
            case 2:
                return new TabFragment_Codi_large("summer");
            case 3:
                return new TabFragment_Codi_large("fall");
            case 4:
                return new TabFragment_Codi_large("winter");
            case 5:
                return new TabFragment_Codi_large("daily");
            case 6:
                return new TabFragment_Codi_large("formal");
            case 7:
                return new TabFragment_Codi_large("special");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
