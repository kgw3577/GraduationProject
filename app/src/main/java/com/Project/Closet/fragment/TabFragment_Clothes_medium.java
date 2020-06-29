package com.Project.Closet.fragment;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Project.Closet.util.ClothesListAdapter_medium;
import com.Project.Closet.Global;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.OkHttpClient;
import retrofit2.Call;

public class TabFragment_Clothes_medium extends Fragment {

    String identifier; //프래그먼트의 종류를 알려줌
    int medium_gridsize = 3; //미디엄 그리드 3x4
    String medium_pagesize="15"; //미디엄 페이지 사이즈

    int page=0;
    RecyclerView list_clothes;
    ArrayList<String> ImageUrlList = new ArrayList<String>();
    ClothesListAdapter_medium clothesListAdapter = new ClothesListAdapter_medium(getActivity(),ImageUrlList, R.layout.fragment_recyclerview);
    Call<List<ClothesVO>> cloListCall; // 옷 VO 리스트를 응답으로 받는 http 요청

    public TabFragment_Clothes_medium(String identifier){
        this.identifier = identifier;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        new networkTask().execute(Integer.toString(page));

        //핵심 코드
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        list_clothes = (RecyclerView) view.findViewById(R.id.tab_clothes_rv);
        list_clothes.setLayoutManager(new GridLayoutManager(getContext(), medium_gridsize));
        list_clothes.setAdapter(clothesListAdapter);
        list_clothes.setNestedScrollingEnabled(true);
        list_clothes.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (!list_clothes.canScrollVertically(-1)) {
                } else if (!list_clothes.canScrollVertically(1)) {
                    new networkTask().execute(Integer.toString(++page));
                    Log.e("test","test");
                } else {
                }
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
            OkHttpClient client = new OkHttpClient();
            String baseURL = Global.baseURL;



            switch(identifier){
                case "all" : //모든 옷 조회
                    cloListCall = ClothesService.getRetrofit(getActivity()).myAllClothes(params[0], medium_pagesize);
                    break;
                case "top" : //카테고리 top 조회
                case "bottom" : //카테고리 bottom 조회
                case "suit" : //카테고리 suit 조회
                case "outer" : //카테고리 outer 조회
                case "shoes" : //카테고리 shoes 조회
                case "bag" : //카테고리 bag 조회
                case "accessory" : //카테고리 accessory 조회
                    cloListCall = ClothesService.getRetrofit(getActivity()).chooseCategory(identifier, params[0], medium_pagesize);
                    break;
                case "like" : //즐겨찾기 여부가 "yes"인 옷 가져오기
                    cloListCall = ClothesService.getRetrofit(getActivity()).favoriteClothes("yes", params[0], medium_pagesize);
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
                    ImageUrlList.add(new String(Global.baseURL+e.getFilePath()));
                    Log.e("item", e.getFilePath());
                }
                clothesListAdapter.notifyDataSetChanged();
            }
        }
    }
}
