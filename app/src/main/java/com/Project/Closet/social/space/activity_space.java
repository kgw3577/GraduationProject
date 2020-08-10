package com.Project.Closet.social.space;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.SocialService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.FeedVO;
import com.Project.Closet.HTTP.VO.FollowVO;
import com.Project.Closet.HTTP.VO.UserspaceVO;
import com.Project.Closet.R;
import com.Project.Closet.home.activity_home;
import com.Project.Closet.social.TabPagerAdapter_social;
import com.Project.Closet.social.activity_addBoard;
import com.Project.Closet.social.subfragment.Fragment_Feed;
import com.Project.Closet.subfragment.TabFragment_Clothes_inHome;
import com.Project.Closet.util.OnBackPressedListener;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

import static android.app.Activity.RESULT_OK;
import static com.Project.Closet.util.NumFormat.formatNumString;
import static com.Project.Closet.util.NumFormat.formatNumStringZero;

public class activity_space extends AppCompatActivity {

    Toast toast;
    long backKeyPressedTime;

    //int ADD_BOARD = 8080;

    //Activity activity;

    private TabLayout tabLayout;
    public TabPagerAdapter_social pagerAdapter;
    private ViewPager finalPager;

    LinearLayout drawer;

    Call<UserspaceVO> userspaceCall;
    UserspaceVO userspaceInfo;
    String targetID;
    String myID;
    Button bt_follow;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);

        targetID = getIntent().getExtras().getString("targetID");
        myID = MySharedPreferences.getInstanceOf(getApplicationContext()).getUserID();

        try {
            userspaceInfo = new userInfoTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //프사 설정
        ImageView iv_profileImage = findViewById(R.id.iv_profileImage);
        Glide.with(getApplicationContext()).load(Global.getOriginalPath(userspaceInfo.getPfImagePath())).into(iv_profileImage);
        //아이디 설정
        TextView tv_id = findViewById(R.id.tv_id);
        tv_id.setText("@"+targetID);
        //닉네임 설정
        TextView tv_nickname = findViewById(R.id.tv_nickname);
        tv_nickname.setText(userspaceInfo.getNickname());
        //게시물, 팔로워, 팔로잉 수 설정
        TextView tv_numBoard = findViewById(R.id.tv_numBoard);
        TextView tv_numFollower = findViewById(R.id.tv_numFollower);
        TextView tv_numFollowing = findViewById(R.id.tv_numFollowing);
        tv_numBoard.setText(formatNumStringZero(userspaceInfo.getNumBoard()));
        tv_numFollower.setText(formatNumStringZero(userspaceInfo.getNumFollower()));
        tv_numFollowing.setText(formatNumStringZero(userspaceInfo.getNumFollowing()));
        //소개글 설정
        TextView tv_pfContents = findViewById(R.id.tv_pfContents);
        if(userspaceInfo.getPfContents()==null){
            tv_pfContents.setVisibility(View.GONE);
        }else{
            tv_pfContents.setText(userspaceInfo.getPfContents());
        }

        LinearLayout ll_following_friends = findViewById(R.id.ll_following_friends);

        //팔로우 여부 설정
        bt_follow = findViewById(R.id.bt_follow);
        final String myID = MySharedPreferences.getInstanceOf(getApplicationContext()).getUserID();
        if(myID.equals(targetID)){
            bt_follow.setVisibility(View.GONE);
            ll_following_friends.setVisibility(View.GONE);
        }
        else{
            bt_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String result = new followTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, myID,targetID).get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            // 내가 팔로우한 사용자 중 현재 페이지 사용자를 팔로우한 사용자가 있는지.
            if(userspaceInfo.getFollowing_friendsID()==null){ //없음
                ll_following_friends.setVisibility(View.GONE);
            }else{ //있음
                //친구 프사 설정
                ImageView iv_following_friendsImg = findViewById(R.id.iv_following_friendsImg);
                Glide.with(getApplicationContext()).load(Global.getOriginalPath(userspaceInfo.getFollowing_friendsImgPath())).into(iv_following_friendsImg);
                //친구 닉네임 설정
                TextView tv_following_friendsName = findViewById(R.id.tv_following_friendsName);
                tv_following_friendsName.setText(userspaceInfo.getFollowing_friendsName());
            }
        }



        if(userspaceInfo.getIf_following().equals("not_following")){
            ViewCompat.setBackgroundTintList(bt_follow, ColorStateList.valueOf(Color.parseColor("#aa0055af")));
            bt_follow.setTextColor(Color.parseColor("#ffffff"));
            bt_follow.setText("팔로우");
        }else if(userspaceInfo.getIf_following().equals("following")){
            ViewCompat.setBackgroundTintList(bt_follow, ColorStateList.valueOf(Color.parseColor("#ffffff")));
            bt_follow.setTextColor(Color.parseColor("#000000"));
            bt_follow.setText("팔로잉");
        }

        toast = Toast.makeText(getApplicationContext(),"한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT);

        drawer = findViewById(R.id.final_drawer_layout);

        //BtnOnClickListener onClickListener = new BtnOnClickListener();


        if(tabLayout == null){
            //탭 목록 설정
            tabLayout = findViewById(R.id.tabLayout);
            tabLayout.addTab(tabLayout.newTab().setText("팔로잉"));
            tabLayout.addTab(tabLayout.newTab().setText("인기"));
            tabLayout.addTab(tabLayout.newTab().setText("최신"));

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            //탭 페이저 설정 (탭 클릭시 바뀌는 화면)
            finalPager = findViewById(R.id.tab_Pager);
            pagerAdapter = new TabPagerAdapter_social(getSupportFragmentManager(), tabLayout.getTabCount());
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


    public class userInfoTask extends AsyncTask<String, Void, UserspaceVO> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }


        @Override
        protected UserspaceVO doInBackground(String... params) {


            userspaceCall = SocialService.getRetrofit(activity_space.this).showUserSpace(targetID, myID);

            //인자 params[0]은 page.

            try {
                return userspaceCall.execute().body();

                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(UserspaceVO userspaceInfo) {
            super.onPostExecute(userspaceInfo);
            if(userspaceInfo!=null) {
                //
            }
        }
    }



    public class followTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }


        @Override
        protected String doInBackground(String... params) {
            FollowVO followInfo = new FollowVO(params[0],params[1]);
            //params : 팔로워, 팔로우되는 사람

            Call<String> stringCall = SocialService.getRetrofit(activity_space.this).executeFollow(followInfo);

            //인자 params[0]은 page.

            try {
                return stringCall.execute().body();

                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            if(res!=null) {
                if(res.equals("following")){
                    setFollowColor(true);
                }else if(res.equals("not_following")){
                    setFollowColor(false);
                }

            }
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        //activity.setOnBackPressedListener(this);
    }

    //뒤로 가기 버튼
    //@Override
    //public void onBackPressed() {
        //
    //}

    //클릭 리스너
    class BtnOnClickListener implements Button.OnClickListener {
        String res="";

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.header_add : //헤더- 추가 버튼
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == ADD_BOARD && resultCode == RESULT_OK)
//            ((activity_home)activity).refresh_share();
    }

    public void setFollowColor(boolean is_following){
        if(!is_following){
            ViewCompat.setBackgroundTintList(bt_follow, ColorStateList.valueOf(Color.parseColor("#aa0055af")));
            bt_follow.setTextColor(Color.parseColor("#ffffff"));
            bt_follow.setText("팔로우");
        }else if(is_following){
            ViewCompat.setBackgroundTintList(bt_follow, ColorStateList.valueOf(Color.parseColor("#ffffff")));
            bt_follow.setTextColor(Color.parseColor("#000000"));
            bt_follow.setText("팔로잉");
        }
    }
}