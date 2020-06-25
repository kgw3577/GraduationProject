package com.Project.Closet.closet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import retrofit2.Call;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.Project.Closet.ClothesListAdapter;
import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.R;
import com.Project.Closet.item_Cloth_List;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class activity_mycloset extends AppCompatActivity {
    int page=0;
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

    public class networkTask extends AsyncTask<String,Void, List<ClothesVO>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }

        @Override
        protected List<ClothesVO> doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            Call<List<ClothesVO>> cloListCall = ClothesService.getRetrofit(getApplicationContext()).myAllClothes(params[0], "10");
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
