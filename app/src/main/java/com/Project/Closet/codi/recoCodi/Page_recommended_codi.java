package com.Project.Closet.codi.recoCodi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.ClothesService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.ClothesVO;
import com.Project.Closet.R;
import com.Project.Closet.closet.fragment_closet;
import com.Project.Closet.util.ClothesListAdapter;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;

/* 그리드 사이즈 조절 방법 :
어댑터 변경, 그리드 사이즈 변경, 페이지사이즈 변경
(small(4), medium(3), large(2)) 20p, 15p, 10p
*/

public class Page_recommended_codi extends Fragment {

    String color1;
    String color2;
    int tmp;
    ArrayList<ClothesVO> clothesList;


    //파트별 뷰 선언
    ImageView ivTop; //상의 파트
    ImageView ivBottom; //하의 파트
    ImageView ivSuit;   //한벌옷 파트
    ImageView ivOuter;  //외투 파트
    ImageView ivShoes;  //신발 파트
    ImageView ivBag;    //가방 파트
    ImageView ivAccessory4; //액세서리4 파트


    public static Page_recommended_codi newInstance(String color1, String color2, int tmp, ArrayList<ClothesVO> clothesList) {

        Bundle args = new Bundle();
        args.putString("color1", color1);  // 키값, 데이터
        args.putString("color2", color2);  // 키값, 데이터
        args.putInt("tmp", tmp);
        args.putParcelableArrayList("clothesList",clothesList);

        Page_recommended_codi fragment = new Page_recommended_codi();
        fragment.setArguments(args);
        return fragment;
    }

    public interface FragListener{
        void ReceivedData(Object data);
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
            color1 = args.getString("color1");
            color2 = args.getString("color2");
            tmp = args.getInt("tmp");
            clothesList = args.getParcelableArrayList("clothesList");
        }

        for (ClothesVO clo : clothesList){
            if("상의".equals(clo.getKind())){
                Glide.with(getContext()).load(Global.baseURL+clo.getFilePath()).into(ivTop);
            }
            if("하의".equals(clo.getKind())){
                Glide.with(getContext()).load(Global.baseURL+clo.getFilePath()).into(ivBottom);
            }
            if("가방".equals(clo.getKind())){
                Glide.with(getContext()).load(Global.baseURL+clo.getFilePath()).into(ivBag);
            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_codi_recommend_page, container, false);
        //이미지뷰 불러오기
        ivTop = (ImageView) view.findViewById(R.id.iv_top);
        ivBottom = (ImageView) view.findViewById(R.id.iv_bottom);
        ivSuit = (ImageView) view.findViewById(R.id.iv_suit);
        ivOuter= (ImageView) view.findViewById(R.id.iv_outer);
        ivShoes= (ImageView) view.findViewById(R.id.iv_shoes);
        ivBag= (ImageView) view.findViewById(R.id.iv_bag);
        ivAccessory4= (ImageView) view.findViewById(R.id.iv_acc4);


        return view;
    }

}
