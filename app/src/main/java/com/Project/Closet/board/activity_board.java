package com.Project.Closet.board;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.Project.Closet.R;
import com.Project.Closet.closet.activity_addClothes;
import com.Project.Closet.activity_profile;
import com.Project.Closet.codi.activity_codi_main;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.ArrayList;

public class activity_board extends AppCompatActivity {
    private TabLayout tabLayout;
    TabPagerAdapter_board pagerAdapter;
    private ViewPager finalPager;
    ImageView navMenu;
    public RelativeLayout Cloth_Info;
    public RelativeLayout Cloth_Info_edit;
    public ImageView iv_image;
    public ImageView iv_edit_image;
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
    public ImageView iv_save;
    public TextView tv_cloNo;
    public TextView tv_cloFavorite;
    public TextView tv_edit_category;
    public TextView tv_edit_season;
    public TextView tv_edit_date;
    public TextView tv_edit_name;
    public TextView tv_edit_detailcategory;
    public TextView tv_edit_brand;
    public TextView tv_edit_size;
;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_board);
        Cloth_Info = (RelativeLayout) findViewById(R.id.cloth_info);
        Cloth_Info.setVisibility(View.GONE);
        Cloth_Info_edit = (RelativeLayout) findViewById(R.id.cloth_info_edit);
        Cloth_Info_edit.setVisibility(View.GONE);


        iv_image = (ImageView) findViewById(R.id.iv_image);
        iv_edit_image = (ImageView) findViewById(R.id.iv_edit_image);
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
        iv_save = (ImageView) findViewById(R.id.iv_save);
        tv_cloNo = (TextView) findViewById(R.id.tv_clothes_no);
        tv_cloFavorite = (TextView) findViewById(R.id.tv_clothes_favorite);
        tv_edit_name = (TextView) findViewById(R.id.tv_edit_name);
        tv_edit_detailcategory = (TextView) findViewById(R.id.tv_edit_detailcategory);
        tv_edit_brand = (TextView) findViewById(R.id.tv_edit_brand);
        tv_edit_size = (TextView) findViewById(R.id.tv_edit_size);

        tv_edit_category = (TextView) findViewById(R.id.tv_edit_catergory);
        tv_edit_season = (TextView) findViewById(R.id.tv_edit_season);
        tv_edit_date = (TextView) findViewById(R.id.tv_edit_date);
        tv_edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(activity_board.this, listener, 2020, 6, 30);
                dialog.show();
            }
        });


        iv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_edit_name.getText()!=null)
                    tv_name.setText(tv_edit_name.getText());
                if(tv_edit_category.getText()!="카테고리를 선택해주세요.")
                    tv_category.setText(tv_edit_category.getText());
                if(tv_edit_detailcategory.getText()!=null)
                    tv_detailcategory.setText(tv_edit_detailcategory.getText());
                if(tv_edit_season.getText()!="계절을 선택해주세요.")
                    tv_season.setText(tv_edit_season.getText());
                if(tv_edit_brand.getText()!=null)
                    tv_brand.setText(tv_edit_brand.getText());
                if(tv_edit_size.getText()!=null)
                    tv_size.setText(tv_edit_size.getText());
                if(tv_edit_date.getText()!=null)
                    tv_date.setText(tv_edit_date.getText());

                Cloth_Info_edit.setVisibility(View.GONE);
            }
        });
        final String[] Season = {""};
        tv_edit_season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_board.this);
                final String[] items = getResources().getStringArray(R.array.Season);
                final ArrayList<String> selectedItem  = new ArrayList<String>();
                selectedItem.add(items[0]);

                builder.setTitle("카테고리 선택");

                builder.setSingleChoiceItems(R.array.Season, 0, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int pos)
                    {
                        selectedItem.clear();
                        selectedItem.add(items[pos]);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int pos)
                    {
                        Season[0] = selectedItem.get(0);

                        switch(Season[0]){
                            case "봄":
                                tv_edit_season.setText("봄");
                                break;
                            case "여름":
                                tv_edit_season.setText("여름");
                                break;
                            case "가을":
                                tv_edit_season.setText("가을");
                                break;
                            case "겨울":
                                tv_edit_season.setText("겨울");
                                break;
                        }
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        final String[] Category = {""};
        tv_edit_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_board.this);
                final String[] items = getResources().getStringArray(R.array.Category);
                final ArrayList<String> selectedItem  = new ArrayList<String>();
                selectedItem.add(items[0]);

                builder.setTitle("카테고리 선택");

                builder.setSingleChoiceItems(R.array.Category, 0, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int pos)
                    {
                        selectedItem.clear();
                        selectedItem.add(items[pos]);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int pos)
                    {
                        Category[0] = selectedItem.get(0);

                        switch(Category[0]){
                            case "상의":
                                tv_edit_category.setText("상의");
                                break;
                            case "하의":
                                tv_edit_category.setText("하의");
                                break;
                            case "한벌옷":
                                tv_edit_category.setText("한벌옷");
                                break;
                            case "외투":
                                tv_edit_category.setText("외투");
                                break;
                            case "신발":
                                tv_edit_category.setText("신발");
                                break;
                            case "가방":
                                tv_edit_category.setText("가방");
                                break;
                            case "악세서리":
                                tv_edit_category.setText("악세서리");
                                break;
                        }
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        final DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.final_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.final_nav_view); //드로워 뷰


        //옷장 아이콘 클릭 -> 새로고침 하도록 하자
        /*ScalableLayout MyCloset = (ScalableLayout) findViewById(R.id.icon_footer_Closet);
        MyCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_closet.this, activity_closet.class);
                startActivity(intent);
            }
        });
         */
        
        //코디 아이콘 클릭
        ScalableLayout Codi = (ScalableLayout) findViewById(R.id.icon_footer_codi);
        Codi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_board.this, activity_codi_main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        //옷 추가 아이콘 클릭
        ScalableLayout AddClothes = (ScalableLayout) findViewById(R.id.icon_footer_Add);
        AddClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_board.this, activity_addClothes.class);
                startActivity(intent);
            }
        });

        //내 정보 아이콘 클릭
        ScalableLayout Profile = (ScalableLayout) findViewById(R.id.icon_footer_profile);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_board.this, activity_profile.class);
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
        tabLayout.addTab(tabLayout.newTab().setText("상의"));
        tabLayout.addTab(tabLayout.newTab().setText("하의"));
        tabLayout.addTab(tabLayout.newTab().setText("한벌"));
        tabLayout.addTab(tabLayout.newTab().setText("외투"));
        tabLayout.addTab(tabLayout.newTab().setText("신발"));
        tabLayout.addTab(tabLayout.newTab().setText("가방"));
        tabLayout.addTab(tabLayout.newTab().setText("장식"));

        // 탭메뉴 아이콘 설정
        tabLayout.getTabAt(0).setIcon(R.drawable.all2); // 메뉴1
        tabLayout.getTabAt(1).setIcon(R.drawable.top); // 메뉴2
        tabLayout.getTabAt(2).setIcon(R.drawable.bottom); // 메뉴3
        tabLayout.getTabAt(3).setIcon(R.drawable.suit2); // 메뉴4
        tabLayout.getTabAt(4).setIcon(R.drawable.outer); // 메뉴5
        tabLayout.getTabAt(5).setIcon(R.drawable.shoes); // 메뉴6
        tabLayout.getTabAt(6).setIcon(R.drawable.bag); // 메뉴7
        tabLayout.getTabAt(7).setIcon(R.drawable.hat); // 메뉴8

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //탭 페이저 설정 (탭 클릭시 바뀌는 화면)
        finalPager = (ViewPager) findViewById(R.id.tab_Pager);
        pagerAdapter = new TabPagerAdapter_board(getSupportFragmentManager(), tabLayout.getTabCount());
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


/*
    class BtnOnClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.iv_heart :
                    Toast.makeText(getApplicationContext(), "하트 선택", Toast.LENGTH_SHORT).show();
                    break ;
                case R.id.iv_modify :
                    break ;
                case R.id.iv_delete : //삭제
                    break ;
            }
        }
    }

 */

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            tv_edit_date.setText(year + "년" + monthOfYear + "월" + dayOfMonth +"일");
            Toast.makeText(getApplicationContext(), year + "년" + monthOfYear + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();
        }
    };
    //뒤로 가기 버튼이 눌렸을 경우 드로워(메뉴)를 닫는다.
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.final_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (Cloth_Info_edit.getVisibility() == View.VISIBLE) {
            Cloth_Info_edit.setVisibility(View.GONE);
        } else if (Cloth_Info.getVisibility() == View.VISIBLE) {
            Cloth_Info.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }

    }

}
