package com.Project.Closet.codi.addCodi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.Service.CodiService;
import com.Project.Closet.R;
import com.Project.Closet.closet.activity_closet;
import com.Project.Closet.codi.activity_codi_main;
import com.Project.Closet.home.activity_home;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;

public class activity_addCodi extends AppCompatActivity implements Page_allClothes.FragListener, Page_top.FragListener, Page_bottom.FragListener
    , Page_suit.FragListener, Page_outer.FragListener, Page_shoes.FragListener, Page_bag.FragListener, Page_accessory.FragListener {

    class Category {
        final static int ALL = 0;
        final static int TOP = 1;
        final static int BOTTOM = 2;
        final static int SUIT = 3;
        final static int OUTER = 4;
        final static int SHOES = 5;
        final static int BAG = 6;
        final static int ACCESSORY = 7;
    }

    //뷰페이저 선언
    private ViewPager viewPager;
    private PagerAdapter_addCodi pagerAdapter;

    //셀렉트바 선언
    LinearLayout selectbar;
    ImageView iv_selected;
    ImageView ivTop;
    ImageView ivBottom;
    ImageView ivSuit;
    ImageView ivOuter;
    ImageView ivShoes;
    ImageView ivBag;
    ImageView ivAccessory1;

    RelativeLayout layout_preview;
    ImageView ivPreviewImage;
    Bitmap savedCodi;
    Bitmap resizedImage;
    String path;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_codi);

        layout_preview = (RelativeLayout) findViewById(R.id.preview);
        layout_preview.setVisibility(View.GONE);
        ivPreviewImage  = (ImageView) findViewById(R.id.iv_preview_image);

        //헤더 메뉴 아이콘 받아오기
        ImageView ivSearch = (ImageView) findViewById(R.id.iv_search);
        ImageView ivRandom = (ImageView) findViewById(R.id.iv_random);
        TextView tvPreview = (TextView) findViewById(R.id.tv_preview);
        TextView tvDone = (TextView) findViewById(R.id.tv_done);

        //코디 화면 레이아웃과 이미지뷰 받아오기
        RelativeLayout layoutMakeCodi = (RelativeLayout) findViewById(R.id.layout_makecodi);
        ivTop = (ImageView) findViewById(R.id.iv_top);
        ivBottom = (ImageView) findViewById(R.id.iv_bottom);
        ivSuit = (ImageView) findViewById(R.id.iv_suit);
        ivOuter= (ImageView) findViewById(R.id.iv_outer);
        ivShoes= (ImageView) findViewById(R.id.iv_shoes);
        ivBag= (ImageView) findViewById(R.id.iv_bag);
        ivAccessory1= (ImageView) findViewById(R.id.iv_acc1);
        selectbar = (LinearLayout) findViewById(R.id.selectbar);

        //버튼 온클릭리스너 설정
        BtnOnClickListener onClickListener = new BtnOnClickListener();
        ivSearch.setOnClickListener(onClickListener);
        ivRandom.setOnClickListener(onClickListener);
        tvPreview.setOnClickListener(onClickListener);
        tvDone.setOnClickListener(onClickListener);
        ivTop.setOnClickListener(onClickListener);
        ivBottom.setOnClickListener(onClickListener);
        ivSuit.setOnClickListener(onClickListener);
        ivOuter.setOnClickListener(onClickListener);
        ivShoes.setOnClickListener(onClickListener);
        ivBag.setOnClickListener(onClickListener);
        ivAccessory1.setOnClickListener(onClickListener);

        //하단 뷰페이저 어댑터 설정
        viewPager = (ViewPager) findViewById(R.id.viewPager) ;
        viewPager.setOffscreenPageLimit(1); //캐싱을 해놓을 프래그먼트 개수
        viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        pagerAdapter = new PagerAdapter_addCodi(getSupportFragmentManager()); //getSupportFragmentManager로 프래그먼트 참조가능

        //뷰페이저에 프래그먼트 설정
        pagerAdapter.addItem(new Page_allClothes());
        pagerAdapter.addItem(new Page_top());
        pagerAdapter.addItem(new Page_bottom());
        pagerAdapter.addItem(new Page_suit());
        pagerAdapter.addItem(new Page_outer());
        pagerAdapter.addItem(new Page_shoes());
        pagerAdapter.addItem(new Page_bag());
        pagerAdapter.addItem(new Page_accessory());
        viewPager.setAdapter(pagerAdapter);


    }


    //프래그먼트에서 데이터 받아오기
    public void ReceivedData(Object data){
        if(iv_selected!=null)
            iv_selected.setImageBitmap((Bitmap)data);
        else
            Toast.makeText(getApplicationContext(), "추가할 부분을 선택해야 합니다!", Toast.LENGTH_LONG).show();
    }

    class BtnOnClickListener implements Button.OnClickListener {

        RelativeLayout layoutMakeCodi = (RelativeLayout) findViewById(R.id.layout_makecodi);

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.iv_search :
                    break ;
                case R.id.iv_random :
                    getApplication();
                    break ;
                case R.id.tv_preview : //미리보기 버튼 : 현재 코디 비트맵으로 저장한 후 보여주기
                    layout_preview.setVisibility(View.VISIBLE);
                    layoutMakeCodi.setDrawingCacheEnabled(false); //캐시를 지운다
                    layoutMakeCodi.setDrawingCacheEnabled(true); //캐시를 얻는다
                    layoutMakeCodi.buildDrawingCache(); //뷰 이미지 캡쳐 Drawing cache에 저장
                    savedCodi = layoutMakeCodi.getDrawingCache(); //캡쳐를 비트맵으로 저장
                    if (savedCodi != null) {
                        Log.e("confirm","코디 저장됨.");
                        layout_preview.setVisibility(View.VISIBLE);
                        ivPreviewImage.setImageBitmap(savedCodi.copy(savedCodi.getConfig(), false));
                    }
                    break ;
                case R.id.tv_done : //완료 버튼
                    //현재 코디 이미지 캡쳐
                    layoutMakeCodi.setDrawingCacheEnabled(false); //캐시를 지운다
                    layoutMakeCodi.setDrawingCacheEnabled(true); //캐시를 얻는다
                    layoutMakeCodi.buildDrawingCache(); //뷰 이미지를 Drawing cache에 저장
                    savedCodi = layoutMakeCodi.getDrawingCache(); //캡쳐를 비트맵으로 저장
                    resizedImage = Bitmap.createScaledBitmap(savedCodi, 500, 500, true);
                    if (resizedImage != null) {
                        upload();
                        //Intent intent = new Intent(activity_addCodi.this, activity_sendCodi.class);
                        //resizedImage = Bitmap.createScaledBitmap(savedCodi, 500, 500, true);
                        //intent.putExtra("codiImage", resizedImage) ;
                        //startActivity(intent);
                    }
                    break ;
                case R.id.iv_top :
                    iv_selected = ivTop;
                    viewPager.setCurrentItem(Category.TOP);
                    break;
                case R.id.iv_bottom :
                    iv_selected = ivBottom;
                    viewPager.setCurrentItem(Category.BOTTOM);
                    break;
                case R.id.iv_suit :
                    iv_selected = ivSuit;
                    viewPager.setCurrentItem(Category.SUIT);
                    break;
                case R.id.iv_outer :
                    iv_selected = ivOuter;
                    viewPager.setCurrentItem(Category.OUTER);
                    break;
                case R.id.iv_shoes :
                    iv_selected = ivShoes;
                    viewPager.setCurrentItem(Category.SHOES);
                    break;
                case R.id.iv_bag :
                    iv_selected = ivBag;
                    viewPager.setCurrentItem(Category.BAG);
                    break;
                case R.id.iv_acc1:
                    iv_selected = ivAccessory1;
                    viewPager.setCurrentItem(Category.ACCESSORY);
                    break;
            }

            if(iv_selected!=null)
                selectbar.setVisibility(View.VISIBLE);

        }
    }

    String upload(){
        String res="";

        //임시 파일로 저장하기
        final Context context = getApplicationContext();
        String filename = "myTemp";
        File tempFile = null;
        try {
            tempFile = File.createTempFile(filename, null, context.getCacheDir());
            FileOutputStream out = new FileOutputStream(tempFile);
            resizedImage.compress(Bitmap.CompressFormat.JPEG, 70 , out);  // 넘겨 받은 bitmap을 jpeg(손실압축)으로 저장해줌
            out.close();
            path = tempFile.getAbsolutePath(); //임시 파일 경로
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            res = new UploadTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            if (res.contains("ok")) {
                Toast.makeText(activity_addCodi.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), activity_codi_main.class);
                startActivity(intent);
            } else if (res.contains("fail")) {
                Toast.makeText(activity_addCodi.this, "업로드 실패", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(activity_addCodi.this, "업로드 오류", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(getApplicationContext(), activity_home.class);
            //startActivity(intent);
        }
        return res;
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
            List<MultipartBody.Part> arrBody = new ArrayList<>();


            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            mapRequestBody.put("file\"; filename=\"" + file.getName(), requestBody);
            mapRequestBody.put("closetName", RequestBody.create(MediaType.parse("text/plain"), "default"));
            mapRequestBody.put("category", RequestBody.create(MediaType.parse("text/plain"), "outer"));
            body = MultipartBody.Part.createFormData("fileName", file.getName(), requestBody);
            arrBody.add(body);


            Call<String> stringCall = CodiService.getRetrofit(getApplicationContext()).addCodi(mapRequestBody, arrBody);
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

    //뒤로 가기 버튼이 눌렸을 경우 드로워(메뉴)를 닫는다.
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.final_drawer_layout);
        if (layout_preview.getVisibility() == View.VISIBLE) {
            layout_preview.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }


}