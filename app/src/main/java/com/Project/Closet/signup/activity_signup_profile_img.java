package com.Project.Closet.signup;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.UserService;
import com.Project.Closet.R;
import com.Project.Closet.activity_login;
import com.Project.Closet.util.Utils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class activity_signup_profile_img extends AppCompatActivity {

    String userID;
    ImageView iv_add_profile, iv_profileImage;
    Button joinBtn;

    private final int CAMERA_CODE = 11;
    private final int GALLERY_CODE = 12;
    static final String TAG = "red";
    Uri uri = Uri.parse("content");
    String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userID = getIntent().getExtras().getString("userID");

        BtnOnClickListener onClickListener = new BtnOnClickListener() ;
        setContentView(R.layout.layout_signup_next_next);

        iv_add_profile = (ImageView) findViewById(R.id.iv_add_profile);
        iv_profileImage = (ImageView) findViewById(R.id.iv_profileImage);

        iv_add_profile.setOnClickListener(onClickListener);

        joinBtn = (Button) findViewById(R.id.bt_join);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(activity_signup_profile_img.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }



    public class UploadTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {

            RequestBody requestBody;
            MultipartBody.Part body;
            File file = new File(path);
            LinkedHashMap<String, RequestBody> mapRequestBody = new LinkedHashMap<String, RequestBody>();
            List<MultipartBody.Part> arrBody = new ArrayList<>();

            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            mapRequestBody.put("file\"; filename=\"" + file.getName(), requestBody);
            mapRequestBody.put("userID", RequestBody.create(MediaType.parse("text/plain"), userID));
            body = MultipartBody.Part.createFormData("fileName", file.getName(), requestBody);
            arrBody.add(body);

            Call<String> stringCall = UserService.getRetrofit(getApplicationContext()).modifyProfileImage(mapRequestBody, arrBody);
            try {
                return stringCall.execute().body(); //웹서버에 이미지 보내고 응답 받기
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            try {
                uri = Uri.parse(intent.getDataString());
                Log.d(TAG, intent.getDataString());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());

            } catch(FileNotFoundException e) {
                e.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == CAMERA_CODE && resultCode == RESULT_OK && intent.hasExtra("data")){
            Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
        }
        else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(intent);
            if (resultCode == RESULT_OK) {
                iv_profileImage.setVisibility(View.VISIBLE);
                iv_add_profile.setVisibility(View.INVISIBLE);
                iv_profileImage.setImageURI(result.getUri());
                try{
                    //비트맵 파일 얻기
                    ImageView view = (ImageView)findViewById(R.id.iv_profileImage);
                    Bitmap bitmap = ((BitmapDrawable)view.getDrawable()).getBitmap();

                    //크기 줄여주기 (메모리 부족 오류 방지)
                    double height=bitmap.getHeight();
                    double width=bitmap.getWidth();
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, (int) Global.bitmapWidth, (int)(height/(width/Global.bitmapWidth)), true);

                    //임시 파일로 저장하기
                    final Context context = getApplicationContext();
                    String filename = "myTemp";
                    File tempFile = File.createTempFile(filename, null, context.getCacheDir());
                    FileOutputStream out = new FileOutputStream(tempFile);
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , out);  // 넘겨 받은 bitmap을 jpeg(손실압축)으로 저장해줌
                    out.close();
                    path = tempFile.getAbsolutePath(); //임시 파일 경로

                    iv_profileImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.CropImageSetting().start(activity_signup_profile_img.this);
                        }
                    });


                    joinBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                String result  = new activity_signup_profile_img.UploadTask().execute().get();
                                if(result.equals("ok")) {
                                    Intent intent = new Intent(getApplicationContext(), activity_signup_profile_contents.class);
                                    intent.putExtra("userID", userID);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(activity_signup_profile_img.this, result, Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e) {}
                        }
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            } else{
                finish();
            }
        }
    }


    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_add_profile : // 프로필 이미지 추가 버튼 눌렀을 경우
                    Utils.CropImageSetting().start(activity_signup_profile_img.this);
                    break;
            }
        }
    }
}
