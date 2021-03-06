package com.Project.Closet.codi.weather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.R;
import com.Project.Closet.closet.fragment_closet;
import com.Project.Closet.util.ClothesListAdapter;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;

/* 그리드 사이즈 조절 방법 :
어댑터 변경, 그리드 사이즈 변경, 페이지사이즈 변경
(small(4), medium(3), large(2)) 20p, 15p, 10p
*/

public class Page_category_weather extends Fragment {

    fragment_closet parentFragment;
    boolean is_first=true;

    String identifier; //프래그먼트의 종류를 알려줌
    String size;
    String[] recommendedDCate;

    int gridsize;
    String pagesize;

    int page=0;
    RecyclerView rv_clothes;
    ArrayList<ClothesVO> clothesList = new ArrayList<ClothesVO>();

    ClothesListAdapter clothesListAdapter;
    Call<List<ClothesVO>> cloListCall; // 옷 VO 리스트를 응답으로 받는 http 요청


    public static Page_category_weather newInstance(String identifier, String size, String[] recommendedDCate) {

        Bundle args = new Bundle();
        args.putString("identifier", identifier);  // 키값, 데이터
        args.putString("size", size);
        args.putStringArray("recommendedDCate", recommendedDCate);

        Page_category_weather fragment = new Page_category_weather();
        fragment.setArguments(args);
        return fragment;
    }


    public interface FragListener{
        void ReceivedData(Object data);
    }
    private FragListener mFragListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof FragListener){
            mFragListener = (FragListener) getActivity();
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments(); // 데이터 받기
        if(args != null)
        {
            identifier = args.getString("identifier");
            size = args.getString("size");
            recommendedDCate = args.getStringArray("recommendedDCate");
        }

        switch (size){
            case "small":
                gridsize = 4; //스몰 그리드 4x5
                pagesize="25"; //스몰 페이지 사이즈 25
                break;
            case "medium":
                gridsize = 3; //미디엄 그리드 3x4
                pagesize="17"; //미디엄 페이지 사이즈 17
                break;
            case "large":
                gridsize = 2; //라지 그리드 2x3
                pagesize="7"; //라지 페이지 사이즈 7
                break;
        }

        pagesize="12"; //임의 설정..

        clothesListAdapter = new ClothesListAdapter(getActivity(),clothesList, R.layout.fragment_recyclerview, "small");

        clothesListAdapter.setOnItemClickListener(new ClothesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ImageView iv_Clothes, ClothesVO cloInfo) {
                Bitmap selectedImage = ((BitmapDrawable)iv_Clothes.getDrawable()).getBitmap();
                if(mFragListener != null){
                    mFragListener.ReceivedData(selectedImage);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //현재 페이지수와 함께 웹서버에 옷 데이터 요청
        new networkTask().execute(Integer.toString(page));

        //리사이클러 뷰 설정하기
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        rv_clothes = (RecyclerView) view.findViewById(R.id.tab_clothes_rv);
        rv_clothes.setLayoutManager(new GridLayoutManager(getContext(), gridsize)); //그리드 사이즈 설정
        rv_clothes.setAdapter(clothesListAdapter);
        rv_clothes.setNestedScrollingEnabled(true);
        rv_clothes.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (!rv_clothes.canScrollVertically(-1)) {
                    //스크롤이 최상단이면 데이터를 갱신한다
                    //page = 0;
                    //new networkTask().execute(Integer.toString(page));
                    //Log.e("test","데이터 갱신");
                }
                else if (!rv_clothes.canScrollVertically(1)) {
                    //스크롤이 최하단이면 웹서버에 다음 페이지 옷 데이터 요청
                    new networkTask().execute(Integer.toString(++page));
                    Log.e("test","페이지 수 증가");
                }
                else {
                }
            }
        });

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //스크롤이 최상단이면 데이터를 갱신한다
                clothesList.clear();
                page=0;
                new networkTask().execute(Integer.toString(page));
                clothesListAdapter.notifyDataSetChanged();
                Log.e("test","데이터 갱신");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;
    }

    public class networkTask extends AsyncTask<String, Void, List<ClothesVO>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }

        @Override
        protected List<ClothesVO> doInBackground(String... params) {
            String userID = MySharedPreferences.getInstanceOf(getContext()).getUserID();
            HashMap map = new HashMap();
            Log.e("message",recommendedDCate.toString());
            for(String str : recommendedDCate){
                Log.e("message",str);
            }

//            String encodeResult=null;
//            try {
//                encodeResult = URLEncoder.encode(dCateString, "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }

            //Log.e("message",encodeResult);

            switch(identifier){
                case "share" : //모든 옷 조회
                    //cloListCall = ClothesService.getRetrofit(getActivity()).searchClothesByList(
                    //        clothesFilter, userID, params[0], pagesize,"detailCategory",encodeResult);
                    break;
                case "상의" : //카테고리 top 조회
                case "하의" : //카테고리 bottom 조회
                case "한벌옷" : //카테고리 suit 조회
                case "외투" : //카테고리 outer 조회
                case "신발" : //카테고리 shoes 조회
                case "가방" : //카테고리 bag 조회
                case "액세서리" : //카테고리 accessory 조회
                    map.put("kind",identifier);
                    map.put("location","private");
                    map.put("list",recommendedDCate);
                    cloListCall = ClothesService.getRetrofit(getActivity()).searchClothesByList(
                            map, userID, params[0], pagesize,"detailCategory");
                    break;
                case "favorite" : //즐겨찾기 여부가 "yes"인 옷 가져오기
                    //clothesFilter.setFavorite("yes");
                    cloListCall = ClothesService.getRetrofit(getActivity()).searchClothesByList(
                            map, userID, params[0], pagesize,"detailCategory");
                    break;
            }
            //인자 param[0]은 page.

            try {

                return cloListCall.execute().body();

                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ClothesVO> clolist) {
            super.onPostExecute(clolist);
            if(clolist!=null) {
                for(ClothesVO e:clolist) {
                    //옷 데이터를 받아온 후 이미지 url 리스트를 갱신
                    clothesList.add(e);
                    Log.e("item", e.getFilePath());
                    clothesListAdapter.notifyDataSetChanged();
                }
                if(is_first && clothesList.size()!=0){
                    String randomPath = clothesList.get(new Random().nextInt(clothesList.size())).getFilePath();
                    activity_weatherCodi activity = (activity_weatherCodi)getActivity();
                    switch(identifier){
                        case "상의" : //카테고리 top 조회
                            Glide.with(getContext()).load(Global.baseURL+randomPath).into(activity.ivTop);
                            activity.tvTop.setVisibility(View.GONE);
                            activity.is_selected_once[activity_weatherCodi.Category.TOP]=true;
                            break;
                        case "하의" : //카테고리 bottom 조회
                            Glide.with(getContext()).load(Global.baseURL+randomPath).into(activity.ivBottom);
                            activity.tvBottom.setVisibility(View.GONE);
                            activity.is_selected_once[activity_weatherCodi.Category.BOTTOM]=true;
                            break;
                        case "한벌옷" : //카테고리 suit 조회
                            Glide.with(getContext()).load(Global.baseURL+randomPath).into(activity.ivSuit);
                            activity.tvSuit.setVisibility(View.GONE);
                            activity.is_selected_once[activity_weatherCodi.Category.SUIT]=true;
                            activity.is_checked_suit=true;
                            break;
                        case "외투" : //카테고리 outer 조회
                            Glide.with(getContext()).load(Global.baseURL+randomPath).into(activity.ivOuter);
                            activity.tvOuter.setVisibility(View.GONE);
                            activity.is_selected_once[activity_weatherCodi.Category.OUTER]=true;
                            break;
                        case "신발" : //카테고리 shoes 조회
                            Glide.with(getContext()).load(Global.baseURL+randomPath).into(activity.ivShoes);
                            activity.tvShoes.setVisibility(View.GONE);
                            activity.is_selected_once[activity_weatherCodi.Category.SHOES]=true;
                            break;
                        case "가방" : //카테고리 bag 조회
                            Glide.with(getContext()).load(Global.baseURL+randomPath).into(activity.ivBag);
                            activity.tvBag.setVisibility(View.GONE);
                            activity.is_selected_once[activity_weatherCodi.Category.BAG]=true;
                            break;
                        case "액세서리" : //카테고리 accessory 조회
                            Glide.with(getContext()).load(Global.baseURL+randomPath).into(activity.ivAccessory4);
                            activity.tvAccessory4.setVisibility(View.GONE);
                            activity.is_selected_once[activity_weatherCodi.Category.ACCESSORY4]=true;
                            break;

                }
                is_first=false;
            }


        }
    }
    }

    //프래그먼트 갱신
    private void refresh(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }
}
