package com.Project.Closet.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.Project.Closet.R;
import com.Project.Closet.activity_addClothes;
import com.Project.Closet.closet.activity_closet;
import com.Project.Closet.codi.activity_codi_main;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.ssomai.android.scalablelayout.ScalableLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

public class activity_home extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager finalPager;
    TabPagerAdapter_home pagerAdapter;
    ImageView navMenu;

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

        final DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.final_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.final_nav_view); //드로워 뷰

        //옷장 아이콘 클릭
        ScalableLayout MyCloset = (ScalableLayout) findViewById(R.id.icon_footer_Closet);
        MyCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_home.this, activity_closet.class);
                startActivity(intent);
            }
        });

        //코디 아이콘 클릭
        ScalableLayout Codi = (ScalableLayout) findViewById(R.id.icon_footer_codi);
        Codi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_home.this, activity_codi_main.class);
                startActivity(intent);
            }
        });

        //옷 추가 아이콘 클릭
        ScalableLayout AddClothes = (ScalableLayout) findViewById(R.id.icon_footer_Add);
        AddClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_home.this, activity_addClothes.class);
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
