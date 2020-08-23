package com.Project.Closet.codi;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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
                return TabFragment_Codi_large.newInstance("봄");
            case 2:
                return TabFragment_Codi_large.newInstance("여름");
            case 3:
                return TabFragment_Codi_large.newInstance("가을");
            case 4:
                return TabFragment_Codi_large.newInstance("겨울");
            case 5:
                return TabFragment_Codi_large.newInstance("캐주얼");
            case 6:
                return TabFragment_Codi_large.newInstance("세미포멀");
            case 7:
                return TabFragment_Codi_large.newInstance("포멀");
            case 8:
                return TabFragment_Codi_large.newInstance("특수");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
