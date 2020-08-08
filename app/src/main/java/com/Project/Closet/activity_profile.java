package com.Project.Closet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;

public class activity_profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);

        Button bt_logout = findViewById(R.id.bt_logout);
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreferences pref = MySharedPreferences.getInstanceOf(getApplicationContext());
                pref.setUserID("");
                startActivity(new Intent(activity_profile.this,activity_login.class));
                ActivityCompat.finishAffinity(activity_profile.this);
            }
        });
    }
}