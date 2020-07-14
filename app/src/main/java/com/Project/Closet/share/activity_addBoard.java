package com.Project.Closet.share;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.BoardService;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.R;
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
    static final String TAG = "lynnfield";
    Uri uri = Uri.parse("content");
    String path;
    String likeArray[] = {"yes","no"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_board);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(activity_addBoard.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        final ImageView edit_iv = (ImageView) findViewById(R.id.add_image);
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(activity_addBoard.this);
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
            mapRequestBody.put("boardType", RequestBody.create(MediaType.parse("text/plain"), params[0]));
            mapRequestBody.put("subject", RequestBody.create(MediaType.parse("text/plain"), params[1]));
            mapRequestBody.put("contents", RequestBody.create(MediaType.parse("text/plain"), params[2]));
            body = Part.createFormData("fileName", file.getName(), requestBody);
            arrBody.add(body);

            Call<String> stringCall = BoardService.getRetrofit(getApplicationContext()).addBoard(mapRequestBody, arrBody);
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
                    double height=bitmap.getHeight();
                    double width=bitmap.getWidth();
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, (int) Global.bitmapWidth, (int)(height/(width/Global.bitmapWidth)), true);

                    //임시 파일로 저장하기
                    final Context context = getApplicationContext();
                    String filename = "myTemp";
                    File tempFile = File.createTempFile(filename, null, context.getCacheDir());
                    FileOutputStream out = new FileOutputStream(tempFile);
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 70 , out);  // 넘겨 받은 bitmap을 jpeg(손실압축)으로 저장해줌
                    out.close();
                    path = tempFile.getAbsolutePath(); //임시 파일 경로

                    final String[] Category = {""};
                    Button select = (Button) findViewById(R.id.select_category);
                    select.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity_addBoard.this);
                            final String[] items = getResources().getStringArray(R.array.Board_Category);
                            final ArrayList<String> selectedItem  = new ArrayList<String>();
                            selectedItem.add(items[0]);

                            builder.setTitle("카테고리 선택");

                            builder.setSingleChoiceItems(R.array.Board_Category, 0, new DialogInterface.OnClickListener(){
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
                                        case "옷":
                                            Category[0] = "clothes";
                                            break;
                                        case "코디":
                                            Category[0] = "codi";
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

                                EditText et_subject = (EditText) findViewById(R.id.tv_subject);
                                EditText et_contents = (EditText) findViewById(R.id.tv_contents);
                                String subject = et_subject.getText().toString();
                                String contents = et_contents.getText().toString();

                                String res = null;
                                try {
                                    res = new UploadTask().execute(Category[0], subject, contents).get();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                Intent intent = new Intent();

                                try{
                                    if (res.contains("ok")) {
                                        Toast.makeText(activity_addBoard.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    } else if (res.contains("fail")) {
                                        Toast.makeText(activity_addBoard.this, "업로드 실패", Toast.LENGTH_SHORT).show();
                                        setResult(RESULT_CANCELED, intent);
                                        finish();
                                    }
                                }  catch (NullPointerException e) {
                                    e.printStackTrace();
                                    Toast.makeText(activity_addBoard.this, "업로드 오류", Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_CANCELED, intent);
                                    finish();
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
    }


}
