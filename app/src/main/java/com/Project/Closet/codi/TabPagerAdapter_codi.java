package com.Project.Closet.codi;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Project.Closet.subfragment.TabFragment_Codi_large;

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
                return TabFragment_Codi_large.newInstance("share");
            case 1:
                return TabFragment_Codi_large.newInstance("spring");
            case 2:
                return TabFragment_Codi_large.newInstance("summer");
            case 3:
                return TabFragment_Codi_large.newInstance("fall");
            case 4:
                return TabFragment_Codi_large.newInstance("winter");
            case 5:
                return TabFragment_Codi_large.newInstance("casual");
            case 6:
                return TabFragment_Codi_large.newInstance("business");
            case 7:
                return TabFragment_Codi_large.newInstance("formal");
            case 8:
                return TabFragment_Codi_large.newInstance("special");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
