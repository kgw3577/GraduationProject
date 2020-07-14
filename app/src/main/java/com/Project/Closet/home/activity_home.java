package com.Project.Closet.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Project.Closet.R;
import com.Project.Closet.activity_profile;
import com.Project.Closet.share.fragment_share;
import com.Project.Closet.closet.fragment_closet;
import com.Project.Closet.codi.fragment_codi;
import com.Project.Closet.util.OnBackPressedListener;
import com.ssomai.android.scalablelayout.ScalableLayout;

public class activity_home extends AppCompatActivity {

    private FragmentManager fragmentManager;
    Fragment f_closet, f_codi, f_home, f_share;

    OnBackPressedListener listener;

    int ADD_CLOTHES = 11;

    boolean is_home_changed=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);


        fragmentManager = getSupportFragmentManager();
        f_home = new fragment_home();
        fragmentManager.beginTransaction().replace(R.id.fragment_place, f_home,"home").commit();


        //옷장 아이콘 클릭
        ScalableLayout MyCloset = (ScalableLayout) findViewById(R.id.icon_footer_Closet);
        MyCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f_closet == null) {
                    f_closet = new fragment_closet();
                    fragmentManager.beginTransaction().add(R.id.fragment_place, f_closet,"closet").commit();
                }

                if(f_closet != null) fragmentManager.beginTransaction().show(f_closet).commit();
                if(f_codi != null) fragmentManager.beginTransaction().hide(f_codi).commit();
                if(f_home != null) fragmentManager.beginTransaction().hide(f_home).commit();
                if(f_share != null) fragmentManager.beginTransaction().hide(f_share).commit();
                setOnBackPressedListener((fragment_closet)f_closet);
            }
        });

        //코디 아이콘 클릭
        ScalableLayout Codi = (ScalableLayout) findViewById(R.id.icon_footer_codi);
        Codi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f_codi == null) {
                    f_codi = new fragment_codi();
                    fragmentManager.beginTransaction().add(R.id.fragment_place, f_codi,"codi").commit();
                }

                if(f_closet != null) fragmentManager.beginTransaction().hide(f_closet).commit();
                if(f_codi != null) fragmentManager.beginTransaction().show(f_codi).commit();
                if(f_home != null) fragmentManager.beginTransaction().hide(f_home).commit();
                if(f_share != null) fragmentManager.beginTransaction().hide(f_share).commit();
                setOnBackPressedListener((fragment_codi)f_codi);
            }
        });

        //홈 아이콘 클릭
        ScalableLayout Home = (ScalableLayout) findViewById(R.id.icon_footer_Add);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(is_home_changed){
                    refresh_home();
                    is_home_changed=false;
                }
                else{
                    if(f_home == null) {
                        f_home = new fragment_home();
                        fragmentManager.beginTransaction().add(R.id.fragment_place, f_home,"home").commit();
                    }

                    if(f_closet != null) fragmentManager.beginTransaction().hide(f_closet).commit();
                    if(f_codi != null) fragmentManager.beginTransaction().hide(f_codi).commit();
                    if(f_home != null) fragmentManager.beginTransaction().show(f_home).commit();
                    if(f_share != null) fragmentManager.beginTransaction().hide(f_share).commit();
                    setOnBackPressedListener((fragment_home)f_home);
                }
            }
        });

        //공유 아이콘 클릭
        ScalableLayout Share = (ScalableLayout) findViewById(R.id.icon_footer_share);
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f_share == null) {
                    f_share = new fragment_share();
                    fragmentManager.beginTransaction().add(R.id.fragment_place, f_share,"share").commit();
                }

                if(f_closet != null) fragmentManager.beginTransaction().hide(f_closet).commit();
                if(f_codi != null) fragmentManager.beginTransaction().hide(f_codi).commit();
                if(f_home != null) fragmentManager.beginTransaction().hide(f_home).commit();
                if(f_share != null) fragmentManager.beginTransaction().show(f_share).commit();
                setOnBackPressedListener((fragment_share)f_share);
            }
        });

        //내 정보 아이콘 클릭
        ScalableLayout Profile = (ScalableLayout) findViewById(R.id.icon_footer_profile);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_home.this, activity_profile.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentTransaction transaction;

        if (requestCode == ADD_CLOTHES && resultCode == RESULT_OK){
            transaction = fragmentManager.beginTransaction();
            transaction.remove(f_closet);
            f_closet = new fragment_closet();
            transaction.add(R.id.fragment_place,f_closet,"closet").commit();
        }
    }

    public OnBackPressedListener getOnBackPressedListener(){
        return listener;
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

    public void refresh_clothes(Fragment end_fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(end_fragment instanceof fragment_closet){
            if(f_home != null){
                System.out.println("home초기화");
                transaction.remove(f_home);
                f_home = new fragment_home();
                transaction.add(R.id.fragment_place,f_home,"home");
            }
            if(f_closet != null){
                System.out.println("closet초기화");
                transaction.remove(f_closet);
                f_closet = new fragment_closet();
                transaction.add(R.id.fragment_place,f_closet,"closet");
            }
        }
        else if((end_fragment instanceof fragment_home)){
            if(f_closet != null){
                System.out.println("closet초기화");
                transaction.remove(f_closet);
                f_closet = new fragment_closet();
                transaction.add(R.id.fragment_place,f_closet,"closet");
            }
            if(f_home != null){
                System.out.println("home초기화");
                transaction.remove(f_home);
                f_home = new fragment_home();
                transaction.add(R.id.fragment_place,f_home,"home");
            }
        }
        transaction.commit();
    }




    public void refresh_codi(Fragment end_fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(end_fragment instanceof fragment_codi){
            if(f_home != null){
                System.out.println("home초기화");
                transaction.remove(f_home);
                f_home = new fragment_home();
                transaction.add(R.id.fragment_place,f_home,"home");
            }
            if(f_codi != null){
                System.out.println("codi초기화");
                transaction.remove(f_codi);
                f_codi = new fragment_codi();
                transaction.add(R.id.fragment_place,f_codi,"codi");
            }
        }
        else if((end_fragment instanceof fragment_home)){
            if(f_codi != null){
                System.out.println("codi초기화");
                transaction.remove(f_codi);
                f_codi = new fragment_codi();
                transaction.add(R.id.fragment_place,f_codi,"codi");
            }
            if(f_home != null){
                System.out.println("home초기화");
                transaction.remove(f_home);
                f_home = new fragment_home();
                transaction.add(R.id.fragment_place,f_home,"home");
            }
        }
        transaction.commit();
    }









    public void refresh_home(){
        if(f_home != null){
            FragmentTransaction transaction;
            transaction = fragmentManager.beginTransaction();
            transaction.remove(f_home);
            f_home = new fragment_home();
            transaction.add(R.id.fragment_place,f_home,"home").commit();
        }
    }

    public void notify_home_changed(){
        is_home_changed=true;
    }
}