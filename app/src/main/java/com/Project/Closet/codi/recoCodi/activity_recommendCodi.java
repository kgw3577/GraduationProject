package com.Project.Closet.codi.recoCodi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.Project.Closet.util.Codi;
import com.Project.Closet.util.ColorArrange;
import com.Project.Closet.util.Utils;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;

public class activity_recommendCodi extends AppCompatActivity implements Page_recommended_codi.FragListener {

    private static final String TAG = "recommend";


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







        //상하의 일때 mode 1 - switch mode
        main_part = Utils.Kind.TOP;
        sub_part = Utils.Kind.BOTTOM;
        other_parts = new int[]{Utils.Kind.SUIT, Utils.Kind.OUTER, Utils.Kind.SHOES, Utils.Kind.BAG, Utils.Kind.ACCESSORY};



        //각 파트 별로 나눔
        ArrayList<ClothesVO>[] parts = new ArrayList[7];
        for (int i=0; i<7; i++){
            parts[i] = new ArrayList<>();
        }
        for (ClothesVO clo : clothesList){
            switch(clo.getKind()){
                case "상의" :
                    parts[Utils.Kind.TOP].add(clo);
                    break;
                case "하의" :
                    parts[Utils.Kind.BOTTOM].add(clo);
                    break;
                case "한벌옷" :
                    parts[Utils.Kind.SUIT].add(clo);
                    break;
                case "외투" :
                    parts[Utils.Kind.OUTER].add(clo);
                    break;
                case "신발" :
                    parts[Utils.Kind.SHOES].add(clo);
                    break;
                case "가방" :
                    parts[Utils.Kind.BAG].add(clo);
                    break;
                case "액세서리" :
                    parts[Utils.Kind.ACCESSORY].add(clo);
                    break;
            }
        }

        if(parts[main_part].size()==0 || parts[sub_part].size()==0){
            //모드 바꾸기..
            Toast.makeText(this, "만들 수 있는 코디가 없습니다. 더 많은 옷을 추가해보세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }
        Log.d(TAG, parts[main_part].size()+" "+parts[sub_part].size());

        /*각 파트 별로 컬러 목록을 만듬*/
        Set<Integer>[] colorsOfPart = new Set[7]; //set : 중복x
        for (int i=0; i<7; i++){
            colorsOfPart[i] = new HashSet<>();
        }
        //모든 파트에 대해 (i는 파트 번호)
        for(int i=0; i<7; i++){
            //해당 파트의 옷들 중에서
            for(ClothesVO clo : parts[i]){
                //해당 파트 색 목록에 <- 해당 옷 색깔 저장. set이므로 중복 x
                colorsOfPart[i].add(Utils.colorMap.get(clo.getColor()));
            }
        }


        /*main 파트와 sub 파트의 배색 점수 계산*/
        List<ColorArrange> colorArrangeList = new ArrayList<>();
        //main 파트와 sub 파트 내의 모든 색 조합에 대하여 다음을 수행
        for(int main_color : colorsOfPart[main_part]){
            for(int sub_color : colorsOfPart[sub_part]){
                //해당 컬러들에 대한 배색 클래스를 만듬
                ColorArrange colorArrange = new ColorArrange(main_color,sub_color);
                Log.d(TAG, Utils.getKey(Utils.colorMap, main_color) + " " + Utils.getKey(Utils.colorMap, sub_color)+ " 배색 생성");
                //기타 파트 중 메인-서브 컬러와 같은 계열의 색이 존재하는지 체크하고 없으면 balance 점수를 -20 함
                for(int part : other_parts){
                    if(colorsOfPart[part].size()==0) { //해당 파트의 옷이 아예 존재하지 않으면 balance 점수를 깎지 않음.
                        Log.d(TAG, Utils.getKey(Utils.colorMap, main_color) + " " + Utils.getKey(Utils.colorMap, sub_color)
                                + " 파트" + part + " 존재하지 않음.");
                        continue;
                    }
                    int flag=0;
                    for(int color : colorArrange.getOther_colors()) {
                        if(colorsOfPart[part].contains(color)){
                            flag=1;
                            Log.d(TAG, Utils.getKey(Utils.colorMap,main_color)+" "+Utils.getKey(Utils.colorMap,sub_color)
                                    +" "+part+" "+Utils.getKey(Utils.colorMap,color));
                            break;
                        }
                    }
                    if(flag==0){
                        colorArrange.balance_score-=20;
                        Log.d(TAG, Utils.getKey(Utils.colorMap,main_color)+" "+Utils.getKey(Utils.colorMap,sub_color)
                                +" 파트"+part+" 해당 없음.");
                    }

                }
                colorArrange.total_score = colorArrange.arrange_score + colorArrange.balance_score; //총점 계산
                Log.d(TAG, Utils.getKey(Utils.colorMap,main_color)+" "+Utils.getKey(Utils.colorMap,sub_color)
                        +" 배색 점수 "+colorArrange.arrange_score+" 조화 점수 "+colorArrange.balance_score);

                colorArrangeList.add(colorArrange);
            }
        }

        //출력 확인용.
        Collections.sort(colorArrangeList);
        for(ColorArrange cA : colorArrangeList){
            cA.describe();
        }

        List<Codi> codiList = new ArrayList<>();
        int numColorArrange = colorArrangeList.size();

        int numAllRepeat = 1;
        int numRepeat = 1;
        if(numColorArrange<10&&numColorArrange!=0){
            numAllRepeat =3;
            numRepeat = 10/numColorArrange;
        }

        for(int i=0; i<numAllRepeat; i++){ //중복값을 대비해 전체 과정을 최대 2번 되풀이

            for(ColorArrange colorArrange : colorArrangeList){ //모든 배색조합에 대해
                for(int j=0; j<numRepeat; j++){ //해당 색조합을 반복횟수만큼 코디를 만듬

                    Codi codi = new Codi(); //코디 만들기
                    int main_color = colorArrange.getMain_color();
                    int sub_color = colorArrange.getSub_color();
                    Set<Integer> other_colors = colorArrange.getOther_colors();
                    List<ClothesVO> tempList = new ArrayList<>();
                    int cloIndex;

                    for(ClothesVO clothes: parts[main_part]){ //코디의 main 파트 결정
                        if(Utils.colorMap.get(clothes.getColor())==main_color) //해당 컬러를 가진 해당 파트 찾기
                            tempList.add(clothes);
                    }
                    cloIndex = new Random().nextInt(tempList.size()); //랜덤으로 옷 선택
                    codi.setPart(main_part,tempList.get(cloIndex)); //코디의 해당 파트에 선택된 옷 set
                    Log.d(TAG, "메인 파트 선택 : "+Utils.getKey(Utils.colorMap,main_color)+" "+tempList.get(cloIndex).getDetailCategory());
                    tempList.clear();


                    for(ClothesVO clothes: parts[sub_part]){ //코디의 sub 파트 결정
                        if(Utils.colorMap.get(clothes.getColor())==sub_color) //해당 컬러를 가진 해당 파트 찾기
                            tempList.add(clothes);
                    }
                    cloIndex = new Random().nextInt(tempList.size());
                    codi.setPart(sub_part,tempList.get(cloIndex));
                    Log.d(TAG, "서브 파트 선택 : "+Utils.getKey(Utils.colorMap,sub_color)+" "+tempList.get(cloIndex).getDetailCategory());
                    tempList.clear();

                    for(int part : other_parts){ //코디의 나머지 파트 결정
                        for(ClothesVO clothes: parts[part]){ //해당 파트의 옷에서
                            for(int color : other_colors){ //모든 기타 컬러에 대해
                                if(Utils.colorMap.get(clothes.getColor())==color) //해당 컬러를 가진 해당 파트 찾기
                                    tempList.add(clothes);
                            }
                        }
                        if(tempList.size()!=0){
                            //해당 컬러의 해당 파트가 있을 경우 그 중에서 랜덤 선택
                            cloIndex = new Random().nextInt(tempList.size());
                            codi.setPart(part,tempList.get(cloIndex));
                            Log.d(TAG, part+"기타 파트 선택 : "+tempList.get(cloIndex).getColor()+" "+tempList.get(cloIndex).getDetailCategory());
                            tempList.clear();
                        }else if(parts[part].size()!=0){
                            //없을 경우 해당 파트 중에서 랜덤 선택
                            cloIndex = new Random().nextInt(parts[part].size());
                            codi.setPart(part,parts[part].get(cloIndex));
                            Log.d(TAG, part+"(안 어울림)기타 파트 선택 : "+parts[part].get(cloIndex).getColor()+" "+parts[part].get(cloIndex).getDetailCategory());
                        }
                    }
                    if(!codiList.contains(codi)){
                        codiList.add(codi); //코디 생성 과정이 모두 끝난 후 리스트에 추가

                    }else
                        Log.d(TAG,"중복 제거함");
                    Log.d(TAG,i+" "+j+"번 코디 생성 완료");
                }
            }
        }
        Log.d(TAG, "코디 리스트 개수 :"+codiList.size());


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
        for(int i=0; i<codiList.size();i++){ //생성된 코디 개수만큼 탭 생성
            tabLayout.addTab(tabLayout.newTab().setText(i+1+""));
        }

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
        for(Codi codi : codiList){ //생성된 코디 개수만큼 페이지 생성
            pagerAdapter.addItem(Page_recommended_codi.newInstance("블랙","화이트", 17, codi));
        }

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