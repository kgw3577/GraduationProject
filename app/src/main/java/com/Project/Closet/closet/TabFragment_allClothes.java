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

import com.Project.Closet.ClothesListAdapter_small;
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

public class TabFragment_allClothes extends Fragment {
    int page=0;
    RecyclerView list_clothes;
    item_Cloth_List ClothList;

    Clothes_List ClothesList;
    ArrayList<String> ImageUrlList = new ArrayList<String>();

    //그리드 사이즈 조절 : 어댑터 변경+그리드 사이즈 변경, 페이지사이즈 변경 (small(4), medium(3), large(2)) 20p, 15p, 10p
    //어댑터 설정
    ClothesListAdapter_small clothesListAdapter = new ClothesListAdapter_small(getActivity(),ImageUrlList, R.layout.fragment_large);
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
        list_clothes.setLayoutManager(new GridLayoutManager(getContext(), 4)); //그리드 사이즈 설정
        list_clothes.setAdapter(clothesListAdapter);
        list_clothes.setNestedScrollingEnabled(true);
        list_clothes.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (!list_clothes.canScrollVertically(-1)) {
                } else if (!list_clothes.canScrollVertically(1)) {
                    new networkTask().execute(Integer.toString(++page));
                    Log.e("test","페이지 수 증가");
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

            //모든 옷 조회
            Call<List<ClothesVO>> cloListCall = ClothesService.getRetrofit(getActivity()).myAllClothes(params[0], "20");
            //페이지 사이즈 설정
            //인자 page, pageSize
            //pageSize는 최소 17?이어야 함. (화면 갱신되려면)

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
