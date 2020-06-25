package com.Project.Closet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.Project.Closet.HTTP.Service.ClothesService;
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
import okhttp3.MultipartBody.*;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;

//import okhttp3.internal.concurrent.Task;


public class activity_addClothes extends AppCompatActivity {

    private final int CAMERA_CODE = 11;
    private final int GALLERY_CODE = 12;
    static final String TAG = "lynnfield";
    Uri uri = Uri.parse("content");
    String path;

    String categoryArray[] = {"top", "bottom", "suit", "outer", "shoes", "bag", "accessory"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_clothes);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(activity_addClothes.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        final ImageView edit_iv = (ImageView) findViewById(R.id.add_image);
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(activity_addClothes.this);

    }

    public class UploadTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody;
            MultipartBody.Part body;
            File file = new File(path);
            LinkedHashMap<String, RequestBody> mapRequestBody = new LinkedHashMap<String, RequestBody>();
            List<Part> arrBody = new ArrayList<>();
            int randomCateNum = (int) (Math.random() * 7);


            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            mapRequestBody.put("file\"; filename=\"" + file.getName(), requestBody);
            mapRequestBody.put("closetName", RequestBody.create(MediaType.parse("text/plain"), "default")); //필수
            mapRequestBody.put("name", RequestBody.create(MediaType.parse("text/plain"), "brown skirt"));
            mapRequestBody.put("category", RequestBody.create(MediaType.parse("text/plain"), categoryArray[randomCateNum]));
            mapRequestBody.put("brand", RequestBody.create(MediaType.parse("text/plain"), "zara"));
            mapRequestBody.put("color", RequestBody.create(MediaType.parse("text/plain"), "brown"));
            mapRequestBody.put("date", RequestBody.create(MediaType.parse("text/plain"), "200430"));
            mapRequestBody.put("season", RequestBody.create(MediaType.parse("text/plain"), "spring"));
            mapRequestBody.put("cloSize", RequestBody.create(MediaType.parse("text/plain"), "s"));
            //mapRequestBody.put("filename", RequestBody.create(MediaType.parse("text/plain"), "brown skirt.jpg"));

            body = MultipartBody.Part.createFormData("fileName", file.getName(), requestBody);
            arrBody.add(body);


            //딥러닝 서버로 전송. (addClothesDeep)
            Call<String> stringCall = ClothesService.getRetrofit(getApplicationContext()).addClothes(mapRequestBody, arrBody);
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
                ((ImageView)findViewById(R.id.add_image)).setImageURI(result.getUri());
                try{
                    //비트맵 파일 얻기
                    ImageView view = (ImageView)findViewById(R.id.add_image);
                    Bitmap bitmap = ((BitmapDrawable)view.getDrawable()).getBitmap();

                    //임시 파일로 저장하기
                    Context context = getApplicationContext();
                    String filename = "myTemp";
                    File tempFile = File.createTempFile(filename, null, context.getCacheDir());
                    FileOutputStream out = new FileOutputStream(tempFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90 , out);  // 넘겨 받은 bitmap을 jpeg(손실압축)으로 저장해줌
                    out.close();
                    path = tempFile.getAbsolutePath(); //임시 파일 경로

                    //(마우스 클릭됐을 때 실행 되도록 바꿀 것)
                    String res = new UploadTask().execute().get();

                    if (res.indexOf("true") > -1) {
                        Toast.makeText(activity_addClothes.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                    } else if (res.indexOf("false") > -1) {
                        Toast.makeText(activity_addClothes.this, "업로드 실패", Toast.LENGTH_SHORT).show();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
/*                Bitmap bitmap = result.getBitmap();
                ((ImageView)findViewById(R.id.edit_iv)).setImageBitmap(bitmap);*/
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


}
