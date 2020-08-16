package com.Project.Closet.closet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.R;
import com.Project.Closet.util.Utils;
import com.ssomai.android.scalablelayout.ScalableLayout;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.*;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;

import petrov.kristiyan.colorpicker.ColorPicker;

//import okhttp3.internal.concurrent.Task;


public class activity_addClothes extends AppCompatActivity {

    private final int CAMERA_CODE = 11;
    private final int GALLERY_CODE = 12;
    static final String TAG = "lynnfield";
    Uri uri = Uri.parse("content");
    String path;
    String likeArray[] = {"yes","no"};

    public LinearLayout ll_detailcategory;
    public LinearLayout Cloth_Info_edit;
    public ScalableLayout sl_ok;

    public TextView tv_edit_category;
    public TextView tv_edit_detailcategory;
    public TextView tv_edit_season;
    public TextView tv_edit_buyDate;
    public TextView tv_edit_color;
    public TextView tv_edit_brand;
    public TextView tv_edit_size;

    ImageView warning;
    ImageView warning2;

    String category="";
    String detail_category="";
    String color="";
    String season="";
    String brand="";
    String cloSize ="";
    String buyDate ="";

    String location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_clothes);

        location = getIntent().getExtras().getString("location");

        Cloth_Info_edit = (LinearLayout) findViewById(R.id.cloth_info_edit);
        sl_ok = findViewById(R.id.sl_ok);

        warning = findViewById(R.id.warning);
        warning2 = findViewById(R.id.warning2);

        //카테고리 선택 설정
        tv_edit_category = (TextView) findViewById(R.id.tv_edit_catergory);
        final String[] Category = {""};
        tv_edit_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_addClothes.this);
                final String[] items = getResources().getStringArray(R.array.Kind);
                final ArrayList<String> selectedItem  = new ArrayList<String>();
                selectedItem.add(items[0]);

                builder.setTitle("카테고리 선택");

                builder.setSingleChoiceItems(R.array.Kind, 0, new DialogInterface.OnClickListener(){
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
                        tv_edit_category.setText(Category[0]);
                        tv_edit_category.setTextColor(Color.parseColor("#000000"));
                        category = Category[0];
                        warning.setVisibility(View.GONE);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        tv_edit_detailcategory = (TextView) findViewById(R.id.tv_edit_detailcategory);

        //컬러 선택 설정 - 컬러피커
        tv_edit_color = (TextView) findViewById(R.id.tv_edit_color);
        tv_edit_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        //계절 선택 설정
        tv_edit_season = (TextView) findViewById(R.id.tv_edit_season);
        final String[] Season = {""};
        tv_edit_season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_addClothes.this);
                final String[] items = getResources().getStringArray(R.array.Season);
                final ArrayList<String> selectedItem  = new ArrayList<String>();
                selectedItem.add(items[0]);

                builder.setTitle("계절 선택");

                builder.setSingleChoiceItems(R.array.Season, 0, new DialogInterface.OnClickListener(){
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
                        Season[0] = selectedItem.get(0);
                        tv_edit_season.setText(Season[0]);
                        season= Season[0];
                        tv_edit_season.setTextColor(Color.parseColor("#000000"));
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        tv_edit_brand = (TextView) findViewById(R.id.tv_edit_brand);
        tv_edit_size = (TextView) findViewById(R.id.tv_edit_size);

        //구입일 선택 설정 - 데이트 피커
        tv_edit_buyDate = (TextView) findViewById(R.id.tv_edit_date);
        tv_edit_buyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                //현재 년도, 월, 일
                int year = cal.get (Calendar.YEAR);
                int month = cal.get (Calendar.MONTH);
                int date = cal.get (Calendar.DATE) ;

                DatePickerDialog dialog = new DatePickerDialog(activity_addClothes.this, listener, year, month, date);
                dialog.show();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(activity_addClothes.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        final ImageView edit_iv = (ImageView) findViewById(R.id.add_image);
        Utils.CropImageSetting().start(activity_addClothes.this);
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
            MultipartBody.Part body;
            File file = new File(path);
            LinkedHashMap<String, RequestBody> mapRequestBody = new LinkedHashMap<String, RequestBody>();
            List<Part> arrBody = new ArrayList<>();


            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            mapRequestBody.put("userID", RequestBody.create(MediaType.parse("text/plain"), MySharedPreferences.getInstanceOf(getApplicationContext()).getUserID()));
            mapRequestBody.put("file\"; filename=\"" + file.getName(), requestBody);
            mapRequestBody.put("closetName", RequestBody.create(MediaType.parse("text/plain"), "default"));

            mapRequestBody.put("location", RequestBody.create(MediaType.parse("text/plain"),location));
            mapRequestBody.put("category", RequestBody.create(MediaType.parse("text/plain"),category));
            mapRequestBody.put("detailCategory", RequestBody.create(MediaType.parse("text/plain"),detail_category));
            mapRequestBody.put("color", RequestBody.create(MediaType.parse("text/plain"),color));
            if(!season.isEmpty())
                mapRequestBody.put("season", RequestBody.create(MediaType.parse("text/plain"),season));
            mapRequestBody.put("brand", RequestBody.create(MediaType.parse("text/plain"),brand));
            mapRequestBody.put("cloSize", RequestBody.create(MediaType.parse("text/plain"), cloSize));
            if(!buyDate.isEmpty())
                mapRequestBody.put("buyDate", RequestBody.create(MediaType.parse("text/plain"), buyDate));

            body = MultipartBody.Part.createFormData("fileName", file.getName(), requestBody);
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

                    ScalableLayout sl_ok = findViewById(R.id.sl_ok);
                    sl_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!category.isEmpty() && !color.isEmpty()){
                                detail_category = tv_edit_detailcategory.getText().toString();
                                brand = tv_edit_brand.getText().toString();
                                cloSize = tv_edit_size.getText().toString();

                                String res = null;
                                try {
                                    res = new UploadTask().execute().get();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                Intent intent = new Intent();

                                try{
                                    if (res.contains("ok")) {
                                        Toast.makeText(activity_addClothes.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    } else if (res.contains("fail")) {
                                        Toast.makeText(activity_addClothes.this, "업로드 실패", Toast.LENGTH_SHORT).show();
                                        setResult(RESULT_CANCELED, intent);
                                        finish();
                                    }
                                }  catch (NullPointerException e) {
                                    e.printStackTrace();
                                    Toast.makeText(activity_addClothes.this, "업로드 오류", Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_CANCELED, intent);
                                    finish();
                                }
                            }
                            else{
                                if(category.equals("선택")){
                                    warning.setVisibility(View.VISIBLE);
                                }
                                if(color.equals("선택")){
                                    warning2.setVisibility(View.VISIBLE);
                                }

                            }

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

    public void openColorPicker() {
        final ColorPicker colorPicker = new ColorPicker(this);  // ColorPicker 객체 생성
        ArrayList<String> colors = new ArrayList<>();  // Color 넣어줄 list

        final Utils colorUtil = new Utils();
        colorUtil.setColorUtil(getApplicationContext());

        for(int i=0; i<colorUtil.color_name.length; i++){
            colors.add(colorUtil.mapColors.get(colorUtil.color_name[i]));
        }

        colorPicker.setColors(colors)  // 만들어둔 list 적용
                .setColumns(5)  // 5열로 설정
                .setRoundColorButton(true)  // 원형 버튼으로 설정
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int colorInt) {

                        color = Utils.getKey(colorUtil.mapColors,String.format("#%06X", 0xFFFFFF & colorInt));
                        tv_edit_color.setText(color);
                        tv_edit_color.setTextColor(Color.parseColor("#000000"));
                        warning2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancel() {
                        // Cancel 버튼 클릭 시 이벤트
                    }
                }).show();  // dialog 생성
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            buyDate = String.format("%d-%02d-%02d",year,monthOfYear+1,dayOfMonth);
            tv_edit_buyDate.setText(buyDate);
        }
    };



}
