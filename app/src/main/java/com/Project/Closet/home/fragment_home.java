package com.Project.Closet.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.Service.SocialService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.BoardVO;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.R;
import com.Project.Closet.closet.addClothes.activity_addClothes;
import com.Project.Closet.codi.addCodi.MyPagerAdapter;
import com.Project.Closet.home.recommend.recommendPagerFragment;
import com.Project.Closet.util.OnBackPressedListener;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

public class fragment_home extends Fragment implements OnBackPressedListener {

    ViewGroup viewGroup;
    Toast toast;
    long backKeyPressedTime;

    int ADD_CLOTHES = 100;

    Activity activity;

    private ViewPager finalPager_recommend;
    private MyPagerAdapter pagerAdapter_recommend;

    private TabLayout tabLayout_favorite;
    public TabPagerAdapter_home pagerAdapter_favorite;
    private ViewPager finalPager_favorite;

    RelativeLayout filterButton;
    RelativeLayout addButton;

    DrawerLayout drawer;

    LinearLayout ll_detail;
    public RelativeLayout Cloth_Info;
    public RelativeLayout Cloth_Info_edit;
    public ImageView iv_image;
    public ImageView iv_edit_image;
    public TextView tv_category;
    public TextView tv_detailcategory;
    public TextView tv_color;
    public TextView tv_season;
    public TextView tv_brand;
    public TextView tv_size;
    public TextView tv_buyDate;

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

    public static fragment_home newInstance() {

        Bundle args = new Bundle();

        fragment_home fragment = new fragment_home();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.frag_home,container,false);
        toast = Toast.makeText(getContext(),"한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT);
        return viewGroup;
    }

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

        MySharedPreferences pref = MySharedPreferences.getInstanceOf(getContext());
        String userID = pref.getUserID();

        addButton = getView().findViewById(R.id.header_add);
        filterButton = getView().findViewById(R.id.header_search);
        ll_detail = getView().findViewById(R.id.ll_detail);

        drawer = getView().findViewById(R.id.final_drawer_layout);

        Cloth_Info = (RelativeLayout) getView().findViewById(R.id.cloth_info);
        Cloth_Info.setVisibility(View.GONE);
        Cloth_Info_edit = (RelativeLayout) getView().findViewById(R.id.cloth_info_edit);
        Cloth_Info_edit.setVisibility(View.GONE);

        iv_image = (ImageView) getView().findViewById(R.id.iv_codi_image);
        iv_edit_image = (ImageView) getView().findViewById(R.id.iv_edit_image);
        tv_category = (TextView) getView().findViewById(R.id.tv_info_catergory);
        tv_detailcategory = (TextView) getView().findViewById(R.id.tv_info_detailcategory);
        tv_color = (TextView) getView().findViewById(R.id.tv_info_color);
        tv_season = (TextView) getView().findViewById(R.id.tv_info_season);
        tv_brand = (TextView) getView().findViewById(R.id.tv_info_brand);
        tv_size = (TextView) getView().findViewById(R.id.tv_info_size);
        tv_buyDate = (TextView) getView().findViewById(R.id.tv_info_date);

        iv_heart = (ImageView) getView().findViewById(R.id.iv_heart);
        iv_modify = (ImageView) getView().findViewById(R.id.iv_modify);
        iv_delete = (ImageView) getView().findViewById(R.id.iv_delete);
        iv_save = (ImageView) getView().findViewById(R.id.iv_save);

        tv_cloNo = (TextView) getView().findViewById(R.id.tv_clothes_no);
        tv_cloFavorite = (TextView) getView().findViewById(R.id.tv_clothes_favorite);
        tv_edit_name = (TextView) getView().findViewById(R.id.tv_info_color);
        tv_edit_detailcategory = (TextView) getView().findViewById(R.id.tv_edit_detailcategory);
        tv_edit_brand = (TextView) getView().findViewById(R.id.tv_edit_brand);
        tv_edit_size = (TextView) getView().findViewById(R.id.tv_edit_size);

        tv_edit_category = (TextView) getView().findViewById(R.id.tv_edit_catergory);
        tv_edit_season = (TextView) getView().findViewById(R.id.tv_edit_season);
        tv_edit_date = (TextView) getView().findViewById(R.id.tv_edit_date);
        tv_edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(activity, listener, 2020, 6, 30);
                dialog.show();
            }
        });

        BtnOnClickListener onClickListener = new BtnOnClickListener();
        iv_heart.setOnClickListener(onClickListener);
        iv_modify.setOnClickListener(onClickListener);
        iv_delete.setOnClickListener(onClickListener);
        addButton.setOnClickListener(onClickListener);


        iv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    tv_buyDate.setText(tv_edit_date.getText());

                Cloth_Info_edit.setVisibility(View.GONE);
            }
        });
        final String[] Season = {""};
        tv_edit_season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                final String[] items = getResources().getStringArray(R.array.Kind);
                final ArrayList<String> selectedItem  = new ArrayList<String>();
                selectedItem.add(items[0]);

                builder.setTitle("카테고리 선택");

                builder.setSingleChoiceItems(R.array.Kind, 0, new DialogInterface.OnClickListener(){
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
                            case "액세서리":
                                tv_edit_category.setText("액세서리");
                                break;
                        }
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

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








        if(tabLayout_favorite == null){
            //탭 목록 설정
            tabLayout_favorite = (TabLayout) getView().findViewById(R.id.favorite_tabLayout);
            tabLayout_favorite.addTab(tabLayout_favorite.newTab().setText("찜한 옷"));
            tabLayout_favorite.addTab(tabLayout_favorite.newTab().setText("찜한 코디"));
            tabLayout_favorite.setTabGravity(TabLayout.GRAVITY_CENTER);

            //탭 페이저 설정 (탭 클릭시 바뀌는 화면)
            finalPager_favorite = (ViewPager) getView().findViewById(R.id.favorite_tab_Pager);
            pagerAdapter_favorite = new TabPagerAdapter_home(getChildFragmentManager(), tabLayout_favorite.getTabCount());
            finalPager_favorite.setAdapter(pagerAdapter_favorite);
            finalPager_favorite.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout_favorite));
            tabLayout_favorite.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    finalPager_favorite.setCurrentItem(tab.getPosition());
                }
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }
                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }





        //탭 페이저 설정 (탭 클릭시 바뀌는 화면)
        finalPager_recommend = (ViewPager) getView().findViewById(R.id.recommend_tab_Pager);
        pagerAdapter_recommend = new MyPagerAdapter(getChildFragmentManager());

        List<BoardVO> recommendedList = null;
        try {
            recommendedList = new RecommendTask().execute(userID).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(recommendedList.size()!=0){
            pagerAdapter_recommend.addItem(recommendPagerFragment.newInstance((ArrayList<BoardVO>) recommendedList));
        }
        else
            Toast.makeText(getContext(), "추천할 아이템이 없습니다.", Toast.LENGTH_SHORT).show();

        finalPager_recommend.setAdapter(pagerAdapter_recommend);


    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            tv_edit_date.setText(year + "년" + monthOfYear + "월" + dayOfMonth +"일");
            Toast.makeText(getContext(), year + "년" + monthOfYear + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();
        }
    };


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
        } else if (Cloth_Info_edit.getVisibility() == View.VISIBLE) {
            Cloth_Info_edit.setVisibility(View.GONE);
        } else if (Cloth_Info.getVisibility() == View.VISIBLE) {
            Cloth_Info.setVisibility(View.GONE);
        } else if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast.show();
            return;
        } else if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            activity.finish();
            toast.cancel();
        }

    }

    public class RecommendTask extends AsyncTask<String, Void, List<BoardVO>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<BoardVO> doInBackground(String... params) {

            Call<List<BoardVO>> boardListCall = SocialService.getRetrofit(getContext()).recommendFull(params[0]);
            //params : userID
            try {
                return boardListCall.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
        @Override
        protected void onPostExecute(List<BoardVO> boardList) {
            super.onPostExecute(boardList);
        }
    }


    public class FavoriteTask extends AsyncTask<ClothesVO, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(ClothesVO... ClothesFilter) {

            Call<String> stringCall = ClothesService.getRetrofit(getContext()).modifyClothes(ClothesFilter[0]);
            try {
                return stringCall.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public class DeleteTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... cloNo) {

            Call<String> stringCall = ClothesService.getRetrofit(getContext()).deleteClothes(cloNo[0]);
            try {
                return stringCall.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    //클릭 리스너
    class BtnOnClickListener implements Button.OnClickListener {
        String res="";

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.header_add : //헤더- 추가 버튼
                    Intent intent = new Intent(getContext(), activity_addClothes.class);
                    intent.putExtra("location","private");
                    startActivityForResult(intent,ADD_CLOTHES);
                    break;
                case R.id.iv_heart : //즐겨찾기
                    //필터가 될 vo 설정
                    ClothesVO clothesFilter = new ClothesVO();
                    clothesFilter.setCloNo(Integer.parseInt(tv_cloNo.getText().toString()));
                    boolean reverted_favorite;
                    //즐겨찾기 여부 불러와서 반대값으로 설정
                    if("yes".equals(tv_cloFavorite.getText().toString())){
                        clothesFilter.setFavorite("no");
                        reverted_favorite = false;
                    }
                    else{
                        clothesFilter.setFavorite("yes");
                        reverted_favorite = true;
                    }

                    try {
                        res = new FavoriteTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, clothesFilter).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e("tag",res);
                    if("ok".equals(res)){
                        if(reverted_favorite){
                            Toast.makeText(getContext(), "즐겨찾기를 등록했습니다.", Toast.LENGTH_SHORT).show();
                            iv_heart.setImageResource(R.drawable.heart_color);
                            tv_cloFavorite.setText("yes");
                        }else{
                            Toast.makeText(getContext(), "즐겨찾기를 해제했습니다.", Toast.LENGTH_SHORT).show();
                            iv_heart.setImageResource(R.drawable.heart_empty);
                            tv_cloFavorite.setText("no");
                        }
                        ((activity_home)activity).refresh_home();
                    }
                    else
                        Toast.makeText(getContext(), "즐겨찾기 실패", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.iv_modify : //수정 버튼
                    //Cloth_Info.setVisibility(View.GONE);
                    Cloth_Info_edit.setVisibility(View.VISIBLE);
                    tv_edit_date.setText(tv_buyDate.getText());
                    break;
                case R.id.iv_delete : //삭제 버튼
                    //확인 Alert 다이얼로그
                    try {
                        res = new DeleteTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, tv_cloNo.getText().toString()).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if("ok".equals(res)){
                        Toast.makeText(getContext(), "옷을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                        Cloth_Info.setVisibility(View.GONE);
                        //pagerAdapter.notifyDataSetChanged();
                        ((activity_home)activity).refresh_clothes(fragment_home.this);

                    }else{
                        Toast.makeText(getContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_CLOTHES && resultCode == RESULT_OK)
            ((activity_home)activity).refresh_clothes(fragment_home.this);
    }

    //커스텀 함수
    public void setInfo(ClothesVO cloInfo){

        Cloth_Info.setVisibility(View.VISIBLE);
        String ImageUrl = Global.baseURL+cloInfo.getFilePath();

        Glide.with((iv_image).getContext()).load(ImageUrl).into(iv_image);
        Glide.with((iv_edit_image).getContext()).load(ImageUrl).into(iv_edit_image);

        String category = cloInfo.getCategory();
        String detailCategory = cloInfo.getDetailCategory();
        tv_category.setText(category);
        if(category.equals(detailCategory))
            ll_detail.setVisibility(View.GONE);
        else{
            ll_detail.setVisibility(View.VISIBLE);
            tv_detailcategory.setText(detailCategory);
        }
        tv_color.setText(cloInfo.getColor());
        tv_season.setText(cloInfo.getSeason());
        tv_brand.setText(cloInfo.getBrand());
        tv_size.setText(cloInfo.getCloSize());
        tv_buyDate.setText(cloInfo.getBuyDate());
        tv_cloNo.setText(Integer.toString(cloInfo.getCloNo()));

        if("yes".equals(cloInfo.getFavorite())){
            iv_heart.setImageResource(R.drawable.heart_color);
            tv_cloFavorite.setText("yes");
        }
        else{
            iv_heart.setImageResource(R.drawable.heart_empty);
            tv_cloFavorite.setText("no");
        }
    }
}