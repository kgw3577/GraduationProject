package com.Project.Closet.closet.addClothes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.Project.Closet.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;


public class Dialogue_choose_category extends BottomSheetDialogFragment{

    public static Dialogue_choose_category getInstance() { return new Dialogue_choose_category(); }


    activity_addClothes parentActivity;

    private ViewPager viewPager;
    private PagerAdapter_choose_category pagerAdapter;

    public String kind="";
    public String category="";
    public String detailCategory="";

    private LinearLayout msgLo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dailogue_choose_category, container,false);

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(1); //캐싱을 해놓을 프래그먼트 개수
        viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        pagerAdapter = new PagerAdapter_choose_category(getChildFragmentManager()); //getSupportFragmentManager로 프래그먼트 참조가능

        //뷰페이저에 프래그먼트 설정
        pagerAdapter.addItem(Page_choose_dialogue.newInstance("kind","","")); //0


        viewPager.setAdapter(pagerAdapter);

        return view;
    }


    public void setKind(String kind) {
        this.kind = kind;
        pagerAdapter.addItem(Page_choose_dialogue.newInstance("category",kind,"")); //1
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(1);
    }

    public void setCategory(String category) {
        this.category = category;
        ArrayList<String> detailList = new ArrayList<String>(Arrays.asList("반팔티셔츠", "긴팔티셔츠", "셔츠",
                "니트","후디","스커트","원피스","자켓","점퍼","코트","패딩","모자","상의 기타","하의 기타", "한벌옷 기타"));

        if(detailList.contains(category)){
            pagerAdapter.addItem(Page_choose_dialogue.newInstance("detailCategory",kind,category));
            pagerAdapter.notifyDataSetChanged();
            viewPager.setCurrentItem(2);
        }else{
            this.detailCategory = category;
            parentActivity= (activity_addClothes) getActivity();
            parentActivity.setCategories(kind,category,detailCategory);
            dismiss();
        }
    }

    public void setDetailCategory(String detailCategory) {
        this.detailCategory = detailCategory;
        parentActivity= (activity_addClothes) getActivity();
        parentActivity.setCategories(kind,category,detailCategory);
        dismiss();
    }


}