package com.Project.Closet.codi.addCodi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Project.Closet.R;
import com.Project.Closet.closet.TabFragment_accessory;
import com.Project.Closet.closet.TabFragment_allClothes;
import com.Project.Closet.closet.TabFragment_bag;
import com.Project.Closet.closet.TabFragment_bottom;
import com.Project.Closet.closet.TabFragment_outer;
import com.Project.Closet.closet.TabFragment_shoes;
import com.Project.Closet.closet.TabFragment_suit;
import com.Project.Closet.closet.TabFragment_top;
import com.Project.Closet.codi.PagerAdapter_addCodi;

public class activity_addCodi extends AppCompatActivity {

    enum Category {
        ALL, TOP, BOTTOM, SUIT, OUTER, SHOES, BAG, ACCESSORY
    }

    //뷰페이저 선언
    private ViewPager viewPager;
    private PagerAdapter_addCodi pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_codi);

        //헤더 메뉴 아이콘 받아오기
        ImageView ivSearch = (ImageView) findViewById(R.id.iv_search);
        ImageView ivRandom = (ImageView) findViewById(R.id.iv_random);
        TextView tvPreview = (TextView) findViewById(R.id.tv_preview);
        TextView tvDone = (TextView) findViewById(R.id.tv_done);

        //코디 화면 레이아웃과 이미지뷰 받아오기
        RelativeLayout layoutMakeCodi = (RelativeLayout) findViewById(R.id.layout_makecodi);
        ImageView ivTop = (ImageView) findViewById(R.id.iv_top);
        ImageView ivBottom = (ImageView) findViewById(R.id.iv_bottom);

        //버튼 온클릭리스너 설정
        BtnOnClickListener onClickListener = new BtnOnClickListener();
        ivSearch.setOnClickListener(onClickListener);
        ivRandom.setOnClickListener(onClickListener);
        tvPreview.setOnClickListener(onClickListener);
        tvDone.setOnClickListener(onClickListener);
        ivTop.setOnClickListener(onClickListener);
        ivBottom.setOnClickListener(onClickListener);

        //하단 뷰페이저 어댑터 설정
        viewPager = (ViewPager) findViewById(R.id.viewPager) ;
        viewPager.setOffscreenPageLimit(1); //캐싱을 해놓을 프래그먼트 개수

        pagerAdapter = new PagerAdapter_addCodi(getSupportFragmentManager()); //getSupportFragmentManager로 프래그먼트 참조가능

        //뷰페이저에 프래그먼트 설정
        pagerAdapter.addItem(new TabFragment_allClothes());
        pagerAdapter.addItem(new TabFragment_top());
        pagerAdapter.addItem(new TabFragment_bottom());
        pagerAdapter.addItem(new TabFragment_suit());
        pagerAdapter.addItem(new TabFragment_outer());
        pagerAdapter.addItem(new TabFragment_shoes());
        pagerAdapter.addItem(new TabFragment_bag());
        pagerAdapter.addItem(new TabFragment_accessory());
        viewPager.setAdapter(pagerAdapter);

    }


    class BtnOnClickListener implements Button.OnClickListener {

        RelativeLayout layoutMakeCodi = (RelativeLayout) findViewById(R.id.layout_makecodi);
        ImageView ivBottom = (ImageView) findViewById(R.id.iv_bottom);

        @Override
        public void onClick(View view) {

            Bitmap savedBitmap;

            switch (view.getId()) {
                case R.id.iv_search :
                    break ;
                case R.id.iv_random :
                    getApplication();
                    break ;
                case R.id.tv_preview : //미리보기 버튼 : 현재 코디 비트맵으로 저장한 후 보여주기
                    layoutMakeCodi.setDrawingCacheEnabled(false); //캐시를 지운다
                    layoutMakeCodi.setDrawingCacheEnabled(true); //캐시를 얻는다
                    layoutMakeCodi.buildDrawingCache(); //뷰 이미지를 Drawing cache에 저장
                    savedBitmap = layoutMakeCodi.getDrawingCache();
                    if (savedBitmap != null) {
                        ivBottom.setImageBitmap(savedBitmap.copy(savedBitmap.getConfig(), false));
                    }
                    break ;
                case R.id.tv_done :
                    layoutMakeCodi.setDrawingCacheEnabled(false); //캐시를 지운다
                    layoutMakeCodi.setDrawingCacheEnabled(true); //캐시를 얻는다
                    layoutMakeCodi.buildDrawingCache(); //뷰 이미지를 Drawing cache에 저장
                    savedBitmap = layoutMakeCodi.getDrawingCache();
                    break ;
            }
        }
    }







}