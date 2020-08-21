package com.Project.Closet.home.recommend;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.VO.BoardVO;
import com.Project.Closet.HTTP.VO.DetailFeedVO;
import com.Project.Closet.R;
import com.Project.Closet.codi.addCodi.MyPagerAdapter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recommendPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recommendPagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String boardParams = "recommendedBoards";

    // TODO: Rename and change types of parameters

    private ArrayList<DetailFeedVO> recommendedBoardsWithChild;

    ImageView iv_codi1, iv_codi2, iv_codi3, iv_codi4, iv_codi5;
    ArrayList<ImageView> iv_codi_list;
    ArrayList<Integer> index_resourceID;

    ImageView selected_iv;
    int selected_index;



    //뷰페이저 선언
    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;


    public recommendPagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param boards Parameter 1.
     * @return A new instance of fragment PagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static recommendPagerFragment newInstance(ArrayList<DetailFeedVO> boards) {
        recommendPagerFragment fragment = new recommendPagerFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(boardParams, boards);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recommendedBoardsWithChild = getArguments().getParcelableArrayList(boardParams);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.layout_recommend_viewpager, container, false);



        HashMap<String, ArrayList<DetailFeedVO>> feedMapByBoardNo = new HashMap<>();

        //게시물 번호 별로 나누기
        for(int i =0; i<recommendedBoardsWithChild.size();i++){

            DetailFeedVO thisData =  recommendedBoardsWithChild.get(i);
            //해당 레코드의 게시물 번호가 key에 없으면
            if(!feedMapByBoardNo.containsKey(thisData.getBoardNo())){
                //새로운 리스트를 생성해 레코드를 추가하고 key로 집어넣음
                ArrayList<DetailFeedVO> newDataList = new ArrayList<>();
                newDataList.add(thisData);
                feedMapByBoardNo.put(thisData.getBoardNo(),newDataList);
            } else{ //해당 key가 존재하면 해당 레코드를 리스트에 추가
                ArrayList<DetailFeedVO> ExistedList = feedMapByBoardNo.get(thisData.getBoardNo());
                ExistedList.add(thisData);
            }

        }






        iv_codi1 = view.findViewById(R.id.iv_codi1);
        iv_codi2 = view.findViewById(R.id.iv_codi2);
        iv_codi3 = view.findViewById(R.id.iv_codi3);
        iv_codi4 = view.findViewById(R.id.iv_codi4);
        iv_codi5 = view.findViewById(R.id.iv_codi5);

        BtnOnClickListener onClickListener = new BtnOnClickListener();
        iv_codi1.setOnClickListener(onClickListener);
        iv_codi2.setOnClickListener(onClickListener);
        iv_codi3.setOnClickListener(onClickListener);
        iv_codi4.setOnClickListener(onClickListener);
        iv_codi5.setOnClickListener(onClickListener);


        //뷰페이저 어댑터 설정
        viewPager = (ViewPager) view.findViewById(R.id.viewPager) ;
        //viewPager.setOffscreenPageLimit(1); //캐싱을 해놓을 프래그먼트 개수

        pagerAdapter = new MyPagerAdapter(getChildFragmentManager()); //getSupportFragmentManager로 프래그먼트 참조가능



        int BoardNum = feedMapByBoardNo.size();
        iv_codi_list = new ArrayList<ImageView>(Arrays.asList(iv_codi1, iv_codi2, iv_codi3, iv_codi4, iv_codi5));
        index_resourceID = new ArrayList<Integer>(Arrays.asList(R.id.iv_codi1, R.id.iv_codi2, R.id.iv_codi3, R.id.iv_codi4, R.id.iv_codi5));


        //뷰페이저에 코디 개수만큼 프래그먼트 추가
        for (Map.Entry<String, ArrayList<DetailFeedVO>> entry : feedMapByBoardNo.entrySet()) {
            //String key = entry.getKey();
            ArrayList<DetailFeedVO> boardData = entry.getValue();
            pagerAdapter.addItem(recommendedItemFragment.newInstance(boardData));
        }
        viewPager.setAdapter(pagerAdapter);


        // 추천 코디 개수만큼 이미지 보여주기
        int i=0;
        for (Map.Entry<String, ArrayList<DetailFeedVO>> entry : feedMapByBoardNo.entrySet()) {
            ArrayList<DetailFeedVO> boardData = entry.getValue();
            iv_codi_list.get(i).setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(Global.baseURL+ boardData.get(0).getBoardImagePath()).into(iv_codi_list.get(i));
            i++;
        }

        // 안 쓰는 이미지뷰 끄기
        for(i=4; i>BoardNum-1; i--){
            iv_codi_list.get(i).setVisibility(View.GONE);
        }

        viewPager.setCurrentItem(0); //첫페이지 초기 설정
        selected_index=0;
        selected_iv = iv_codi_list.get(0);


        return view;
    }

    class BtnOnClickListener implements Button.OnClickListener {
        String res="";

        @Override
        public void onClick(View view) {
            Intent intent;
            Integer resourceID = view.getId();
            int dp40;
            int dp50;
            switch (view.getId()) {
                case R.id.iv_codi1 :
                case R.id.iv_codi2 :
                case R.id.iv_codi3 :
                case R.id.iv_codi4 :
                case R.id.iv_codi5 :
                    if(selected_index!=index_resourceID.indexOf(resourceID)){
                        //이전 선택 이미지뷰 작게 변경
                        dp40 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,40,getResources().getDisplayMetrics());
                        dp50 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics());
                        selected_iv.getLayoutParams().height=dp40;
                        selected_iv.getLayoutParams().width=dp40;
                        selected_iv.requestLayout();
                        //현재 선택된 페이지로 바꾸고 이미지뷰 크게 변경
                        selected_index = index_resourceID.indexOf(resourceID);
                        selected_iv = iv_codi_list.get(selected_index);
                        selected_iv.getLayoutParams().height=dp50;
                        selected_iv.getLayoutParams().width=dp50;
                        selected_iv.requestLayout();
                        viewPager.setCurrentItem(selected_index);
                    }
                    break;
            }
        }
    }
}
