package com.Project.Closet.closet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.HTTP.VO.DetailFeedVO;
import com.Project.Closet.R;
import com.Project.Closet.closet.addClothes.activity_addClothes;
import com.Project.Closet.closet.closet_activities.activity_closet_share;
import com.Project.Closet.social.addFeed.activity_addBoard;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class activity_cloInfo extends AppCompatActivity {

    DetailFeedVO cloInfoVO;
    String mode;

    LinearLayout ll_detail;
    public ImageView iv_image;

    public TextView tv_category;
    public TextView tv_detailcategory;
    public TextView tv_color;
    public TextView tv_season;
    public TextView tv_brand;

    //public ImageView iv_heart;
    //public ImageView iv_modify;
    //public ImageView iv_delete;
    public TextView tv_cloNo;
    public TextView tv_cloFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_info);
        cloInfoVO = getIntent().getExtras().getParcelable("cloInfo");

        ll_detail = findViewById(R.id.ll_detail);

        try{
            mode = getIntent().getExtras().getString("mode");
        }catch(NullPointerException e){
            e.printStackTrace();
        }

        if(mode==null)
            mode = "social"; //my, social


        ll_detail = findViewById(R.id.ll_detail);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        tv_category = (TextView) findViewById(R.id.tv_info_catergory);
        tv_detailcategory = (TextView) findViewById(R.id.tv_info_detailcategory);
        tv_color = (TextView) findViewById(R.id.tv_info_color);
        tv_season = (TextView) findViewById(R.id.tv_info_season);
        tv_brand = (TextView) findViewById(R.id.tv_info_brand);

        //iv_heart = (ImageView) findViewById(R.id.iv_heart);
        //iv_modify = (ImageView) findViewById(R.id.iv_modify);
        //iv_delete = (ImageView) findViewById(R.id.iv_delete);

        tv_cloNo = (TextView) findViewById(R.id.tv_clothes_no);
        tv_cloFavorite = (TextView) findViewById(R.id.tv_clothes_favorite);


        //BtnOnClickListener onClickListener = new BtnOnClickListener();
        //iv_heart.setOnClickListener(onClickListener);
        //iv_modify.setOnClickListener(onClickListener);
        //iv_delete.setOnClickListener(onClickListener);




        String ImageUrl = Global.baseURL+cloInfoVO.getCloImagePath();

        Glide.with((iv_image).getContext()).load(ImageUrl).into(iv_image);

        String category = cloInfoVO.getCloCategory();
        String detailCategory = cloInfoVO.getCloDetailCategory();
        tv_category.setText(category);
        if(category.equals(detailCategory))
            ll_detail.setVisibility(View.GONE);
        else{
            ll_detail.setVisibility(View.VISIBLE);
            tv_detailcategory.setText(detailCategory);
        }
        tv_color.setText(cloInfoVO.getCloColor());
        tv_season.setText(cloInfoVO.getCloSeason());
        tv_brand.setText(cloInfoVO.getCloBrand());
        tv_cloNo.setText(cloInfoVO.getCloNo());


        switch(mode){
            case "social": //소셜 모드일 때
                LinearLayout ll_closet_iconSet = findViewById(R.id.ll_closet_iconSet);
                ll_closet_iconSet.setVisibility(View.GONE);
                LinearLayout ll_share_iconSet = findViewById(R.id.ll_share_iconSet);
                ll_share_iconSet.setVisibility(View.VISIBLE);

                ImageView iv_inbox = findViewById(R.id.iv_inbox);
                iv_inbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //cloInfo를 보내서 db 추가 요청 - 복사?
                        //그 후 옷장을 refresh 하기
                        //토스트


                    }
                });
                break;
        }

    }



}