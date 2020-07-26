package com.Project.Closet.social;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.R;
import com.Project.Closet.codi.TabPagerAdapter_codi;
import com.Project.Closet.home.activity_home;
import com.Project.Closet.util.OnBackPressedListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import static android.app.Activity.RESULT_OK;

public class fragment_share extends Fragment implements OnBackPressedListener {

    ViewGroup viewGroup;
    Toast toast;
    long backKeyPressedTime;

    int ADD_BOARD = 8080;


    Activity activity;

    private TabLayout tabLayout;
    public TabPagerAdapter_share pagerAdapter;
    private ViewPager finalPager;

    RelativeLayout filterButton;
    RelativeLayout addButton;

    DrawerLayout drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.frag_board,container,false);
        toast = Toast.makeText(getContext(),"한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT);
        return viewGroup;
    }

    //액티비티에 재부착될 때의 처리.
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            activity = (Activity) context;
            ((activity_home)activity).setOnBackPressedListener(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        addButton = getView().findViewById(R.id.header_add);
        filterButton = getView().findViewById(R.id.header_search);

        drawer = getView().findViewById(R.id.final_drawer_layout);



        BtnOnClickListener onClickListener = new BtnOnClickListener();
        addButton.setOnClickListener(onClickListener);


        NavigationView navigationView = (NavigationView) getView().findViewById(R.id.final_nav_view); //드로워 뷰


        //필터 버튼 클릭하면 드로워 열고 닫기
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        //필터(메뉴) 아이템 선택
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
            tabLayout.addTab(tabLayout.newTab().setText("인기"));
            tabLayout.addTab(tabLayout.newTab().setText("팔로잉"));
            tabLayout.addTab(tabLayout.newTab().setText("최신"));

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            //탭 페이저 설정 (탭 클릭시 바뀌는 화면)
            finalPager = (ViewPager) getView().findViewById(R.id.tab_Pager);
            pagerAdapter = new TabPagerAdapter_share(getChildFragmentManager(), tabLayout.getTabCount());
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
        //activity.setOnBackPressedListener(this);
    }

    //뒤로 가기 버튼이 눌렸을 경우 드로워(메뉴)를 닫는다.
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast.show();
            return;
        } else if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            activity.finish();
            toast.cancel();
        }

    }

    //클릭 리스너
    class BtnOnClickListener implements Button.OnClickListener {
        String res="";

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.header_add : //헤더- 추가 버튼
                    Intent intent = new Intent(getContext(), activity_addBoard.class);
                    startActivityForResult(intent,ADD_BOARD);
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_BOARD && resultCode == RESULT_OK)
            ((activity_home)activity).refresh_share();
    }

    //커스텀 함수
    public void setInfo(ClothesVO cloInfo){

    }
}