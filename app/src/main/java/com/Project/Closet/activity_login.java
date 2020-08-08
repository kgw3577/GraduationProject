package com.Project.Closet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

import java.io.*;

import androidx.appcompat.app.AppCompatActivity;

import com.Project.Closet.HTTP.Service.UserService;
import com.Project.Closet.HTTP.Session.preference.CookieSharedPreferences;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.UserVO;
import com.Project.Closet.home.activity_home;
import com.Project.Closet.signup.activity_signup;

import retrofit2.Call;

public class activity_login extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    int count = 0;

    EditText userId, userPwd;
    Button loginBtn, joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        BtnOnClickListener onClickListener = new BtnOnClickListener();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_login);
        Button btnSignup = findViewById(R.id.bt_signup);
        btnSignup.setOnClickListener(onClickListener);
        Button btnLogin = findViewById(R.id.bt_login);
        btnLogin.setOnClickListener(onClickListener);

        userId = (EditText) findViewById(R.id.et_ID);
        userPwd = (EditText) findViewById(R.id.et_Password);
        loginBtn = (Button) findViewById(R.id.bt_login);
        joinBtn = (Button) findViewById(R.id.bt_join);
    }


    public class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {

            UserVO userVO = new UserVO(params[0], params[1]);//id, pwd
            Call<String> stringCall = UserService.getRetrofit(getApplicationContext()).login(userVO);
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

                case R.id.bt_login: // 로그인 버튼 눌렀을 경우
                    String loginid = userId.getText().toString();
                    String loginpwd = userPwd.getText().toString();
                    try {
                        String result = new LoginTask().execute(loginid, loginpwd, "login").get();
                        if (result.contains("true")) {
                            Toast.makeText(activity_login.this, "로그인", Toast.LENGTH_SHORT).show();
                            MySharedPreferences pref = MySharedPreferences.getInstanceOf(getApplicationContext());
                            pref.setUserID(loginid);
                            Intent intent = new Intent(activity_login.this, activity_home.class);
                            startActivity(intent);
                            finish();
                        } else if (result.contains("false")) {
                            Toast.makeText(activity_login.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            userPwd.setText("");
                        } else if (result.contains("email")) {
                            Toast.makeText(activity_login.this, "잘못된 이메일 형식입니다.", Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            userPwd.setText("");
                        }
                    } catch (Exception ignored) {
                    }
                    break;
                case R.id.bt_signup: // 회원가입
                    Intent intent = new Intent(getApplicationContext(), activity_signup.class);
                    startActivity(intent);
                    break;
            }
        }
    }

}