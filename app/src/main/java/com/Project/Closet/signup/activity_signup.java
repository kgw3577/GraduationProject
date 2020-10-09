package com.Project.Closet.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.Service.UserService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.HTTP.VO.UserVO;
import com.Project.Closet.R;
import com.Project.Closet.closet.closet_activities.TabFragment_Clothes_inClosetShare;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class activity_signup extends AppCompatActivity {

    EditText et_userID, et_pwd, et_pwdConfirm;
    Button joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BtnOnClickListener onClickListener = new BtnOnClickListener() ;
        setContentView(R.layout.layout_signup);
        Button btnSignup = findViewById(R.id.bt_join);
        btnSignup.setOnClickListener(onClickListener);


        final TextInputLayout input_userID = (TextInputLayout) findViewById(R.id.input_ID);
        input_userID.setCounterEnabled(true);
        input_userID.setCounterMaxLength(45);
        input_userID.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.length() == 0) {
                    input_userID.setError("아이디를 입력해야 합니다.");
                    input_userID.setErrorEnabled(true);
                } else {
                    input_userID.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        final TextInputLayout input_pwd = (TextInputLayout) findViewById(R.id.input_pwd);
        input_pwd.setCounterEnabled(true);
        input_pwd.setCounterMaxLength(45);
        input_pwd.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.length() == 0) {
                    input_pwd.setError("비밀번호를 입력해야 합니다.");
                    input_pwd.setErrorEnabled(true);
                } else {
                    input_pwd.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        final TextInputLayout input_pwd_confirm = (TextInputLayout) findViewById(R.id.input_pwd_confirm);
        input_pwd_confirm.setCounterEnabled(true);
        input_pwd_confirm.setCounterMaxLength(45);
        input_pwd_confirm.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.length() == 0) {
                    input_pwd_confirm.setError("비밀번호를 다시 한 번 입력해주세요.");
                    input_pwd_confirm.setErrorEnabled(true);
                } else {
                    input_pwd_confirm.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        et_userID = (EditText) findViewById(R.id.et_ID);
        et_pwd = (EditText) findViewById(R.id.et_Password);
        et_pwdConfirm = (EditText) findViewById(R.id.et_ConfirmPassword);





        joinBtn = (Button) findViewById(R.id.bt_join);
    }



    public class SignupTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {

            UserVO userVO = new UserVO(params[0], params[1]);//userID, pwd
            Call<String> stringCall = UserService.getRetrofit(getApplicationContext()).join(userVO);
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
                    String userID = et_userID.getText().toString();
                    String pwd = et_pwd.getText().toString();
                    String pwdConfirm = et_pwdConfirm.getText().toString();

                    if (userID.length() == 0 || pwd.length() == 0){
                        Toast.makeText(activity_signup.this,"필요한 항목이 모두 입력되지 않았습니다.",Toast.LENGTH_SHORT).show();
                    } else if(!pwd.equals(pwdConfirm)){
                        Toast.makeText(activity_signup.this,"비밀번호가 서로 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                    } else{

                        try {
                            String result  = new activity_signup.SignupTask().execute(userID, pwd).get();
                            if(result.equals("id")) {
                                Toast.makeText(activity_signup.this,"이미 존재하는 아이디입니다.",Toast.LENGTH_SHORT).show();
                                et_userID.setText("");
                            } else if(result.equals("email")) {
                                Toast.makeText(activity_signup.this, "이미 존재하는 이메일입니다.", Toast.LENGTH_SHORT).show();
                                et_userID.setText("");
                            }else if(result.equals("wrong email")) {
                                Toast.makeText(activity_signup.this, "잘못된 이메일 형식입니다.", Toast.LENGTH_SHORT).show();
                                et_userID.setText("");
                            }else if(result.equals("ok")) {


                                //기본 옷 추가
                                ClothesVO cloInfo1 = new ClothesVO(0,"private","상의","셔츠","클래식/드레스셔츠",
                                        "화이트","화이트_클래식/드레스셔츠",
                                        "클래식／드레스셔츠_화이트.jpg","/download/clothes?imageFileName=클래식／드레스셔츠_화이트.jpg",
                                        "no", userID,"default");
                                ClothesVO cloInfo2 = new ClothesVO(0,"private","하의","청바지","청바지",
                                        "블루","블루_청바지",
                                        "admin_1598175126824.jpg","/download/clothes?imageFileName=admin_1598175126824.jpg",
                                        "no", userID,"default");

                                String res = null;
                                try {


                                    res = new AddTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, cloInfo1).get(); //옷 추가 1
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Log.e("tag",res);


                                if("ok".equals(res)){
                                    try {


                                        res = new AddTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, cloInfo2).get(); //옷 추가2
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Log.e("tag",res);


                                    if("ok".equals(res)){
                                        Intent intent = new Intent(getApplicationContext(), activity_signup_next.class);
                                        intent.putExtra("userID", userID);
                                        startActivity(intent);
                                        return;
                                    }
                                }
                            }

                            Toast.makeText(activity_signup.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();

                        }catch (Exception e) {}
                    }
                    break;
            }
        }
    }

    public class AddTask extends AsyncTask<ClothesVO, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(ClothesVO... ClothesInfo) {

            Call<String> stringCall = ClothesService.getRetrofit(getApplicationContext()).addClothesFrData(ClothesInfo[0]);
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



}
