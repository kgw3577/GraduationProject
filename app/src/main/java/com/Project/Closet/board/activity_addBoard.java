package com.Project.Closet.board;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.R;
import com.Project.Closet.old.activity_closet;
import com.Project.Closet.old.activity_home3;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody.Part;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;

//import okhttp3.internal.concurrent.Task;


public class activity_addBoard extends AppCompatActivity {


    private final int CAMERA_CODE = 11;
    private final int GALLERY_CODE = 12;
    private final int CLOSET_CODE = 20;

    static final String TAG = "red";
    Uri uri = Uri.parse("content");
    String path;

    String imageType;
    String boardType;

    int a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_clothes);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(activity_addBoard.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        // Intent 가져오기.
        Intent intent = getIntent();
        Intent newIntent;

        // No 값을 int 타입에서 String 타입으로 변환하여 표시.
        imageType = intent.getStringExtra("imageType"); //new or closet
        boardType = intent.getStringExtra("boardType"); //clothes or codi

        if("new".equals(imageType))
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(activity_addBoard.this);
        else if("closet".equals(imageType) && "clothes".equals(boardType)){
            newIntent = new Intent(activity_addBoard.this, SelectClothes.class);
            startActivityForResult(newIntent, CLOSET_CODE);
        }
        else if("closet".equals(imageType) && "codi".equals(boardType)){
            newIntent = new Intent(activity_addBoard.this, SelectCodi.class);
            startActivityForResult(newIntent, CLOSET_CODE);
        }
    }

    public class UploadTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody;
            Part body;
            File file = new File(path);
            LinkedHashMap<String, RequestBody> mapRequestBody = new LinkedHashMap<String, RequestBody>();
            List<Part> arrBody = new ArrayList<>();

            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            mapRequestBody.put("file\"; filename=\"" + file.getName(), requestBody);
            mapRequestBody.put("category", RequestBody.create(MediaType.parse("text/plain"), params[0]));
            mapRequestBody.put("name", RequestBody.create(MediaType.parse("text/plain"), "한글 이름"));
            body = Part.createFormData("fileName", file.getName(), requestBody);
            arrBody.add(body);


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

                    //크기 줄여주기 (메모리 부족 오류 방지)
                    //double height=bitmap.getHeight();
                    //double width=bitmap.getWidth();
                    //Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, (int)Global.bitmapWidth, (int)(height/(width/Global.bitmapWidth)), true);

                    //임시 파일로 저장하기
                    final Context context = getApplicationContext();
                    String filename = "myTemp";
                    File tempFile = File.createTempFile(filename, null, context.getCacheDir());
                    FileOutputStream out = new FileOutputStream(tempFile);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG, 70 , out);  // 넘겨 받은 bitmap을 jpeg(손실압축)으로 저장해줌
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, out); //비손실 압축

                    out.close();
                    path = tempFile.getAbsolutePath(); //임시 파일 경로

                    final String[] Category = {""};
                    Button select = (Button) findViewById(R.id.select_category);
                    select.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity_addBoard.this);
                            final String[] items = getResources().getStringArray(R.array.Category);
                            final ArrayList<String> selectedItem  = new ArrayList<String>();
                            selectedItem.add(items[0]);

                            builder.setTitle("카테고리 선택");

                            builder.setSingleChoiceItems(R.array.Category, 0, new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int pos)
                                {
                                    selectedItem.clear();
                                    selectedItem.add(items[pos]);
                                }
                            });

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int pos)
                                {
                                    Category[0] = selectedItem.get(0);
                                    Toast toast = Toast.makeText(getApplicationContext(), "선택된 카테고리 : " + selectedItem.get(0), Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    switch(Category[0]){
                                        case "상의":
                                            Category[0] = "top";
                                            break;
                                        case "하의":
                                            Category[0] = "bottom";
                                            break;
                                        case "한벌옷":
                                            Category[0] = "suit";
                                            break;
                                        case "외투":
                                            Category[0] = "outer";
                                            break;
                                        case "신발":
                                            Category[0] = "shoes";
                                            break;
                                        case "가방":
                                            Category[0] = "bag";
                                            break;
                                        case "악세서리":
                                            Category[0] = "accessory";
                                            break;
                                    }
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    });

                    Button btn_ok = (Button) findViewById(R.id.ok);
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!Category[0].isEmpty()){
                                String res = null;
                                try {
                                    res = new UploadTask().execute(Category[0]).get();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                try{
                                    if (res.contains("ok")) {
                                    Toast.makeText(activity_addBoard.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                                    } else if (res.contains("fail")) {
                                    Toast.makeText(activity_addBoard.this, "업로드 실패", Toast.LENGTH_SHORT).show();
                                    }
                                    Intent intent = new Intent(getApplicationContext(), activity_closet.class);
                                    startActivity(intent);
                                }  catch (NullPointerException e) {
                                    e.printStackTrace();
                                    Toast.makeText(activity_addBoard.this, "업로드 오류", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), activity_home3.class);
                                    startActivity(intent);
                                }

                            }
                            else
                                Toast.makeText(activity_addBoard.this, "카테고리를 선택해야 합니다.", Toast.LENGTH_LONG).show();
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
            }
        }
        else if (requestCode == CLOSET_CODE && resultCode == RESULT_OK && intent.hasExtra("data")){
            //옷장에서 해당 비트맵을 가져옴. putextra로. 그리고 똑같이 전송함. png로.
            Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
        }
    }


}
