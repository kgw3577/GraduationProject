package com.Project.Closet.codi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.Project.Closet.R;
import com.Project.Closet.activity_addClothes;
import com.Project.Closet.closet.activity_closet;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.ssomai.android.scalablelayout.ScalableLayout;

public class activity_codi_main extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager finalPager;
    ImageView navMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_codi);

        final DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.final_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.final_nav_view); //드로워 뷰


        //옷장 아이콘 클릭
        ScalableLayout MyCloset = (ScalableLayout) findViewById(R.id.icon_footer_Closet);
        MyCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_codi_main.this, activity_closet.class);
                startActivity(intent);
            }
        });
        
        //코디 아이콘 클릭 -> 새로고침 하도록
        /*ScalableLayout Codi = (ScalableLayout) findViewById(R.id.icon_footer_codi);
        Codi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_codi.this, Codi_main.class);
                startActivity(intent);
            }
        });
         */

        //옷 추가 아이콘 클릭
        ScalableLayout AddClothes = (ScalableLayout) findViewById(R.id.icon_footer_Add);
        AddClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_codi_main.this, activity_addClothes.class);
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
        tabLayout.getTabAt(5).setIcon(R.drawable.shoes); // 메뉴6
        tabLayout.getTabAt(6).setIcon(R.drawable.bag); // 메뉴7
        tabLayout.getTabAt(7).setIcon(R.drawable.hat); // 메뉴7

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //탭 페이저 설정 (탭 클릭시 바뀌는 화면)
        finalPager = (ViewPager) findViewById(R.id.tab_Pager);
        TabPagerAdapter_codi pagerAdapter = new TabPagerAdapter_codi(getSupportFragmentManager(), tabLayout.getTabCount());
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


        ImageView view_add_codi = (ImageView) findViewById(R.id.add_codi_iv); //드로워 뷰
        view_add_codi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_codi_main.this, activity_addCodi.class);
                startActivity(intent);
            }
        });


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
}
