package com.Project.Closet.codi.addCodi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.R;
import com.Project.Closet.codi.activity_codi_main;
import com.Project.Closet.old.activity_home3;

import java.io.File;
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


public class activity_sendCodi extends AppCompatActivity {

    Bitmap codiImage;
    public String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("confirm","sendCodi 액티비티 시작");
        super.onCreate(savedInstanceState);


        setContentView(R.layout.layout_add_clothes);
        final ImageView edit_iv = (ImageView) findViewById(R.id.add_image);

        Intent intent = getIntent();
        codiImage = (Bitmap) intent.getParcelableExtra("codiImage");
        ((ImageView) findViewById(R.id.add_image)).setImageBitmap(codiImage);


        //임시 파일로 저장하기
        final Context context = getApplicationContext();
        String filename = "myTemp";
        File tempFile = null;
        try {
            tempFile = File.createTempFile(filename, null, context.getCacheDir());
            FileOutputStream out = new FileOutputStream(tempFile);
            codiImage.compress(Bitmap.CompressFormat.JPEG, 70 , out);  // 넘겨 받은 bitmap을 jpeg(손실압축)으로 저장해줌
            out.close();
            path = tempFile.getAbsolutePath(); //임시 파일 경로
        } catch (IOException e) {
            e.printStackTrace();
        }


        final String[] Season = {""};
        Button select = (Button) findViewById(R.id.select_category);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_sendCodi.this);
                final String[] items = getResources().getStringArray(R.array.Season);
                final ArrayList<String> selectedItem = new ArrayList<String>();
                selectedItem.add(items[0]);

                builder.setTitle("코디 계절 선택");

                builder.setSingleChoiceItems(R.array.Season, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        selectedItem.clear();
                        selectedItem.add(items[pos]);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        Season[0] = selectedItem.get(0);
                        Toast toast = Toast.makeText(getApplicationContext(), "선택된 카테고리 : " + selectedItem.get(0), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        switch (Season[0]) {
                            case "봄":
                                Season[0] = "spring";
                                break;
                            case "여름":
                                Season[0] = "summer";
                                break;
                            case "가을":
                                Season[0] = "fall";
                                break;
                            case "겨울":
                                Season[0] = "winter";
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
                if (!Season[0].isEmpty()) {
                    String res = null;
                    try {
                        res = new UploadTask().execute(Season[0]).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (res.contains("ok")) {
                            Toast.makeText(activity_sendCodi.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                        } else if (res.contains("fail")) {
                            Toast.makeText(activity_sendCodi.this, "업로드 실패", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(getApplicationContext(), activity_codi_main.class);
                        startActivity(intent);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        Toast.makeText(activity_sendCodi.this, "업로드 오류", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), activity_home3.class);
                        startActivity(intent);
                    }
                } else
                    Toast.makeText(activity_sendCodi.this, "카테고리를 선택해야 합니다.", Toast.LENGTH_LONG).show();
            }
        });
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
            mapRequestBody.put("closetName", RequestBody.create(MediaType.parse("text/plain"), "default"));
            mapRequestBody.put("category", RequestBody.create(MediaType.parse("text/plain"), params[0]));
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


}




