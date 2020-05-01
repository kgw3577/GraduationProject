package com.Project.Closet;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.VO.clothes_List;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.OkHttpClient;
import retrofit2.Call;

public class TabFragment1 extends Fragment {
    int page=0;
    RecyclerView list_clothes;
    item_Cloth_List ClothList;

    clothes_List ClothesList;
    ArrayList<String> ImageUrlList = new ArrayList<String>();
    ClothesListAdapter clothesListAdapter = new ClothesListAdapter(getActivity(),ImageUrlList, R.layout.fragment_tab1);
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        new networkTask().execute(Integer.toString(page));

        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        list_clothes = (RecyclerView) view.findViewById(R.id.tab_clothes_rv);
        list_clothes.setLayoutManager(new GridLayoutManager(getContext(), 2));
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
            String url = "http://52.79.164.93:3600/v1/truck/list";

            Call<List<ClothesVO>> cloListCall = ClothesService.getRetrofit(getActivity()).myAllClothes(params[0], "10");
            //인자 page, pageSize
            //pageSize는 최소 5여야 함.

/*
            RequestBody formBody = new FormBody.Builder()
                    .add("page", params[0])
                    .add("pageSize", "10")
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
                    */

            try {
                //Response response = client.newCall(request).execute();
                //return response.body().string();
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

/*
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null) {
                Gson gson = new Gson();
                //ClothList= gson.fromJson(s, item_Cloth_List.class);
                ClothesList= gson.fromJson(s, clothes_List.class);


                for(ClothesVO e:ClothesList.getList()) {
                    ImageUrlList.add(new String(e.getFilePath()));
                    Log.e("item", e.getFilePath());
                }
                clothesListAdapter.notifyDataSetChanged();


                for(item_Cloth e:ClothList.list) {
                    ImageUrlList.add(new String(ClothList.imageUrl + e.getThumbnail()));
                    Log.e("item", ClothList.imageUrl + e.getThumbnail());

                }
                clothesListAdapter.notifyDataSetChanged();

            }
        }

        */
    }
}
