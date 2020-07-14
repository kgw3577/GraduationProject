package com.Project.Closet.old;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Project.Closet.R;
import com.Project.Closet.closet.activity_addClothes;
import com.Project.Closet.activity_profile;
import com.Project.Closet.activity_share;
import com.Project.Closet.home.TabPagerAdapter_home;
import com.Project.Closet.codi.activity_codi_main;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.ssomai.android.scalablelayout.ScalableLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

public class activity_home3 extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager finalPager;
    TabPagerAdapter_home pagerAdapter;
    ImageView navMenu;

    public RelativeLayout Cloth_Info;
    public ImageView iv_image;
    public TextView tv_name;
    public TextView tv_category;
    public TextView tv_detailcategory;
    public TextView tv_season;
    public TextView tv_brand;
    public TextView tv_size;
    public TextView tv_date;

    public ImageView iv_heart;
    public ImageView iv_modify;
    public ImageView iv_delete;
    public TextView tv_cloNo;
    public TextView tv_cloFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repeatedFunction(); //onResume()으로 화면의 지속적인 갱신을 위해 코드를 따로 뺌
    }


    //뒤로 가기 버튼이 눌렸을 경우 드로워(메뉴)를 닫는다.
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.final_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (Cloth_Info.getVisibility() == View.VISIBLE) {
            Cloth_Info.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }

    }



    @Override
    public void onResume() {
        super.onResume();
        repeatedFunction();
    }

    public void repeatedFunction(){
        setContentView(R.layout.layout_home);
        Cloth_Info = (RelativeLayout) findViewById(R.id.cloth_info);
        Cloth_Info.setVisibility(View.GONE);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        tv_name = (TextView) findViewById(R.id.tv_info_name);
        tv_category = (TextView) findViewById(R.id.tv_info_catergory);
        tv_detailcategory = (TextView) findViewById(R.id.tv_info_detailcategory);
        tv_season = (TextView) findViewById(R.id.tv_info_season);
        tv_brand = (TextView) findViewById(R.id.tv_info_brand);
        tv_size = (TextView) findViewById(R.id.tv_info_size);
        tv_date = (TextView) findViewById(R.id.tv_info_date);

        iv_heart = (ImageView) findViewById(R.id.iv_heart);
        iv_modify = (ImageView) findViewById(R.id.iv_modify);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        tv_cloNo = (TextView) findViewById(R.id.tv_clothes_no);
        tv_cloFavorite = (TextView) findViewById(R.id.tv_clothes_favorite);





        final DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.final_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.final_nav_view); //드로워 뷰

        //옷장 아이콘 클릭
        ScalableLayout MyCloset = (ScalableLayout) findViewById(R.id.icon_footer_Closet);
        MyCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_home3.this, activity_closet.class);
                startActivity(intent);
            }
        });

        //코디 아이콘 클릭
        ScalableLayout Codi = (ScalableLayout) findViewById(R.id.icon_footer_codi);
        Codi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_home3.this, activity_codi_main.class);
                startActivity(intent);
            }
        });

        //옷 추가 아이콘 클릭
        ScalableLayout AddClothes = (ScalableLayout) findViewById(R.id.icon_footer_Add);
        AddClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_home3.this, activity_addClothes.class);
                startActivity(intent);
            }
        });
        //내 정보 아이콘 클릭
        ScalableLayout Share = (ScalableLayout) findViewById(R.id.icon_footer_share);
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_home3.this, activity_share.class);
                startActivity(intent);
            }
        });

        //내 정보 아이콘 클릭
        ScalableLayout Profile = (ScalableLayout) findViewById(R.id.icon_footer_profile);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_home3.this, activity_profile.class);
                startActivity(intent);
            }
        });

        //메뉴 버튼 클릭하면 드로워 열고 닫기
        navMenu = (ImageView)findViewById(R.id.header_nav_iv);
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
                        Toast.makeText(getApplicationContext(), "SelectedItem 1", Toast.LENGTH_SHORT).show();
                    case R.id.menuitem2:
                        Toast.makeText(getApplicationContext(), "SelectedItem 2", Toast.LENGTH_SHORT).show();
                    case R.id.menuitem3:
                        Toast.makeText(getApplicationContext(), "SelectedItem 3", Toast.LENGTH_SHORT).show();
                }

                DrawerLayout drawer = findViewById(R.id.final_drawer_layout);
                //drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        //탭 목록 설정
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("찜한 옷"));
        tabLayout.addTab(tabLayout.newTab().setText("찜한 코디"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //탭 페이저 설정 (탭 클릭시 바뀌는 화면)
        finalPager = (ViewPager) findViewById(R.id.tab_Pager);
        pagerAdapter = new TabPagerAdapter_home(getSupportFragmentManager(), tabLayout.getTabCount());
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