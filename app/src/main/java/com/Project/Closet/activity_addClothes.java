package com.Project.Closet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.concurrent.Task;

public class activity_addClothes extends AppCompatActivity {

    private final int CAMERA_CODE = 11;
    private final int GALLERY_CODE = 12;
    private Uri photoUri;
    private String currentPhotoPath;//실제 사진 파일 경로
    String mImageCaptureName;//이미지 이름
    static final String TAG = "lynnfield";
    Uri uri = Uri.parse("content");
    String path;

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

/*    public boolean shouldOverrideUrlLoading() {

        final String items[] = {"갤러리에서 가져오기","카메라로 촬영하기"};
        new AlertDialog.Builder(activity_gallery.this)
                .setIcon(R.drawable.ic_launcher_background)
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        dialog.dismiss();

                        if (item == 0) {//갤러리 호출
                            Uri uri = Uri.parse("content://media/external/images/media");
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(intent, GALLERY_CODE);

                        } else if (item == 1) { //카메라로 찍기
                                *//*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);*//*
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_CODE);

                        }
                    }
                }).show();
        return true;
    }*/



    public class UploadTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            //String URL = "http://192.168.0.3:8080/closet/upload/windows"; // 로컬 작업용
            String URL = "http://54.180.99.123:8080/Closet/upload/clothes"; // AWS 서버

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("title", "clothes")
                    .addFormDataPart("photo","clothes_image.jpg", RequestBody.create(MultipartBody.FORM, new File(path)))
                    .addFormDataPart("color", "blue") //파라미터값 : 데이터베이스 변수명으로 바꿀 것
                    .addFormDataPart("category", "pants")
                    .build();

            Request request = new Request.Builder()
                    .url(URL)
                    .post(requestBody)
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
