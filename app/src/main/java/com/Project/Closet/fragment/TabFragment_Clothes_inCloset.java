package com.Project.Closet.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.closet.activity_closet;
import com.Project.Closet.closet.fragment_closet;
import com.Project.Closet.util.ClothesListAdapter;
import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.R;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

/* 그리드 사이즈 조절 방법 :
어댑터 변경, 그리드 사이즈 변경, 페이지사이즈 변경
(small(4), medium(3), large(2)) 20p, 15p, 10p
*/

public class   TabFragment_Clothes_inCloset extends Fragment {

    String identifier; //프래그먼트의 종류를 알려줌
    String size;

    int gridsize;
    String pagesize;

    ImageView iv_heart;
    ImageView iv_modify;
    ImageView iv_delete;
    TextView tv_cloNo;
    TextView tv_cloFavorite;
    boolean is_favorite;


    int page=0;
    RecyclerView rv_clothes;
    ArrayList<String> ImageUrlList = new ArrayList<String>();
    ArrayList<ClothesVO> clothesList = new ArrayList<ClothesVO>();
    //리사이클러뷰 어댑터
    ClothesListAdapter clothesListAdapter;
    Call<List<ClothesVO>> cloListCall; // 옷 VO 리스트를 응답으로 받는 http 요청


    public static TabFragment_Clothes_inCloset newInstance(String identifier, String size) {

        Bundle args = new Bundle();
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


        Bundle args = getArguments(); // 데이터 받기
        if(args != null)
        {
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
        clothesListAdapter = new ClothesListAdapter(getActivity(),ImageUrlList, R.layout.fragment_recyclerview, size);

        clothesListAdapter.setOnItemClickListener(new ClothesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ImageView iv_Clothes) {
                ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).Cloth_Info.setVisibility(View.VISIBLE);
                Glide.with((((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).iv_image).getContext()).load(ImageUrlList.get(position)).into(((activity_closet) getActivity()).iv_image);
                Glide.with((((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).iv_edit_image).getContext()).load(ImageUrlList.get(position)).into(((activity_closet) getActivity()).iv_edit_image);
                ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).tv_name.setText(clothesList.get(position).getName());
                ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).tv_category.setText(clothesList.get(position).getClosetName());
                ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).tv_detailcategory.setText(clothesList.get(position).getCategory());
                ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).tv_season.setText(clothesList.get(position).getSeason());
                ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).tv_brand.setText(clothesList.get(position).getBrand());
                ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).tv_size.setText(clothesList.get(position).getCloSize());
                ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).tv_date.setText(clothesList.get(position).getDate());

                ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).tv_cloNo.setText(Integer.toString(clothesList.get(position).getNo()));
                if("yes".equals(clothesList.get(position).getLike())){
                    iv_heart.setImageResource(R.drawable.favorite_color);
                    tv_cloFavorite.setText("yes");
                }
                else{
                    iv_heart.setImageResource(R.drawable.favorite_empty);
                    tv_cloFavorite.setText("no");
                }

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

        //iv_heart = ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).iv_heart;
        //iv_modify = ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).iv_modify;
        //iv_delete = ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).iv_delete;
        //tv_cloNo = ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).tv_cloNo;
        //tv_cloFavorite = ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).tv_cloFavorite;
        
        BtnOnClickListener onClickListener = new BtnOnClickListener();
        //iv_heart.setOnClickListener(onClickListener);
        //iv_modify.setOnClickListener(onClickListener);
        //iv_delete.setOnClickListener(onClickListener);

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

            switch(identifier){
                case "all" : //모든 옷 조회
                    cloListCall = ClothesService.getRetrofit(getActivity()).myAllClothes(params[0], pagesize);
                    break;
                case "top" : //카테고리 top 조회
                case "bottom" : //카테고리 bottom 조회
                case "suit" : //카테고리 suit 조회
                case "outer" : //카테고리 outer 조회
                case "shoes" : //카테고리 shoes 조회
                case "bag" : //카테고리 bag 조회
                case "accessory" : //카테고리 accessory 조회
                    cloListCall = ClothesService.getRetrofit(getActivity()).chooseCategory(identifier, params[0], pagesize);
                    break;
                case "favorite" : //즐겨찾기 여부가 "yes"인 옷 가져오기
                    cloListCall = ClothesService.getRetrofit(getActivity()).favoriteClothes("yes", params[0], pagesize);
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

            Call<String> stringCall = ClothesService.getRetrofit(((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).getContext()).modifyClothes(ClothesFilter[0]);
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

    public class DeleteTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... cloNo) {

            Call<String> stringCall = ClothesService.getRetrofit(((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).getContext()).deleteClothes(cloNo[0]);
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






    class BtnOnClickListener implements Button.OnClickListener {
        String res="";

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.iv_heart :
                    //필터가 될 vo 설정
                    ClothesVO clothesFilter = new ClothesVO();
                    clothesFilter.setNo(Integer.parseInt(tv_cloNo.getText().toString()));
                    boolean reverted_favorite;
                    //즐겨찾기 여부 불러와서 반대값으로 설정
                    if("yes".equals(tv_cloFavorite.getText().toString())){
                        clothesFilter.setLike("no");
                        reverted_favorite = false;
                    }
                    else{
                        clothesFilter.setLike("yes");
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
                    if("ok".equals(res)){
                        if(reverted_favorite){
                            Toast.makeText(((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).getContext(), "즐겨찾기에 추가했습니다.", Toast.LENGTH_SHORT).show();
                            tv_cloFavorite.setText("yes");
                            iv_heart.setImageResource(R.drawable.favorite_color);
                        }else{
                            Toast.makeText(((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).getContext(), "즐겨찾기를 해제했습니다.", Toast.LENGTH_SHORT).show();
                            tv_cloFavorite.setText("no");
                            iv_heart.setImageResource(R.drawable.favorite_empty);
                        }
                    }
                    else
                        Toast.makeText(((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).getContext(), "즐겨찾기 실패", Toast.LENGTH_SHORT).show();
                    break ;

                case R.id.iv_modify :
                    //((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).Cloth_Info.setVisibility(View.GONE);
                    ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).Cloth_Info_edit.setVisibility(View.VISIBLE);
                    ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).tv_edit_date.setText(((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).tv_date.getText());
                    break ;

                case R.id.iv_delete : //삭제
                    //확인 Alert 다이얼로그
                    try {
                        res = new DeleteTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, tv_cloNo.getText().toString()).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if("ok".equals(res)){
                        Toast.makeText(((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).getContext(), "옷을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                        ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).Cloth_Info.setVisibility(View.GONE);


                        ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).onResume();

                        //Intent intent = ((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).getIntent();
                        ////intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        //((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).finish();
                        //startActivity(intent);
                    }else{
                        Toast.makeText(((fragment_closet)getParentFragmentManager().findFragmentById(R.id.fragment)).getContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }



    //프래그먼트 갱신
    private void refresh(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }



}
