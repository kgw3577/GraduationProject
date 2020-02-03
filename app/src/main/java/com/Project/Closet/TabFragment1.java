package com.Project.Closet;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TabFragment1 extends Fragment {
    int page=1;
    RecyclerView list_clothes;
    item_Cloth_List ClothList;
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

    public class networkTask extends AsyncTask<String,Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            String url = "http://52.79.164.93:3600/v1/truck/list";

            RequestBody formBody = new FormBody.Builder()
                    .add("page", params[0])
                    .add("pageSize", "10")
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();

                return response.body().string();
                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null) {
                Gson gson = new Gson();
                ClothList= gson.fromJson(s, item_Cloth_List.class);

                for(item_Cloth e:ClothList.list) {
                    ImageUrlList.add(new String(ClothList.imageUrl + e.getThumbnail()));
                    Log.e("item", ClothList.imageUrl + e.getThumbnail());

                }
                clothesListAdapter.notifyDataSetChanged();
            }
        }
    }
}
