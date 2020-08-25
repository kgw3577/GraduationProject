package com.Project.Closet.social.addFeed;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.BoardService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.R;
import com.Project.Closet.closet.addClothes.activity_addClothes;
import com.Project.Closet.closet.closet_activities.activity_closet_DB;
import com.Project.Closet.util.Utils;
import com.ssomai.android.scalablelayout.ScalableLayout;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody.Part;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;

//import okhttp3.internal.concurrent.Task;


public class activity_addBoard extends AppCompatActivity implements View.OnClickListener {

    private static final int ADD_CLOTHES = 999;
    private static final int FROM_CLOSET = 1009;
    private static final int FROM_SHARE = 1010;
    private final int CAMERA_CODE = 11;
    private final int GALLERY_CODE = 12;
    static final String TAG = "lynnfield";
    Uri uri = Uri.parse("content");
    String path;


    SlidingDrawer slidingDrawer;
    LinearLayout drawer_content;

    EditText tv_contents;

    TextView tv_add_image;
    TextView tv_from_closet;
    TextView tv_from_share;
    TextView tv_cancel;

    ArrayList<ImageView> list_childClothes;
    int[] child_clothes_no;
    ArrayList<Integer> index_resourceID;
    int selected_clo_index;


    ImageView child1;
    ImageView child2;
    ImageView child3;
    ImageView child4;
    ImageView child5;
    ImageView child6;
    ImageView child7;
    ImageView child8;
    ImageView child9;
    ImageView child10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_board);

        TextView header_title = findViewById(R.id.header_title);
        header_title.setText("새 피드");

        tv_contents = findViewById(R.id.tv_contents);


        //드로워 관련
        slidingDrawer = findViewById(R.id.sliding_drawer);
        drawer_content = findViewById(R.id.drawer_content);

        tv_add_image = findViewById(R.id.tv_add_image);
        tv_from_closet= findViewById(R.id.tv_from_closet);
        tv_from_share = findViewById(R.id.tv_from_share);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_add_image.setOnClickListener(this);
        tv_from_closet.setOnClickListener(this);
        tv_from_share.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        drawer_content.setOnClickListener(this);





        //자식 옷 관련
        child1 = findViewById(R.id.child1);
        child2 = findViewById(R.id.child2);
        child3 = findViewById(R.id.child3);
        child4 = findViewById(R.id.child4);
        child5 = findViewById(R.id.child5);
        child6 = findViewById(R.id.child6);
        child7 = findViewById(R.id.child7);
        child8 = findViewById(R.id.child8);
        child9 = findViewById(R.id.child9);
        child10 = findViewById(R.id.child10);

        list_childClothes = new ArrayList<ImageView>(Arrays.asList(child1, child2, child3, child4, child5, child6, child7, child8, child9, child10));
        child_clothes_no =new int[10]; //옷 no 저장
        index_resourceID = new ArrayList<Integer>(Arrays.asList(R.id.child1, R.id.child2, R.id.child3, R.id.child4, R.id.child5, R.id.child6, R.id.child7, R.id.child8, R.id.child9, R.id.child10));

        for(ImageView v : list_childClothes){
            v.setOnClickListener(this);
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(activity_addBoard.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        final ImageView edit_iv = (ImageView) findViewById(R.id.add_image);

        Utils.CropImageSetting().start(activity_addBoard.this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Integer resourceID = v.getId();
        switch(v.getId()){
            case R.id.child1 :
            case R.id.child2 :
            case R.id.child3 :
            case R.id.child4 :
            case R.id.child5 :
            case R.id.child6 :
            case R.id.child7 :
            case R.id.child8 :
            case R.id.child9 :
            case R.id.child10 :
                selected_clo_index= index_resourceID.indexOf(resourceID);
                slidingDrawer.open();
                break;
            case R.id.drawer_content :
                slidingDrawer.close();
                break;
            case R.id.tv_add_image :
                intent = new Intent(this, activity_addClothes.class);
                intent.putExtra("location","private");
                startActivityForResult(intent, ADD_CLOTHES);
                slidingDrawer.close();
                break;
            case R.id.tv_from_closet :
                //share와 똑같이. mode 만들고 result 받아오기
                intent = new Intent(this, activity_closet_DB.class);
                intent.putExtra("mode","select_my");
                startActivityForResult(intent, FROM_CLOSET);
                slidingDrawer.close();
                break;
            case R.id.tv_from_share :
                intent = new Intent(this, activity_closet_DB.class);
                intent.putExtra("mode","select");
                startActivityForResult(intent, FROM_SHARE);
                slidingDrawer.close();
                break;
            case R.id.tv_cancel :
                resetCurrentItem();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (slidingDrawer.isOpened()) {
            slidingDrawer.close();
        }else{
            super.onBackPressed();
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
            mapRequestBody.put("userID", RequestBody.create(MediaType.parse("text/plain"), MySharedPreferences.getInstanceOf(getApplicationContext()).getUserID()));
            mapRequestBody.put("file\"; filename=\"" + file.getName(), requestBody);
            mapRequestBody.put("contents", RequestBody.create(MediaType.parse("text/plain"), params[0]));


            for(int i=0; i<child_clothes_no.length;i++){
                if(child_clothes_no[i]!=0)
                    mapRequestBody.put("child"+i+"_cloNo", RequestBody.create(MediaType.parse("text/plain"), Integer.toString(child_clothes_no[i]) ));
            }

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
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , out);  // 넘겨 받은 bitmap을 jpeg(손실압축)으로 저장해줌
                    out.close();
                    path = tempFile.getAbsolutePath(); //임시 파일 경로


                    ScalableLayout sl_ok = findViewById(R.id.sl_ok);
                    sl_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText et_contents = (EditText) findViewById(R.id.tv_contents);
                            String contents = et_contents.getText().toString();

                            String res = null;
                            try {
                                res = new UploadTask().execute(contents).get();
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
        } else if(requestCode == ADD_CLOTHES && resultCode == RESULT_OK){




        } else if(requestCode == FROM_CLOSET && resultCode == RESULT_OK||
                requestCode == FROM_SHARE && resultCode == RESULT_OK){
            //데이터 받아오기
            Bundle extras = intent.getExtras();
            String cloNo = extras.getString("cloNo");
            boolean isExist=false;

            for (int i=0;i<10;i++){
                if(i==selected_clo_index)
                    continue;
                if(Integer.parseInt(cloNo)==child_clothes_no[i]){
                    isExist=true;
                    Toast.makeText(this, "중복 아이템입니다.", Toast.LENGTH_SHORT).show();
                }
            }

            if(!isExist){
                String identifier = extras.getString("identifier");
                byte[] byteArray = intent.getByteArrayExtra("image");
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                //태그 추가
                tv_contents.append(" #"+identifier);
                tv_contents.setSelection(tv_contents.length());
                //이미지 설정
                list_childClothes.get(selected_clo_index).setImageBitmap(bitmap);
                //옷 번호 저장
                child_clothes_no[selected_clo_index] = Integer.parseInt(cloNo);
            }
        }

    }

    void resetCurrentItem(){
        //이미지 리셋
        list_childClothes.get(selected_clo_index).setImageResource(R.drawable.hanger_gray_small);
        //옷 번호 리셋
        child_clothes_no[selected_clo_index] = 0;
        slidingDrawer.close();
    }

}
