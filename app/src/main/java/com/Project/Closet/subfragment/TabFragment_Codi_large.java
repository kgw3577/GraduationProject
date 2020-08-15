package com.Project.Closet.subfragment;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.CodiService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.CodiVO;
import com.Project.Closet.R;
import com.Project.Closet.util.ClothesListAdapter_large;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/* 그리드 사이즈 조절 방법 :
어댑터 변경, 그리드 사이즈 변경, 페이지사이즈 변경
(small(4), medium(3), large(2)) 20p, 15p, 10p
*/

public class TabFragment_Codi_large extends Fragment {

    String identifier; //프래그먼트의 종류를 알려줌

    int page=0;
    RecyclerView rv_clothes;
    ArrayList<String> ImageUrlList = new ArrayList<String>();
    ArrayList<CodiVO> codiVOList = new ArrayList<CodiVO>();
    //리사이클러뷰 어댑터 초기화
    ClothesListAdapter_large clothesListAdapter = new ClothesListAdapter_large(getActivity(),ImageUrlList, R.layout.fragment_recyclerview);

    Call<List<CodiVO>> codiListCall; //코디 VO 리스트를 응답으로 받는 http 요청

    public static TabFragment_Codi_large newInstance(String identifier) {

        Bundle args = new Bundle();
        args.putString("identifier", identifier);  // 키값, 데이터

        TabFragment_Codi_large fragment = new TabFragment_Codi_large();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle args = getArguments(); // 데이터 받기
        if(args != null)
        {
            identifier = args.getString("identifier");
        }


        //현재 페이지수와 함께 웹서버에 옷 데이터 요청
        new networkTask().execute(Integer.toString(page));

        //리사이클러 뷰 설정하기
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        rv_clothes = (RecyclerView) view.findViewById(R.id.tab_clothes_rv);
        rv_clothes.setLayoutManager(new GridLayoutManager(getContext(), 2)); //그리드 사이즈 설정
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


        return view;
    }

    public class networkTask extends AsyncTask<String, Void, List<CodiVO>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }

        @Override
        protected List<CodiVO> doInBackground(String... params) {


            String userID = MySharedPreferences.getInstanceOf(getContext()).getUserID();
            CodiVO codiFilter = new CodiVO();

            switch (identifier){
                case "share" : //모든 코디 조회
                    codiListCall = CodiService.getRetrofit(getActivity()).myAllCodi(userID,params[0], "7");
                    break;
                case "spring" : //봄 코디 조회
                case "summer" : //여름 코디 조회
                case "fall" : //가을 코디 조회
                case "winter" : //겨울 코디 조회
                    codiFilter.setSeason(identifier);
                    codiListCall = CodiService.getRetrofit(getActivity()).searchCodi(codiFilter,userID,params[0], "7");
                    break;
                case "casual" : //캐주얼 코디 조회
                case "business" : //비지니스 코디 조회
                case "formal" : //포멀 코디 조회
                case "special" : //특수 코디 조회
                    codiFilter.setPlace(identifier);
                    codiListCall = CodiService.getRetrofit(getActivity()).searchCodi(codiFilter,userID,params[0], "7");
                    break;
                case "favorite" : //즐겨찾기한 코디 조회
                    codiFilter.setFavorite(identifier);
                    codiListCall = CodiService.getRetrofit(getActivity()).searchCodi(codiFilter,userID,params[0], "7");
            }




            //페이지 사이즈 설정
            //인자 page, pageSize
            //pageSize는 최소 21?이어야 함. (화면 갱신되려면)
            try {
                return codiListCall.execute().body();

                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<CodiVO> codiList) {
            super.onPostExecute(codiList);
            if(codiList!=null) {
                for(CodiVO e: codiList) {
                    //옷 데이터를 받아온 후 이미지 url 리스트를 갱신
                    ImageUrlList.add(new String(Global.baseURL+e.getFilePath()));
                    codiVOList.add(e);
                    Log.e("item", e.getFilePath());
                }
                clothesListAdapter.notifyDataSetChanged();
            }
        }
    }

    //프래그먼트 갱신
    private void refresh(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }
}
