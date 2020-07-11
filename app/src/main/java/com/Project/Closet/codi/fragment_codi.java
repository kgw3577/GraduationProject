package com.Project.Closet.codi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.Project.Closet.R;
import com.Project.Closet.activity_addClothes;
import com.Project.Closet.closet.activity_closet;
import com.Project.Closet.codi.addCodi.activity_addCodi;
import com.Project.Closet.home.activity_home2;
import com.Project.Closet.util.OnBackPressedListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.ssomai.android.scalablelayout.ScalableLayout;

public class fragment_codi extends Fragment implements OnBackPressedListener {
    private TabLayout tabLayout;
    private ViewPager finalPager;
    RelativeLayout navMenu;
    ViewGroup viewGroup;
    activity_home2 activity;
    Toast toast;
    long backKeyPressedTime;

    public RelativeLayout Cloth_Info;
    public RelativeLayout Cloth_Info_edit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment2,container,false);
        activity = (activity_home2) getActivity();
        toast = Toast.makeText(getContext(),"한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT);
        return viewGroup;
    }

    @Override
    public void onStart() {

        super.onStart();
        activity.setOnBackPressedListener(this);
        Cloth_Info = (RelativeLayout) getView().findViewById(R.id.cloth_info);
        Cloth_Info.setVisibility(View.GONE);
        Cloth_Info_edit = (RelativeLayout) getView().findViewById(R.id.cloth_info_edit);
        Cloth_Info_edit.setVisibility(View.GONE);

        final DrawerLayout drawLayout = (DrawerLayout) getView().findViewById(R.id.final_drawer_layout);
        NavigationView navigationView = (NavigationView) getView().findViewById(R.id.final_nav_view); //드로워 뷰



        //메뉴 버튼 클릭하면 드로워 열고 닫기
        navMenu = getView().findViewById(R.id.header_nav);
        navMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawLayout.isDrawerOpen(GravityCompat.START)) {
                    drawLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        //드로워(메뉴) 아이템 선택
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.menuitem1:
                        Toast.makeText(getContext(), "SelectedItem 1", Toast.LENGTH_SHORT).show();
                    case R.id.menuitem2:
                        Toast.makeText(getContext(), "SelectedItem 2", Toast.LENGTH_SHORT).show();
                    case R.id.menuitem3:
                        Toast.makeText(getContext(), "SelectedItem 3", Toast.LENGTH_SHORT).show();
                }

                DrawerLayout drawer = getView().findViewById(R.id.final_drawer_layout);
                //drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        if(tabLayout == null){
            //탭 목록 설정
            tabLayout = (TabLayout) getView().findViewById(R.id.tabLayout);
            tabLayout.addTab(tabLayout.newTab().setText("모두"));
            tabLayout.addTab(tabLayout.newTab().setText("봄"));
            tabLayout.addTab(tabLayout.newTab().setText("여름"));
            tabLayout.addTab(tabLayout.newTab().setText("가을"));
            tabLayout.addTab(tabLayout.newTab().setText("겨울"));
            tabLayout.addTab(tabLayout.newTab().setText("일상"));
            tabLayout.addTab(tabLayout.newTab().setText("포멀"));
            tabLayout.addTab(tabLayout.newTab().setText("특별"));

            // 탭메뉴 아이콘 설정
            tabLayout.getTabAt(0).setIcon(R.drawable.all2); // 메뉴1
            tabLayout.getTabAt(1).setIcon(R.drawable.spring); // 메뉴2
            tabLayout.getTabAt(2).setIcon(R.drawable.summer); // 메뉴3
            tabLayout.getTabAt(3).setIcon(R.drawable.fall2); // 메뉴4
            tabLayout.getTabAt(4).setIcon(R.drawable.winter4); // 메뉴5
            tabLayout.getTabAt(5).setIcon(R.drawable.daily); // 메뉴6
            tabLayout.getTabAt(6).setIcon(R.drawable.formal); // 메뉴7
            tabLayout.getTabAt(7).setIcon(R.drawable.special4); // 메뉴7

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            //탭 페이저 설정 (탭 클릭시 바뀌는 화면)
            finalPager = (ViewPager) getView().findViewById(R.id.tab_Pager);
            TabPagerAdapter_codi pagerAdapter = new TabPagerAdapter_codi(getChildFragmentManager(), tabLayout.getTabCount());
            finalPager.setAdapter(pagerAdapter);
            finalPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    finalPager.setCurrentItem(tab.getPosition());
                }
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }
                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        }





    }


    @Override
    public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(this);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }


    //뒤로 가기 버튼이 눌렸을 경우 드로워(메뉴)를 닫는다.
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = getView().findViewById(R.id.final_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast.show();
            return;
        } else if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            getActivity().finish();
            toast.cancel();
        }
    }
}
