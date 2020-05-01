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
import com.Project.Closet.HTTP.VO.UserVO;

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

            UserVO userVO = new UserVO(params[0], params[1]);//id, pwd
            Call<String> stringCall = UserService.getRetrofit(getApplicationContext()).login(userVO);
            try {
                return stringCall.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        /*
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", params[0]);
                jsonObject.put("pwd", params[1]);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                RequestBody body = RequestBody.create(jsonObject.toString(),JSON);
                Request request = new Request.Builder()
                        .addHeader("description", "")
                        .url(URL)
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();



                return response.body().string();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        */
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