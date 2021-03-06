package com.Project.Closet.closet;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.home.activity_home;
import com.Project.Closet.util.ClothesListAdapter;
import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

/* 그리드 사이즈 조절 방법 :
어댑터 변경, 그리드 사이즈 변경, 페이지사이즈 변경
(small(4), medium(3), large(2)) 20p, 15p, 10p
*/

public class   TabFragment_Clothes_inCloset extends Fragment {

    fragment_closet parentFragment;
    int current_pos = 0;

    String location;
    String identifier; //프래그먼트의 종류를 알려줌
    String size;

    int gridsize;
    String pagesize;

    public RelativeLayout Cloth_Info;

    int page=0;
    RecyclerView rv_clothes;
    ArrayList<String> ImageUrlList = new ArrayList<String>();
    ArrayList<ClothesVO> clothesList = new ArrayList<ClothesVO>();
    //리사이클러뷰 어댑터
    ClothesListAdapter clothesListAdapter;
    Call<List<ClothesVO>> cloListCall; // 옷 VO 리스트를 응답으로 받는 http 요청


    public static TabFragment_Clothes_inCloset newInstance(String location, String identifier, String size) {

        Bundle args = new Bundle();
        args.putString("location", location);
        args.putString("identifier", identifier);  // 키값, 데이터
        args.putString("size", size);

        TabFragment_Clothes_inCloset fragment = new TabFragment_Clothes_inCloset();
        fragment.setArguments(args);
        return fragment;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentFragment = ((fragment_closet)getParentFragment());


        Bundle args = getArguments(); // 데이터 받기
        if(args != null)
        {
            location = args.getString("location");
            identifier = args.getString("identifier");
            size = args.getString("size");
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

        //리사이클러뷰 어댑터 초기화
        clothesListAdapter = new ClothesListAdapter(getActivity(), clothesList, R.layout.fragment_recyclerview, size);


        clothesListAdapter.setOnItemClickListener(new ClothesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, final int position, ImageView iv_Clothes, ClothesVO cloInfo) {

                parentFragment.setInfo(cloInfo);

                final ImageView iv_heart = parentFragment.iv_heart;
                iv_heart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String res = null;
                        //필터가 될 vo 설정
                        ClothesVO clothesFilter = new ClothesVO();
                        clothesFilter.setCloNo(Integer.parseInt(parentFragment.tv_cloNo.getText().toString()));
                        boolean reverted_favorite;
                        //즐겨찾기 여부 불러와서 반대값으로 설정
                        if("yes".equals(parentFragment.tv_cloFavorite.getText().toString())){
                            clothesFilter.setFavorite("no");
                            reverted_favorite = false;
                        }
                        else{
                            clothesFilter.setFavorite("yes");
                            reverted_favorite = true;
                        }

                        try {
                            res = new FavoriteTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, clothesFilter).get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e("tag",res);

                        ClothesVO currentVO;
                        if("ok".equals(res)){
                            if(reverted_favorite){
                                Toast.makeText(getContext(), "즐겨찾기를 등록했습니다.", Toast.LENGTH_SHORT).show();
                                iv_heart.setImageResource(R.drawable.star_color);
                                parentFragment.tv_cloFavorite.setText("yes");
                                currentVO = clothesList.get(position);
                                currentVO.setFavorite("yes");
                                clothesList.set(position,currentVO);
                            }else{
                                Toast.makeText(getContext(), "즐겨찾기를 해제했습니다.", Toast.LENGTH_SHORT).show();
                                iv_heart.setImageResource(R.drawable.star_empty);
                                parentFragment.tv_cloFavorite.setText("no");
                                currentVO = clothesList.get(position);
                                currentVO.setFavorite("no");
                                clothesList.set(position,currentVO);
                            }
                            ((activity_home)getActivity()).notify_home_changed();
                        }
                        else
                            Toast.makeText(getContext(), "즐겨찾기 실패", Toast.LENGTH_SHORT).show();
                    }
                });
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
                    //clothesList.clear();
                    //page=0;
                    //new networkTask().execute(Integer.toString(page));
                    //clothesListAdapter.notifyDataSetChanged();
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
            ClothesVO clothesFilter = new ClothesVO();
            clothesFilter.setLocation(location);
            switch(identifier){
                case "share" : //모든 옷 조회
                    cloListCall = ClothesService.getRetrofit(getActivity()).searchClothes(clothesFilter, userID,params[0], pagesize);
                    break;
                case "상의" : //카테고리 top 조회
                case "하의" : //카테고리 bottom 조회
                case "한벌옷" : //카테고리 suit 조회
                case "외투" : //카테고리 outer 조회
                case "신발" : //카테고리 shoes 조회
                case "가방" : //카테고리 bag 조회
                case "액세서리" : //카테고리 accessory 조회
                    clothesFilter.setKind(identifier);
                    cloListCall = ClothesService.getRetrofit(getActivity()).searchClothes(clothesFilter,userID, params[0], pagesize);
                    break;
                case "favorite" : //즐겨찾기 여부가 "yes"인 옷 가져오기
                    clothesFilter.setFavorite("yes");
                    cloListCall = ClothesService.getRetrofit(getActivity()).searchClothes(clothesFilter,userID, params[0], pagesize);
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
                    ImageUrlList.add(new String(Global.baseURL+e.getFilePath()));
                    clothesList.add(e);
                    Log.e("item", e.getFilePath());
                }
                clothesListAdapter.notifyDataSetChanged();
            }
        }
    }


    public class FavoriteTask extends AsyncTask<ClothesVO, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(ClothesVO... ClothesFilter) {

            Call<String> stringCall = ClothesService.getRetrofit(getContext()).modifyClothes(ClothesFilter[0]);
            try {
                return stringCall.execute().body();
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


    @Override
    public void onResume() {
        super.onResume();
    }

    //프래그먼트 갱신
    private void refresh(){
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }


}
