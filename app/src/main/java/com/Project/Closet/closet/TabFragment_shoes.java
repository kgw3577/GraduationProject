package com.Project.Closet.closet;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.ClothesListAdapter;
import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.HTTP.VO.Clothes_List;
import com.Project.Closet.R;
import com.Project.Closet.item_Cloth_List;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;

public class TabFragment_shoes extends Fragment {
    int page=0;
    RecyclerView list_clothes;
    item_Cloth_List ClothList;

    Clothes_List ClothesList;
    ArrayList<String> ImageUrlList = new ArrayList<String>();
    ClothesListAdapter clothesListAdapter = new ClothesListAdapter(getActivity(),ImageUrlList, R.layout.fragment_large);
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        new networkTask().execute(Integer.toString(page));

        View view = inflater.inflate(R.layout.fragment_large, container, false);
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

            Call<List<ClothesVO>> cloListCall = ClothesService.getRetrofit(getActivity()).chooseCategory("shoes", params[0], "10");
            //인자 page, pageSize
            //pageSize는 최소 5여야 함.

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
