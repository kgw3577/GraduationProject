package com.Project.Closet.home;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Project.Closet.ClothesListAdapter_large;
import com.Project.Closet.ClothesListAdapter_medium;
import com.Project.Closet.Global;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.VO.Clothes_List;
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

public class TabFragment_likeClo extends Fragment {
    int page=0;
    RecyclerView list_clothes;
    ArrayList<String> ImageUrlList = new ArrayList<String>();
    ClothesListAdapter_medium clothesListAdapter = new ClothesListAdapter_medium(getActivity(),ImageUrlList, R.layout.fragment_recyclerview);
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
        list_clothes.setLayoutManager(new GridLayoutManager(getContext(), 3));
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

            //즐겨찾기 여부가 "yes"인 옷 가져오기
            Call<List<ClothesVO>> cloListCall = ClothesService.getRetrofit(getActivity()).favoriteClothes("yes", params[0], "15");
            //인자 page, pageSize
            //pageSize는 최소 10이어야 함.

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
