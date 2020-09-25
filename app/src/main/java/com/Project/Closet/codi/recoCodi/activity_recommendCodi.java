package com.Project.Closet.codi.recoCodi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.CodiService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.R;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;

public class activity_recommendCodi extends AppCompatActivity implements Page_recommended_codi.FragListener {

    public class Kind {
        //final static int ALL = 0;
        final static int TOP = 0;
        final static int BOTTOM = 1;
        final static int SUIT = 2;
        final static int OUTER = 3;
        final static int SHOES = 4;
        final static int BAG = 5;
        final static int ACCESSORY = 6;
    }

    public class Color {
        final static int BLACK = 1000;
        final static int WHITE = 1001;
        final static int GRAY = 1002;
        final static int IVORY = 1003;
        final static int BEIGE = 1004;
        final static int PINK = 1005;
        final static int RED = 1006;
        final static int WINE = 1007;
        final static int PURPLE = 1008;
        final static int SKY_BLUE = 1009;
        final static int BLUE = 1010;
        final static int NAVY = 1011;
        final static int GREEN = 1012;
        final static int OLIVE_GREEN = 1013;
        final static int YELLOW = 1014;
        final static int ORANGE = 1015;
        final static int BROWN = 1016;
        final static int GOLD = 1017;
        final static int SILVER = 1018;
    }

    HashMap<String,Integer> colorMap;


    ArrayList<ClothesVO> clothesList;

    int main_part;
    int sub_part;
    int other_parts[];


    //뷰페이저 선언
    private TabLayout tabLayout;
    private recommPagerAdapter pagerAdapter;
    private ViewPager viewPager;



    //이미지 저장 관련
    Bitmap savedCodi;
    Bitmap resizedImage;
    String path;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.layout_codi_recommend_all);
        clothesList = getIntent().getExtras().getParcelableArrayList("clothesList");


        colorMap = new HashMap<>();
        colorMap.put("블랙",Color.BLACK);
        colorMap.put("화이트",Color.WHITE);
        colorMap.put("그레이",Color.GRAY);
        colorMap.put("아이보리",Color.IVORY);
        colorMap.put("베이지",Color.BEIGE);
        colorMap.put("핑크",Color.PINK);
        colorMap.put("레드",Color.RED);
        colorMap.put("와인",Color.WINE);
        colorMap.put("퍼플",Color.PURPLE);
        colorMap.put("스카이블루",Color.SKY_BLUE);
        colorMap.put("블루",Color.BLUE);
        colorMap.put("네이비",Color.NAVY);
        colorMap.put("그린",Color.GREEN);
        colorMap.put("올리브그린",Color.OLIVE_GREEN);
        colorMap.put("옐로우",Color.YELLOW);
        colorMap.put("오렌지",Color.ORANGE);
        colorMap.put("브라운",Color.BROWN);
        colorMap.put("골드",Color.GOLD);
        colorMap.put("실버",Color.SILVER);




        //상하의 일때 mode 1 - switch mode
        main_part = Kind.TOP;
        sub_part = Kind.BOTTOM;
        other_parts = new int[]{Kind.SUIT, Kind.OUTER, Kind.SHOES, Kind.BAG, Kind.ACCESSORY};



        //각 파트 별로 나눔
        ArrayList<ClothesVO>[] parts = new ArrayList[7];
        for (int i=0; i<7; i++){
            parts[i] = new ArrayList<>();
        }
        for (ClothesVO clo : clothesList){
            switch(clo.getKind()){
                case "상의" :
                    parts[Kind.TOP].add(clo);
                    break;
                case "하의" :
                    parts[Kind.BOTTOM].add(clo);
                    break;
                case "한벌옷" :
                    parts[Kind.SUIT].add(clo);
                    break;
                case "외투" :
                    parts[Kind.OUTER].add(clo);
                    break;
                case "신발" :
                    parts[Kind.SHOES].add(clo);
                    break;
                case "가방" :
                    parts[Kind.BAG].add(clo);
                    break;
                case "액세서리" :
                    parts[Kind.ACCESSORY].add(clo);
                    break;
            }
        }

        /*각 파트 별로 컬러 목록을 만듬*/
        Set<Integer>[] colorsOfPart = new Set[7];
        for (int i=0; i<7; i++){
            colorsOfPart[i] = new HashSet<>();
        }
        //모든 파트에 대해 (i는 파트 번호)
        for(int i=0; i<7; i++){
            //해당 파트의 옷들 중에서
            for(ClothesVO clo : parts[i]){
                //해당 파트 색 목록에 <- 해당 옷 색깔 저장. set이므로 중복 x
                colorsOfPart[i].add(colorMap.get(clo.getColor()));
                //System.out.println(clo.getKind()+clo.getColor());
            }
        }





        //헤더 메뉴 아이콘 받아오기
        ImageView ivRevert = (ImageView) findViewById(R.id.iv_revert);
        ImageView ivSave = (ImageView) findViewById(R.id.iv_save);
        TextView tvDone = (TextView) findViewById(R.id.tv_done);

        //코디 화면 레이아웃 받아오기
        RelativeLayout layoutMakeCodi = (RelativeLayout) findViewById(R.id.layout_makecodi);

        //버튼 온클릭리스너 설정
        BtnOnClickListener onClickListener = new BtnOnClickListener();
        ivRevert.setOnClickListener(onClickListener);
        ivSave.setOnClickListener(onClickListener);
        tvDone.setOnClickListener(onClickListener);


        //탭 설정
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("1"));
        tabLayout.addTab(tabLayout.newTab().setText("2"));

        //하단 뷰페이저 어댑터 설정
        viewPager = (ViewPager) findViewById(R.id.viewPager) ;
        //viewPager.setOffscreenPageLimit(2); //캐싱을 해놓을 프래그먼트 개수
//        viewPager.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                return true;
//            }
//        });
        pagerAdapter = new recommPagerAdapter(getSupportFragmentManager()); //getSupportFragmentManager로 프래그먼트 참조가능

        //뷰페이저에 프래그먼트 설정
        pagerAdapter.addItem(Page_recommended_codi.newInstance("블랙","화이트", 17, new ArrayList<ClothesVO>())); //0
        pagerAdapter.addItem(Page_recommended_codi.newInstance("블랙","화이트", 17, new ArrayList<ClothesVO>())); //0
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }



    //프래그먼트에서 데이터 받아오기
    public void ReceivedData(Object data){

    }

    class BtnOnClickListener implements Button.OnClickListener {

        RelativeLayout layoutMakeCodi = (RelativeLayout) findViewById(R.id.layout_makecodi);

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.iv_revert :
                    break ;
                case R.id.iv_save :
                    getApplication();
                    break ;
                case R.id.tv_done : //완료 버튼
                    //현재 코디 이미지 캡쳐
                    layoutMakeCodi.setDrawingCacheEnabled(false); //캐시를 지운다
                    layoutMakeCodi.setDrawingCacheEnabled(true); //캐시를 얻는다
                    layoutMakeCodi.buildDrawingCache(); //뷰 이미지를 Drawing cache에 저장
                    savedCodi = layoutMakeCodi.getDrawingCache(); //캡쳐를 비트맵으로 저장

                    //크기 줄여주기 (메모리 부족 오류 방지)
                    double height=savedCodi.getHeight();
                    double width=savedCodi.getWidth();
                    resizedImage = Bitmap.createScaledBitmap(savedCodi, (int)Global.bitmapWidth, (int)(height/(width/Global.bitmapWidth)), true);
                    if (resizedImage != null) {
                        upload();
                        //Intent intent = new Intent(activity_addCodi.this, activity_sendCodi.class);
                        //intent.putExtra("codiImage", resizedImage) ;
                        //startActivity(intent);
                    }
                    break;
            }

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
            resizedImage.compress(Bitmap.CompressFormat.JPEG, 100 , out);  // 넘겨 받은 bitmap을 jpeg(손실압축)으로 저장해줌
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

        Intent intent = new Intent();
        try {
            if (res.contains("ok")) {
                Toast.makeText(activity_recommendCodi.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, intent);
                finish();
            } else if (res.contains("fail")) {
                Toast.makeText(activity_recommendCodi.this, "업로드 실패", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(activity_recommendCodi.this, "업로드 오류", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED, intent);
            finish();
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
            mapRequestBody.put("userID", RequestBody.create(MediaType.parse("text/plain"), MySharedPreferences.getInstanceOf(getApplicationContext()).getUserID()));
            mapRequestBody.put("file\"; filename=\"" + file.getName(), requestBody);
            mapRequestBody.put("closetName", RequestBody.create(MediaType.parse("text/plain"), "default"));
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
        super.onBackPressed();
    }


}