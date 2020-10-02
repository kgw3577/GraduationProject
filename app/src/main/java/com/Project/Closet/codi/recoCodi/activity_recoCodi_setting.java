package com.Project.Closet.codi.recoCodi;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.R;
import com.Project.Closet.activity_login;
import com.Project.Closet.codi.addCodi.Page_category;
import com.Project.Closet.codi.fragment_codi;
import com.Project.Closet.home.activity_home;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class activity_recoCodi_setting extends AppCompatActivity {

    int RECO_CODI = 255;
    Call<List<ClothesVO>> cloListCall;
    List<ClothesVO> clothesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reco_codi_setting);

        TextView header_title = findViewById(R.id.header_title);
        header_title.setText("코디 추천 설정");

        ScalableLayout sl_ok = findViewById(R.id.sl_ok);
        sl_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    clothesList = new networkTask().execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                //여기서 설정대로 수정 후 보내기


                Intent intent = new Intent(activity_recoCodi_setting.this, activity_recommendCodi.class);
                intent.putParcelableArrayListExtra("clothesList",(ArrayList<ClothesVO>) clothesList);
                startActivityForResult(intent, RECO_CODI);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RECO_CODI && resultCode == RESULT_OK){
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }





    public class networkTask extends AsyncTask<String, Void, List<ClothesVO>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }

        @Override
        protected List<ClothesVO> doInBackground(String... params) {
            String userID = MySharedPreferences.getInstanceOf(getApplicationContext()).getUserID();
            ClothesVO clothesFilter = new ClothesVO();
            clothesFilter.setLocation("private");
            cloListCall = ClothesService.getRetrofit(getApplicationContext()).searchClothesNoPage(clothesFilter, userID);

            try {
                return cloListCall.execute().body();
                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ClothesVO> clolist) {
            super.onPostExecute(clolist);
        }
    }




}