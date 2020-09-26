package com.Project.Closet.codi.recoCodi;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.Project.Closet.Global;
import com.Project.Closet.R;
import com.Project.Closet.util.Codi;
import com.bumptech.glide.Glide;

/* 그리드 사이즈 조절 방법 :
어댑터 변경, 그리드 사이즈 변경, 페이지사이즈 변경
(small(4), medium(3), large(2)) 20p, 15p, 10p
*/

public class Page_recommended_codi extends Fragment {

    String color1;
    String color2;
    int tmp;
    Codi codi;


    //파트별 뷰 선언
    ImageView ivTop; //상의 파트
    ImageView ivBottom; //하의 파트
    ImageView ivSuit;   //한벌옷 파트
    ImageView ivOuter;  //외투 파트
    ImageView ivShoes;  //신발 파트
    ImageView ivBag;    //가방 파트
    ImageView ivAccessory; //액세서리4 파트


    public static Page_recommended_codi newInstance(String color1, String color2, int tmp, Codi codi) {

        Bundle args = new Bundle();
        args.putString("color1", color1);  // 키값, 데이터
        args.putString("color2", color2);  // 키값, 데이터
        args.putInt("tmp", tmp);
        args.putParcelable("codi",codi);

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
            codi = args.getParcelable("codi");
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
        ivAccessory = (ImageView) view.findViewById(R.id.iv_acc4);

        if(codi.getTop().getCategory()!=null)
            Glide.with(getContext()).load(Global.baseURL+codi.getTop().getFilePath()).into(ivTop);
        else
            ivTop.setVisibility(View.GONE);

        if(codi.getBottom().getCategory()!=null)
            Glide.with(getContext()).load(Global.baseURL+codi.getBottom().getFilePath()).into(ivBottom);
        else
            ivBottom.setVisibility(View.GONE);

        if(codi.getSuit().getCategory()!=null)
            Glide.with(getContext()).load(Global.baseURL+codi.getSuit().getFilePath()).into(ivSuit);
        else
            ivSuit.setVisibility(View.GONE);

        if(codi.getOuter().getCategory()!=null)
            Glide.with(getContext()).load(Global.baseURL+codi.getOuter().getFilePath()).into(ivOuter);
        else
            ivOuter.setVisibility(View.GONE);

        if(codi.getShoes().getCategory()!=null)
            Glide.with(getContext()).load(Global.baseURL+codi.getShoes().getFilePath()).into(ivShoes);
        else
            ivShoes.setVisibility(View.GONE);

        if(codi.getBag().getCategory()!=null)
            Glide.with(getContext()).load(Global.baseURL+codi.getBag().getFilePath()).into(ivBag);
        else
            ivBag.setVisibility(View.GONE);

        if(codi.getAccessory().getCategory()!=null)
            Glide.with(getContext()).load(Global.baseURL+codi.getAccessory().getFilePath()).into(ivAccessory);
        else
            ivAccessory.setVisibility(View.GONE);

        return view;
    }

}
