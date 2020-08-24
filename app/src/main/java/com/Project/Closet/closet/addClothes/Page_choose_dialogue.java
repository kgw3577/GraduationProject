package com.Project.Closet.closet.addClothes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.R;
import com.Project.Closet.closet.fragment_closet;
import com.Project.Closet.util.ClothesListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/* 그리드 사이즈 조절 방법 :
어댑터 변경, 그리드 사이즈 변경, 페이지사이즈 변경
(small(4), medium(3), large(2)) 20p, 15p, 10p
*/

public class Page_choose_dialogue extends Fragment {

    String[] items;
    Dialogue_choose_category parentFragment;

    String step;
    String kind;
    String category;
    String detailCategory;

    String selected;



    public static Page_choose_dialogue newInstance(String step, String kind, String category) {

        Bundle args = new Bundle();
        args.putString("step", step);  // 키값, 데이터
        args.putString("kind", kind);  // 키값, 데이터
        args.putString("category", category);  // 키값, 데이터

        Page_choose_dialogue fragment = new Page_choose_dialogue();
        fragment.setArguments(args);
        return fragment;
    }


    public interface FragListener{
        void ReceivedData(String selected,String step);
    }
    private FragListener mFragListener;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof FragListener){
            mFragListener = (FragListener) getActivity();
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments(); // 데이터 받기
        if(args != null)
        {
            step = args.getString("step");
            kind = args.getString("kind");  // 키값, 데이터
            category = args.getString("category");  // 키값, 데이터
        }

        switch(step){
            case "kind" :
                items = getResources().getStringArray(R.array.Kind);
                break;
            case "category" :
                switch(kind){
                    case "상의" :
                        items = getResources().getStringArray(R.array.상의);
                        break;
                    case "하의" :
                        items = getResources().getStringArray(R.array.하의);
                        break;
                    case "한벌옷" :
                        items = getResources().getStringArray(R.array.한벌옷);
                        break;
                    case "외투" :
                        items = getResources().getStringArray(R.array.외투);
                        break;
                    case "신발" :
                        items = getResources().getStringArray(R.array.신발);
                        break;
                    case "가방" :
                        items = getResources().getStringArray(R.array.가방);
                        break;
                    case "액세서리" :
                        items = getResources().getStringArray(R.array.액세서리);
                        break;
                }
                break;
            case "detailCategory" :
                switch(category){
                    case "반팔티셔츠" :
                        items = getResources().getStringArray(R.array.반팔티셔츠);
                        break;
                    case "긴팔티셔츠" :
                        items = getResources().getStringArray(R.array.긴팔티셔츠);
                        break;
                    case "셔츠" :
                        items = getResources().getStringArray(R.array.셔츠);
                        break;
                    case "니트" :
                        items = getResources().getStringArray(R.array.니트);
                        break;
                    case "후디" :
                        items = getResources().getStringArray(R.array.후디);
                        break;
                    case "스커트" :
                        items = getResources().getStringArray(R.array.스커트);
                        break;
                    case "원피스" :
                        items = getResources().getStringArray(R.array.원피스);
                        break;
                    case "자켓" :
                        items = getResources().getStringArray(R.array.자켓);
                        break;
                    case "점퍼" :
                        items = getResources().getStringArray(R.array.점퍼);
                        break;
                    case "코트" :
                        items = getResources().getStringArray(R.array.코트);
                        break;
                    case "패딩" :
                        items = getResources().getStringArray(R.array.패딩);
                        break;
                    case "모자" :
                        items = getResources().getStringArray(R.array.모자);
                        break;
                    case "기타" :
                        items = getResources().getStringArray(R.array.기타);
                        break;
                }
                break;
            default:
                items = getResources().getStringArray(R.array.Kind);
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_choose_category_list, container, false);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, items) ;

        ListView listview = (ListView) view.findViewById(R.id.listview) ;
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) parent.getItemAtPosition(position);
                parentFragment = (Dialogue_choose_category)getParentFragment();
                switch(step){
                    case "kind":
                        parentFragment.setKind(selected);
                        break;
                    case "category":
                        parentFragment.setCategory(selected);
                        break;
                    case "detailCategory":
                        parentFragment.setDetailCategory(selected);
                        break;
                }

            }
        });

/*
        //리사이클러 뷰 설정하기
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


 */

        return view;
    }


    //프래그먼트 갱신
    private void refresh(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }
}
