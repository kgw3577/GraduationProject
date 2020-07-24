package com.Project.Closet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class activity_signup extends AppCompatActivity {

    EditText userId, userPwd;
    Button joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BtnOnClickListener onClickListener = new BtnOnClickListener() ;
        setContentView(R.layout.layout_signup);
        Button btnSignup = findViewById(R.id.bt_join);
        btnSignup.setOnClickListener(onClickListener);

        userId = (EditText) findViewById(R.id.et_Email);
        userPwd = (EditText) findViewById(R.id.et_Password);
        joinBtn = (Button) findViewById(R.id.bt_join);
    }
    // 병합 테스트
    // 병합 테스트2
    class SignupTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;

                String rootURL = Global.baseURL;
                URL url = new URL(rootURL+"/user/join");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("POST");
                OutputStream os = conn.getOutputStream();
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("userID", strings[0]);
                jsonObject.accumulate("pwd", strings[1]);
                // convert JSONObject to JSON to String
                String json = jsonObject.toString();

                //sendMsg = "userID=" + strings[0] + "&pwd=" + strings[1] + "&type=" + strings[2];
                os.write(json.getBytes("UTF-8"));
                os.flush();
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return receiveMsg;
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
                case R.id.bt_join: // 회원가입 버튼 눌렀을 경우
                    String joinid = userId.getText().toString();
                    String joinpwd = userPwd.getText().toString();
                    try {
                        String result  = new activity_signup.SignupTask().execute(joinid, joinpwd, "join").get();
                        if(result.equals("id")) {
                            Toast.makeText(activity_signup.this,"이미 존재하는 아이디입니다.",Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            userPwd.setText("");
                        } else if(result.equals("email")) {
                            Toast.makeText(activity_signup.this, "이미 존재하는 이메일입니다.", Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            userPwd.setText("");
                        }else if(result.equals("wrong email")) {
                            Toast.makeText(activity_signup.this, "잘못된 이메일 형식입니다.", Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            userPwd.setText("");
                        }else if(result.equals("ok")) {
                            userId.setText("");
                            userPwd.setText("");
                            Toast.makeText(activity_signup.this,"회원가입을 축하합니다.",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e) {}
                    break;

                // 뒤로 돌아가는 버튼 추가하기S
            }
        }
    }
}
