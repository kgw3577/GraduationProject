package com.Project.Closet.home.recommend;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.SocialService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.DetailFeedVO;
import com.Project.Closet.R;
import com.Project.Closet.closet.addClothes.activity_addClothes;
import com.Project.Closet.closet.closet_activities.activity_closet_share;
import com.Project.Closet.social.detailFeed.activity_thisFeed;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recommendedItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recommendedItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String paramBoardNo = "boardNo";

    // TODO: Rename and change types of parameters
    private String boardNo;
    ArrayList<DetailFeedVO> selectedFeedList;

    public recommendedItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param boardNo Parameter 1.
     * @return A new instance of fragment recommendedItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static recommendedItemFragment newInstance(String boardNo) {
        recommendedItemFragment fragment = new recommendedItemFragment();
        Bundle args = new Bundle();
        args.putString(paramBoardNo, boardNo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            boardNo = getArguments().getString(paramBoardNo);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_recommended_item, container, false);

        selectedFeedList=new ArrayList<DetailFeedVO>();
        try {
            selectedFeedList = new detailInfoTask().execute(boardNo).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DetailFeedVO feedInfo = selectedFeedList.get(0);

        FrameLayout fl_recommendedItem = v.findViewById(R.id.fl_recommended_item);
        ImageView iv_image = v.findViewById(R.id.iv_image);
        TextView child1 = v.findViewById(R.id.child1);
        TextView child2 = v.findViewById(R.id.child2);
        TextView child3 = v.findViewById(R.id.child3);
        TextView child4 = v.findViewById(R.id.child4);
        ImageView iv_heart= v.findViewById(R.id.iv_heart);
        TextView numHeart = v.findViewById(R.id.tv_numHeart);

        Glide.with(getContext()).load(Global.baseURL+feedInfo.getImagePath()).into(iv_image);

        //하트 여부에 따라 아이콘 변경
        String if_hearting = feedInfo.getIf_hearting();
        if(if_hearting.equals("hearting")){
            iv_heart.setImageResource(R.drawable.heart_color);
        }
        else if(if_hearting.equals("not_hearting")){
            iv_heart.setImageResource(R.drawable.heart_empty_white);
        }
        numHeart.setText(feedInfo.getNumHeart()+"");

        int childNum = selectedFeedList.size();
        TextView tv_childs[] = {child1,child2,child3,child4};


        // 포함 옷 개수만큼 식별자 보여주기
        for(int i=0; i<childNum; i++){
            tv_childs[i].setVisibility(View.VISIBLE);
            tv_childs[i].setText("#"+selectedFeedList.get(i).getCloIdentifier());
        }
        // 안 쓰는 텍스트 끄기
        for(int i=3; i>childNum-1; i--){
            tv_childs[i].setVisibility(View.GONE);
        }

        BtnOnClickListener onClickListener = new BtnOnClickListener();
        fl_recommendedItem.setOnClickListener(onClickListener);
        iv_heart.setOnClickListener(onClickListener);


        // Inflate the layout for this fragment
        return v;
    }




    class BtnOnClickListener implements Button.OnClickListener {
        String res="";

        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()) {

                case R.id.iv_heart : //하트 클릭 로직 추가해야 함
                    break;
                case R.id.fl_recommended_item : //전체
                    intent = new Intent(getContext(), activity_thisFeed.class);
                    intent.putParcelableArrayListExtra("selectedFeedList", selectedFeedList);
                    startActivity(intent);
                    break;
            }
        }
    }


    public class detailInfoTask extends AsyncTask<String, Void, ArrayList<DetailFeedVO>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }


        @Override
        protected ArrayList<DetailFeedVO> doInBackground(String... params) {
            String myID = MySharedPreferences.getInstanceOf(getContext()).getUserID();

            Call<List<DetailFeedVO>> feedListCall = SocialService.getRetrofit(getContext()).detailFeed(params[0],myID);
            //인자 params[0]은 클릭한 boardNo.


            try {
                return (ArrayList<DetailFeedVO>) feedListCall.execute().body();

                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<DetailFeedVO> feeds) {
            super.onPostExecute(feeds);
        }
    }








}