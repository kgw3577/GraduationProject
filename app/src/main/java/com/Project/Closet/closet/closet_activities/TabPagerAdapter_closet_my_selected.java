package com.Project.Closet.closet.closet_activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Project.Closet.util.Utils;

public class TabPagerAdapter_closet_my_selected extends FragmentStatePagerAdapter {

    Fragment fragment;
    int selected_kind;

    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter_closet_my_selected(FragmentManager fm, int tabCount, int selected_kind) {
        super(fm);
        this.tabCount = tabCount;
        this.selected_kind = selected_kind;
    }

    @Override
    public Fragment getItem(int position) {

        String kind = Utils.getKey(Utils.Kind.kindNumMap,selected_kind);
        // Returning the current tabs
        switch (position) {
            case 0:
                switch(selected_kind){
                    case Utils.Kind.TOP :
                    case Utils.Kind.BOTTOM :
                    case Utils.Kind.SUIT :
                    case Utils.Kind.OUTER :
                    case Utils.Kind.SHOES :
                    case Utils.Kind.BAG :
                    case Utils.Kind.ACCESSORY :
                        return TabFragment_Clothes_inClosetShare.newInstance("private",kind,"small");
                    default:
                        return null;
                }
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
