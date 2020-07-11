package com.Project.Closet.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebViewFragment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.Project.Closet.R;
import com.Project.Closet.activity_addClothes;
import com.Project.Closet.activity_login;
import com.Project.Closet.activity_profile;
import com.Project.Closet.activity_share;
import com.Project.Closet.activity_signup;
import com.Project.Closet.closet.activity_closet;
import com.Project.Closet.closet.fragment_closet;
import com.Project.Closet.codi.activity_codi_main;
import com.Project.Closet.codi.fragment_codi;
import com.Project.Closet.util.OnBackPressedListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.ssomai.android.scalablelayout.ScalableLayout;

public class activity_home2 extends AppCompatActivity {

    private FragmentManager fragmentManager;
    Fragment f_closet, f_codi;

    OnBackPressedListener listener;

    int ADD_CLOTHES = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home2);


        fragmentManager = getSupportFragmentManager();
        f_closet = new fragment_closet();
        fragmentManager.beginTransaction().replace(R.id.fragment_place, f_closet).commit();

        //f_codi = new fragment_codi();
        //transaction.hide(this);

        //옷장 아이콘 클릭
        ScalableLayout MyCloset = (ScalableLayout) findViewById(R.id.icon_footer_Closet);
        MyCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f_closet == null) {
                    f_closet = new fragment_closet();
                    fragmentManager.beginTransaction().add(R.id.fragment_place, f_closet).commit();
                }

                if(f_closet != null) fragmentManager.beginTransaction().show(f_closet).commit();
                if(f_codi != null) fragmentManager.beginTransaction().hide(f_codi).commit();
            }
        });

        //코디 아이콘 클릭
        ScalableLayout Codi = (ScalableLayout) findViewById(R.id.icon_footer_codi);
        Codi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f_codi == null) {
                    f_codi = new fragment_codi();
                    fragmentManager.beginTransaction().add(R.id.fragment_place, f_codi).commit();
                }

                if(f_closet != null) fragmentManager.beginTransaction().hide(f_closet).commit();
                if(f_codi != null) fragmentManager.beginTransaction().show(f_codi).commit();
            }
        });

        //옷 추가 아이콘 클릭
        ScalableLayout AddClothes = (ScalableLayout) findViewById(R.id.icon_footer_Add);
        AddClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_home2.this, activity_addClothes.class);
                startActivityForResult(intent,ADD_CLOTHES);
            }
        });

        //내 정보 아이콘 클릭
        ScalableLayout Profile = (ScalableLayout) findViewById(R.id.icon_footer_profile);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_home2.this, activity_profile.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CLOTHES && resultCode == RESULT_OK){
            f_closet = new fragment_closet();
            fragmentManager.beginTransaction().replace(R.id.fragment_place, f_closet).commit();
        }
    }

    public void setOnBackPressedListener(OnBackPressedListener listener){
        this.listener = listener;
    }

    @Override
    public void onBackPressed() {
        if(listener!=null){
            listener.onBackPressed();
        }else{
            super.onBackPressed();
        }
    }
}