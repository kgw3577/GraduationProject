package com.Project.Closet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

public class activity_mycloset extends AppCompatActivity {
    int page=1;
    RecyclerView list_clothes;
    item_Cloth_List ClothList;
    ArrayList<String> ImageUrlList = new ArrayList<String>();
    ClothesListAdapter clothesListAdapter = new ClothesListAdapter(getApplication(), ImageUrlList, R.layout.activity_mycloset);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycloset);
        new networkTask().execute(Integer.toString(page));
        list_clothes = (RecyclerView) findViewById(R.id.mycloset_clothes_rv);
        list_clothes.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        list_clothes.setAdapter(clothesListAdapter);
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
