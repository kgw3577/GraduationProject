package com.Project.Closet.signup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Project.Closet.HTTP.Service.UserService;
import com.Project.Closet.HTTP.VO.UserVO;
import com.Project.Closet.R;
import com.Project.Closet.activity_login;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import retrofit2.Call;

public class activity_signup_profile_contents extends AppCompatActivity {

    String userID;
    EditText tv_profile_contents;
    Button joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userID = getIntent().getExtras().getString("userID");

        BtnOnClickListener onClickListener = new BtnOnClickListener() ;
        setContentView(R.layout.layout_signup_profile_contents);

        tv_profile_contents = (EditText) findViewById(R.id.tv_profile_contents);
        joinBtn = (Button) findViewById(R.id.bt_join);
        joinBtn.setOnClickListener(onClickListener);

    }



    public class UpdateTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {


            UserVO userInfo = new UserVO(userID);//params : 프로필 컨텐츠
            userInfo.setPfContents(params[0]);

            Call<String> stringCall = UserService.getRetrofit(getApplicationContext()).modify(userInfo);
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








    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_join: // 회원가입 버튼 눌렀을 경우
                    String profile_contents = tv_profile_contents.getText().toString();

                    try {
                        String result  = new activity_signup_profile_contents.UpdateTask().execute(profile_contents).get();
                        if(result.equals("ok")) {
                            Toast.makeText(activity_signup_profile_contents.this,"회원가입이 완료되었습니다!",Toast.LENGTH_SHORT).show();
                            finishAffinity();
                            Intent intent = new Intent(getApplicationContext(), activity_login.class); //로그인 자동으로
                            startActivity(intent);
                        }else{
                            Toast.makeText(activity_signup_profile_contents.this, result, Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e) {}
                    break;
            }
        }
    }
}
