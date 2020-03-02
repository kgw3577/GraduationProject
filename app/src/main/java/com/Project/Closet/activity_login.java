package com.Project.Closet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.net.*;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

        userId = (EditText) findViewById(R.id.et_Email);
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
            OkHttpClient client = new OkHttpClient();
            //String URL = "http://13.125.248.126:8080/Android/Login.jsp";                // AWS 서버
            //URL url = new URL("http://192.168.0.3:8080/Android/Login.jsp");    // 로컬 작업용 (192.168.0.208)

            String URL = "http://54.180.99.123:8080/Android/Login.jsp";
            RequestBody formBody = new FormBody.Builder()
                    .add("Content-Type", "application/x-www-form-urlencoded")
                    .add("id", params[0])
                    .add("pwd", params[1])
                    .add("type", params[2])
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .post(formBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
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
                /*
                    case R.id.loginBtn :
                    Intent intent = new Intent(getApplicationContext(), activity_home.class);
                    startActivity(intent);
                    break;
                 */
                case R.id.bt_login: // 로그인 버튼 눌렀을 경우
                    String loginid = userId.getText().toString();
                    String loginpwd = userPwd.getText().toString();
                    try {
                        String result = new LoginTask().execute(loginid, loginpwd, "login").get();
                        if (result.indexOf("true")>-1) {
                            Toast.makeText(activity_login.this, "로그인", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity_login.this, activity_home.class);
                            startActivity(intent);
                            //finish();
                        } else if (result.indexOf("false")>-1) {
                            Toast.makeText(activity_login.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            userPwd.setText("");
                        } /* else if(result.equals("noId")) {
                            Toast.makeText(activity_login.this, "존재하지 않는 아이디", Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            userPwd.setText("");
                        } */ else if (result.indexOf("email")>-1) {
                            Toast.makeText(activity_login.this, "잘못된 이메일 형식입니다.", Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            userPwd.setText("");
                        }
                    } catch (Exception e) {
                    }
                    break;
                case R.id.bt_signup: // 회원가입
                    Intent intent = new Intent(getApplicationContext(), activity_signup.class);
                    startActivity(intent);
                    break;
/*                  String joinid = userId.getText().toString();
                    String joinpwd = userPwd.getText().toString();
                    try {
                        String result  = new CustomTask().execute(joinid,joinpwd,"join").get();
                        if(result.equals("id")) {
                            Toast.makeText(activity_login.this,"이미 존재하는 아이디입니다.",Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            userPwd.setText("");
                        } else if(result.equals("ok")) {
                            userId.setText("");
                            userPwd.setText("");
                            Toast.makeText(activity_login.this,"회원가입을 축하합니다.",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e) {}
                    break;*/
            }
        }
    }
}