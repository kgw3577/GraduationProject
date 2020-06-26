package com.Project.Closet.codi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Project.Closet.R;

public class activity_addCodi extends AppCompatActivity {

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