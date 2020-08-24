package com.Project.Closet.codi.weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.CodiService;
import com.Project.Closet.HTTP.Service.SocialService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.HeartVO;
import com.Project.Closet.HTTP.weather.vo.ApiInterface;
import com.Project.Closet.HTTP.weather.vo.Repo;
import com.Project.Closet.R;
import com.Project.Closet.social.detailFeed.activity_thisFeed;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class activity_weatherCodi extends AppCompatActivity implements Page_category_weather.FragListener {




    class Category {
        //final static int ALL = 0;
        final static int TOP = 0;
        final static int BOTTOM = 1;
        final static int SUIT = 2;
        final static int OUTER = 3;
        final static int SHOES = 4;
        final static int BAG = 5;
        final static int ACCESSORY = 6;
        final static int ACCESSORY2 = 7;
        final static int ACCESSORY3 = 8;
        final static int ACCESSORY4 = 9;
    }

    //뷰페이저 선언
    private ViewPager viewPager;
    private weatherPagerAdapter pagerAdapter;

    //셀렉트바 선언
    LinearLayout selectbar;
    ImageView iv_selected; // 현재 선택된 이미지뷰
    int selectedNum; //선택된 이미지뷰 번호
    List<TextView> tvList; //텍스트뷰 리스트
    boolean[] is_selected_once; //이미지뷰에 이미지가 선택됐는지 체크
    boolean is_topBottom; //상하의/한벌옷 체크
    boolean is_checked_top;
    boolean is_checked_bottom;
    boolean is_checked_suit;

    //파트별 뷰 선언
    ImageView ivTop; //상의 파트
    TextView tvTop;
    ImageView ivBottom; //하의 파트
    TextView tvBottom;
    ImageView ivSuit;   //한벌옷 파트
    TextView tvSuit;
    ImageView ivOuter;  //외투 파트
    TextView tvOuter;
    ImageView ivShoes;  //신발 파트
    TextView tvShoes;
    ImageView ivBag;    //가방 파트
    TextView tvBag;
    ImageView ivAccessory1; //액세서리1 파트
    TextView tvAccessory1;
    ImageView ivAccessory2; //액세서리2 파트
    TextView tvAccessory2;
    ImageView ivAccessory3; //액세서리3 파트
    TextView tvAccessory3;
    ImageView ivAccessory4; //액세서리4 파트
    TextView tvAccessory4;

    //미리보기 관련
    RelativeLayout layout_preview;
    ImageView ivPreviewImage;

    //이미지 저장 관련
    Bitmap savedCodi;
    Bitmap resizedImage;
    String path;

    //날씨 관련
    private LocationManager locationManager;
    private static final int REQUEST_CODE_LOCATION = 2;
    String API_KEY = "f73fa03d36a8a1b6c8acdca0ea6d229a";
    public Address addr;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_codi);

        layout_preview = (RelativeLayout) findViewById(R.id.preview);
        layout_preview.setVisibility(View.GONE);
        ivPreviewImage  = (ImageView) findViewById(R.id.iv_preview_image);

        //헤더 메뉴 아이콘 받아오기
        ImageView ivRevert = (ImageView) findViewById(R.id.iv_revert);
        ImageView ivSearch = (ImageView) findViewById(R.id.iv_search);
        ImageView ivRandom = (ImageView) findViewById(R.id.iv_random);
        TextView tvPreview = (TextView) findViewById(R.id.tv_preview);
        TextView tvDone = (TextView) findViewById(R.id.tv_done);

        //코디 화면 레이아웃 받아오기
        RelativeLayout layoutMakeCodi = (RelativeLayout) findViewById(R.id.layout_makecodi);

        //이미지뷰 불러오기
        ivTop = (ImageView) findViewById(R.id.iv_top);
        ivBottom = (ImageView) findViewById(R.id.iv_bottom);
        ivSuit = (ImageView) findViewById(R.id.iv_suit);
        ivOuter= (ImageView) findViewById(R.id.iv_outer);
        ivShoes= (ImageView) findViewById(R.id.iv_shoes);
        ivBag= (ImageView) findViewById(R.id.iv_bag);
        ivAccessory1= (ImageView) findViewById(R.id.iv_acc1);
        ivAccessory2= (ImageView) findViewById(R.id.iv_acc2);
        ivAccessory3= (ImageView) findViewById(R.id.iv_acc3);
        ivAccessory4= (ImageView) findViewById(R.id.iv_acc4);

        //텍스트뷰 불러오기
        tvTop = (TextView) findViewById(R.id.tv_top);
        tvBottom = (TextView) findViewById(R.id.tv_bottom);
        tvSuit = (TextView) findViewById(R.id.tv_suit);
        tvOuter= (TextView) findViewById(R.id.tv_outer);
        tvShoes= (TextView) findViewById(R.id.tv_shoes);
        tvBag= (TextView) findViewById(R.id.tv_bag);
        tvAccessory1= (TextView) findViewById(R.id.tv_acc1);
        tvAccessory2= (TextView) findViewById(R.id.tv_acc2);
        tvAccessory3= (TextView) findViewById(R.id.tv_acc3);
        tvAccessory4= (TextView) findViewById(R.id.tv_acc4);
        //배열에 넣기
        tvList = new ArrayList<TextView>();
        tvList.add(tvTop);
        tvList.add(tvBottom);
        tvList.add(tvSuit);
        tvList.add(tvOuter);
        tvList.add(tvShoes);
        tvList.add(tvBag);
        tvList.add(tvAccessory1);
        tvList.add(tvAccessory2);
        tvList.add(tvAccessory3);
        tvList.add(tvAccessory4);

        is_selected_once = new boolean[10];
        Arrays.fill(is_selected_once,false);


        selectbar = (LinearLayout) findViewById(R.id.selectbar);

        //버튼 온클릭리스너 설정
        BtnOnClickListener onClickListener = new BtnOnClickListener();
        ivRevert.setOnClickListener(onClickListener);
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
        ivAccessory2.setOnClickListener(onClickListener);
        ivAccessory3.setOnClickListener(onClickListener);
        ivAccessory4.setOnClickListener(onClickListener);

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

        pagerAdapter = new weatherPagerAdapter(getSupportFragmentManager()); //getSupportFragmentManager로 프래그먼트 참조가능

        //뷰페이저에 프래그먼트 설정
        //pagerAdapter.addItem(new Page_allClothes());
        pagerAdapter.addItem(Page_category_weather.newInstance("상의","small")); //0
        pagerAdapter.addItem(Page_category_weather.newInstance("하의","small")); //1
        pagerAdapter.addItem(Page_category_weather.newInstance("한벌옷","small"));
        pagerAdapter.addItem(Page_category_weather.newInstance("외투","small"));
        pagerAdapter.addItem(Page_category_weather.newInstance("신발","small"));
        pagerAdapter.addItem(Page_category_weather.newInstance("가방","small"));
        pagerAdapter.addItem(Page_category_weather.newInstance("액세서리","small"));
        viewPager.setAdapter(pagerAdapter);

        //상하의/한벌옷 모드 초기 설정
        is_topBottom = true; //상하의 모드
        is_checked_top = false;
        is_checked_bottom = false;
        is_checked_suit = false;
        is_selected_once[Category.SUIT] = true; //한벌옷 체크 여부 끔




        //위치
        //사용자의 위치 수신을 위한 세팅
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //사용자의 현재 위치
        Location userLocation = getMyLocation(); //권한을 요청하고 위치 정보를 받아옴
        double latitude = 0;
        double longitude = 0;
        if( userLocation != null ) { //위치 정보를 받아왔을 경우
            latitude = userLocation.getLatitude();
            longitude = userLocation.getLongitude();
        }else{
            //사용자가 위치 정보 권한을 거절했을 경우
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "위치 정보 권한을 허용해야 합니다.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
            //승인했을 경우
            else{ //다시 받아옴
                userLocation = getMyLocation();

                if( userLocation != null ) { //위치 정보를 받아왔을 경우
                    latitude = userLocation.getLatitude();
                    longitude = userLocation.getLongitude();
                }else{
                    Toast.makeText(this, "위치 정보를 받아올 수 없습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
        System.out.println("사용자의 위치 : "+latitude+","+longitude);


        String address = getAddress(getBaseContext(), latitude, longitude);//나라 도 시 구 동 번지
        double temper = 0;

        try {
            temper = new weatherTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, latitude,longitude).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //getWeather(latitude, longitude); //변수 temper에 온도(섭씨) 저장


        int temperInt= (int) Math.round(temper); //소수점 반올림
        String do_ = addr.getAdminArea(); //도
        String si = addr.getLocality(); //시
        String gu = addr.getThoroughfare(); //구


        String weather = si+" "+temperInt+"℃"; //군포시 24℃
        //Toast.makeText(this, si+" "+temperInt+"℃", Toast.LENGTH_SHORT).show();


        String[] recommendedDCate;

        if(temper<17){
            Toast.makeText(this, "해당 날씨에 대한 추천 코디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(temper>=17 && temper<19)
            recommendedDCate = getResources().getStringArray(R.array.fr17to19);
        else if(temper>=19 && temper<22)
            recommendedDCate = getResources().getStringArray(R.array.fr20to22);
        else if(temper>=23 && temper<26)
            recommendedDCate = getResources().getStringArray(R.array.fr23to26);
        else if(temper>=27)
            recommendedDCate = getResources().getStringArray(R.array.fr27);


    }








    //프래그먼트에서 데이터 받아오기
    public void ReceivedData(Object data){
        if(iv_selected !=null){ //선택된 이미지뷰가 있다면
            iv_selected.setImageBitmap((Bitmap)data); //받아온 비트맵으로 이미지뷰를 바꿈

            if(iv_selected.getDrawable() != null){ //이미지뷰의 이미지가 null이 아니고(설정이 됐고)
                if(!is_selected_once[selectedNum]){ // 여태 체크된적 없다면
                    tvList.get(selectedNum).setVisibility(View.GONE); //텍스트뷰를 보이지 않게 하고
                    is_selected_once[selectedNum] = true; //체크함
                }
            }
        }
        else
            Toast.makeText(getApplicationContext(), "추가할 부분을 선택해야 합니다!", Toast.LENGTH_LONG).show();
    }

    class BtnOnClickListener implements Button.OnClickListener {

        RelativeLayout layoutMakeCodi = (RelativeLayout) findViewById(R.id.layout_makecodi);

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.iv_revert :
                    revertMode();
                    break ;
                case R.id.iv_search :
                    break ;
                case R.id.iv_random :
                    getApplication();
                    break ;
                case R.id.tv_preview : //미리보기 버튼 : 현재 코디 비트맵으로 저장한 후 보여주기
                    layout_preview.setVisibility(View.VISIBLE);

                    for(int i=0; i<tvList.size();i++) {
                        tvList.get(i).setVisibility(View.GONE);
                    }

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
                    for(int i=0; i<tvList.size();i++) {
                        tvList.get(i).setVisibility(View.GONE);
                    }

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
                    break ;
                case R.id.iv_top :
                    iv_selected = ivTop;
                    selectedNum = Category.TOP;
                    viewPager.setCurrentItem(Category.TOP);
                    break;
                case R.id.iv_bottom :
                    iv_selected = ivBottom;
                    selectedNum = Category.BOTTOM;
                    viewPager.setCurrentItem(Category.BOTTOM);
                    break;
                case R.id.iv_suit :
                    iv_selected = ivSuit;
                    selectedNum = Category.SUIT;
                    viewPager.setCurrentItem(Category.SUIT);
                    break;
                case R.id.iv_outer :
                    iv_selected = ivOuter;
                    selectedNum = Category.OUTER;
                    viewPager.setCurrentItem(Category.OUTER);
                    break;
                case R.id.iv_shoes :
                    iv_selected = ivShoes;
                    selectedNum = Category.SHOES;
                    viewPager.setCurrentItem(Category.SHOES);
                    break;
                case R.id.iv_bag :
                    iv_selected = ivBag;
                    selectedNum = Category.BAG;
                    viewPager.setCurrentItem(Category.BAG);
                    break;
                case R.id.iv_acc1:
                    iv_selected = ivAccessory1;
                    selectedNum = Category.ACCESSORY;
                    viewPager.setCurrentItem(Category.ACCESSORY);
                    break;
                case R.id.iv_acc2:
                    iv_selected = ivAccessory2;
                    selectedNum = Category.ACCESSORY2;
                    viewPager.setCurrentItem(Category.ACCESSORY);
                    break;
                case R.id.iv_acc3:
                    iv_selected = ivAccessory3;
                    selectedNum = Category.ACCESSORY3;
                    viewPager.setCurrentItem(Category.ACCESSORY);
                    break;
                case R.id.iv_acc4:
                    iv_selected = ivAccessory4;
                    selectedNum = Category.ACCESSORY4;
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
                Toast.makeText(activity_weatherCodi.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, intent);
                finish();
            } else if (res.contains("fail")) {
                Toast.makeText(activity_weatherCodi.this, "업로드 실패", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(activity_weatherCodi.this, "업로드 오류", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED, intent);
            finish();
        }
        return res;
    }

    void revertMode(){

        if(is_topBottom){ //상하의 설정이면
            Toast.makeText(activity_weatherCodi.this, "한벌옷 설정으로 전환합니다.", Toast.LENGTH_SHORT).show();
            is_topBottom = false; //해당 설정을 끄고
            ivTop.setVisibility(View.GONE); //상하의 이미지뷰와 텍스트뷰를 끔
            ivBottom.setVisibility(View.GONE);
            tvTop.setVisibility(View.GONE);
            tvBottom.setVisibility(View.GONE);
            is_checked_top = is_selected_once[Category.TOP]; //상하의 선택여부를 저장하고
            is_checked_bottom = is_selected_once[Category.BOTTOM];
            is_selected_once[Category.TOP] = true; //체크 여부도 끔
            is_selected_once[Category.BOTTOM] = true;

            ivSuit.setVisibility(View.VISIBLE); //한벌옷 이미지뷰를 보이게 함
            is_selected_once[Category.SUIT] = is_checked_suit; //한벌옷 체크여부를 불러옴
            if(!is_selected_once[Category.SUIT]) //false이면
                tvSuit.setVisibility(View.VISIBLE); //텍스트뷰 켬
            }
        else{ //한벌옷 설정이면
            Toast.makeText(activity_weatherCodi.this, "상/하의 설정으로 전환합니다.", Toast.LENGTH_SHORT).show();
            is_topBottom = true;//해당 설정을 끄고
            ivSuit.setVisibility(View.GONE); //한벌옷 이미지뷰와 텍스트뷰를 끔
            tvSuit.setVisibility(View.GONE);
            is_checked_suit = is_selected_once[Category.SUIT]; //한벌옷 선택여부를 저장하고
            is_selected_once[Category.SUIT] = true; //체크 여부도 끔

            ivTop.setVisibility(View.VISIBLE); //상하의 이미지뷰를 보이게 함
            ivBottom.setVisibility(View.VISIBLE);
            is_selected_once[Category.TOP] = is_checked_top; //상하의 선택여부를 불러옴
            is_selected_once[Category.BOTTOM] = is_checked_bottom;
            if(!is_selected_once[Category.TOP]) //false이면
                tvTop.setVisibility(View.VISIBLE); //텍스트뷰 켬
            if(!is_selected_once[Category.BOTTOM])
                tvBottom.setVisibility(View.VISIBLE);
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
        DrawerLayout drawer = findViewById(R.id.final_drawer_layout);
        if (layout_preview.getVisibility() == View.VISIBLE) { //미리보기가 끝나면
            layout_preview.setVisibility(View.GONE);
            for(int i=0; i<tvList.size();i++) {
                if(!is_selected_once[i]){ //선택되지 않았던 파트들에 한해서
                    tvList.get(i).setVisibility(View.VISIBLE); //텍스트가 보이게 한다
                }
            }

        } else {
            super.onBackPressed();
        }
    }


    /**
     * 사용자의 위치를 수신
     */
    private Location getMyLocation() {
        Location currentLocation = null;
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("////////////사용자에게 권한을 요청해야함");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, this.REQUEST_CODE_LOCATION);
        }
        else {
            System.out.println("////////////권한요청 안해도됨");

            // 수동으로 위치 구하기
            String locationProvider = LocationManager.GPS_PROVIDER;
            currentLocation = locationManager.getLastKnownLocation(locationProvider);
            if (currentLocation != null) {
                double lng = currentLocation.getLongitude();
                double lat = currentLocation.getLatitude();
            }
        }
        return currentLocation;
    }


    /**
     * 위도,경도로 주소구하기
     * @param lat
     * @param lng
     * @return 주소
     */
    public String getAddress(Context mContext, double lat, double lng) {
        String nowAddress ="현재 위치를 확인 할 수 없습니다.";
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                address = geocoder.getFromLocation(lat, lng, 1);
                addr = address.get(0);

                if (address != null && address.size() > 0) {
                    // 주소 받아오기
                    String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    nowAddress  = currentLocationAddress;
                }
            }
        } catch (IOException e) {
            nowAddress = "주소를 가져올 수 없습니다.";
            System.out.println("주소를 가져올 수 없습니다.");
            e.printStackTrace();
        }
        return nowAddress;
    }




    public class weatherTask extends AsyncTask<Double, Void, Double> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }


        @Override
        protected Double doInBackground(Double... params)  {

            Retrofit client = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create()).build();
            ApiInterface service = client.create(ApiInterface.class);
            Call<Repo> call = service.repo(API_KEY, params[0], params[1]); //lat,lon
            Repo repo = null;
            try {
                repo = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            double temper = repo.getMain().getTemp()- 273.15;
            return temper;

        }

        @Override
        protected void onPostExecute(Double res) {
            super.onPostExecute(res);
        }
    }



    public void getWeather(double lat, double lot){


        Retrofit client = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface service = client.create(ApiInterface.class);
        Call<Repo> call = service.repo(API_KEY, lat, lot);
        call.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                Repo repo = response.body();
                double temper = repo.getMain().getTemp()- 273.15;
                Toast.makeText(activity_weatherCodi.this, temper+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                Toast.makeText(activity_weatherCodi.this, "날씨 정보 받아오기 실패", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
    }
}