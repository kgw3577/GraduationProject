package com.Project.Closet.codi.recoCodi;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.Project.Closet.R;
import com.Project.Closet.activity_login;
import com.Project.Closet.codi.fragment_codi;
import com.Project.Closet.home.activity_home;
import com.ssomai.android.scalablelayout.ScalableLayout;

public class activity_recoCodi_setting extends AppCompatActivity {

    int RECO_CODI = 255;

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
                Intent intent = new Intent(activity_recoCodi_setting.this, activity_recommendCodi.class);
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
}