package com.Project.Closet.social.subfragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.HTTP.Service.SocialService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.DetailFeedVO;
import com.Project.Closet.HTTP.VO.DetailFeedVO_Extended;
import com.Project.Closet.HTTP.VO.HeartVO;
import com.Project.Closet.R;
import com.Project.Closet.closet.fragment_closet;
import com.Project.Closet.home.activity_home;
import com.Project.Closet.home.recommend.recommendedItemFragment;
import com.Project.Closet.social.detailFeed.activity_thisFeed;
import com.Project.Closet.social.fragment_social;
import com.Project.Closet.social.space.activity_space;
import com.Project.Closet.util.NumFormat;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

/* 그리드 사이즈 조절 방법 :
어댑터 변경, 그리드 사이즈 변경, 페이지사이즈 변경
(small(4), medium(3), large(2)) 20p, 15p, 10p
*/

public class Fragment_Feed extends Fragment {

    fragment_social parentFragment;

    final int SHOW_THIS_FEED = 690;

    String identifier; //프래그먼트의 종류를 알려줌
    String size;

    int gridsize=2;
    String pagesize="8";

    int page=0;
    RecyclerView rv_post;
    List<ArrayList<DetailFeedVO>> feedListByBoardNo;
    HashMap<String, ArrayList<DetailFeedVO>> feedMapByBoardNo;
    
    //리사이클러뷰 어댑터
    FeedRecyclerAdapter feedRecyclerAdapter;
    Call<List<DetailFeedVO>> feedListCall; // 게시글 VO 리스트를 응답으로 받는 http 요청

    MySharedPreferences pref;
    String userID;

    public static Fragment_Feed newInstance(String identifier, String size) {

        Bundle args = new Bundle();
        args.putString("identifier", identifier);  // 키값, 데이터
        args.putString("size", size);



        Fragment_Feed fragment = new Fragment_Feed();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        feedListByBoardNo= new ArrayList<>();
        feedMapByBoardNo = new HashMap<>();
        pref = MySharedPreferences.getInstanceOf(getContext());
        userID = pref.getUserID();
        parentFragment = ((fragment_social)getParentFragment());


        Bundle args = getArguments(); // 데이터 받기
        if(args != null)
        {
            identifier = args.getString("identifier");
            size = args.getString("size");

            switch (size){
                case "small":
                    gridsize = 4; //스몰 그리드 4x5
                    pagesize="150"; //스몰 페이지 사이즈 25*6
                    break;
                case "medium":
                    gridsize = 3; //미디엄 그리드 3x4
                    pagesize="102"; //미디엄 페이지 사이즈 17*6
                    break;
                case "large":
                    gridsize = 2; //라지 그리드 2x3
                    pagesize="42"; //라지 페이지 사이즈 7*6
                    break;
                case "xLarge":
                    gridsize = 1; //X라지 그리드 1x2
                    pagesize="30"; //X라지 페이지 사이즈 5*6
                    break;
            }
        }


        //리사이클러뷰 어댑터 초기화
        feedRecyclerAdapter = new FeedRecyclerAdapter(feedListByBoardNo);
        feedRecyclerAdapter.setOnItemClickListener(new FeedRecyclerAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View itemView, final int pos) {


            Intent intent;
            String result=null;
            Context context = itemView.getContext();

            ArrayList<DetailFeedVO> selectedFeedDataList;
            DetailFeedVO feedInfo;

            MySharedPreferences pref = MySharedPreferences.getInstanceOf(context);
            String myID = pref.getUserID();

            switch(itemView.getId()){
                case R.id.profile_area :
                    selectedFeedDataList = feedListByBoardNo.get(pos);
                    feedInfo = selectedFeedDataList.get(0);
                    intent = new Intent(context, activity_space.class);
                    assert feedInfo != null;
                    intent.putExtra("feedInfo", feedInfo);
                    context.startActivity(intent);
                    break;
                default:
                    selectedFeedDataList = feedListByBoardNo.get(pos);
                    intent = new Intent(context, activity_thisFeed.class);
                    assert selectedFeedDataList != null;
                    intent.putParcelableArrayListExtra("selectedFeedList", selectedFeedDataList);
                    intent.putExtra("pos", pos);
                    startActivityForResult(intent, SHOW_THIS_FEED);
                    break;


            }




            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                }
            };

        }





    });
}

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        //현재 페이지수와 함께 웹서버에 옷 데이터 요청
        new networkTask().execute(Integer.toString(page));

        //리사이클러 뷰 설정하기
        View view = inflater.inflate(R.layout.layout_share, container, false);
        rv_post = (RecyclerView) view.findViewById(R.id.post_list);
        rv_post.setLayoutManager(new GridLayoutManager(getContext(), gridsize)); //그리드 사이즈 설정
        rv_post.setAdapter(feedRecyclerAdapter);
        rv_post.setNestedScrollingEnabled(true);


        rv_post.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (!rv_post.canScrollVertically(-1)) {
                    //스크롤이 최상단이면 데이터를 갱신한다
                    //page = 0;
                    //new networkTask().execute(Integer.toString(page));
                    //Log.e("test","데이터 갱신");
                }
                else if (!rv_post.canScrollVertically(1)) {
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





    public class networkTask extends AsyncTask<String, Void, List<DetailFeedVO>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }


        @Override
        protected List<DetailFeedVO> doInBackground(String... params) {
            //인자 params[0]은 page.

            DetailFeedVO_Extended feedFilter = new DetailFeedVO_Extended();
            String myID = MySharedPreferences.getInstanceOf(getContext()).getUserID();

            if (identifier == null){
                feedFilter.setMyID(myID);
                feedListCall = SocialService.getRetrofit(getActivity()).searchFeed(feedFilter, params[0], pagesize);
            }
            else{
                switch(identifier){
                    case "following" : //팔로잉 피드
                        feedFilter.setMyID(myID);
                        feedFilter.setMode("follow");
                        feedListCall = SocialService.getRetrofit(getActivity()).searchFeed(feedFilter, params[0], pagesize);
                        break;
                    case "best" : //인기 피드. 설정해야 함.
                        feedFilter.setMyID(myID);
                        feedListCall = SocialService.getRetrofit(getActivity()).searchFeed(feedFilter, params[0], pagesize);
                        break;
                    case "newest" : //최신 피드
                        feedFilter.setMyID(myID);
                        feedListCall = SocialService.getRetrofit(getActivity()).searchFeed(feedFilter, params[0], pagesize);
                        break;
                }
            }


            try {
                return feedListCall.execute().body();

                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<DetailFeedVO> feedsDataList) {
            super.onPostExecute(feedsDataList);
            String thisBoardNo;
            int numChild;
            if(feedsDataList!=null&& feedsDataList.size()!=0) {
                for(DetailFeedVO thisData :feedsDataList) {
                    /*받아온 레코드를 게시물 별로 분류*/
                    //해당 레코드의 게시물 번호가 key에 없으면
                    thisBoardNo = thisData.getBoardNo();
                    numChild = Integer.parseInt(thisData.getBoard_numChild());
                    if(!feedMapByBoardNo.containsKey(thisBoardNo)){
                        //새로운 리스트를 생성해 레코드를 추가하고 key로 집어넣음
                        ArrayList<DetailFeedVO> newDataList = new ArrayList<>();
                        newDataList.add(thisData);
                        feedMapByBoardNo.put(thisBoardNo,newDataList);
                    } else{ //해당 key가 존재하면 해당 레코드를 리스트에 추가
                        ArrayList<DetailFeedVO> ExistedList = feedMapByBoardNo.get(thisBoardNo);
                        ExistedList.add(thisData);
                    }
                    //한 게시물 분류가 끝났다면 - 맵에서 게시물의 데이터리스트를 찾아 리사이클러뷰 데이터리스트에 추가함.
                    if(feedMapByBoardNo.get(thisBoardNo).size()==numChild){
                        feedListByBoardNo.add(feedMapByBoardNo.get(thisBoardNo));
                    }
                }
                feedRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }

    public class heartTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }


        @Override
        protected String doInBackground(String... params) {
            HeartVO heartInfo = new HeartVO(params[0],params[1]);
            //params : 게시물 번호, 유저

            Call<String> stringCall = SocialService.getRetrofit(getContext()).executeHeart(heartInfo);

            //인자 params[0]은 page.

            try {
                return stringCall.execute().body();

                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SHOW_THIS_FEED && resultCode == RESULT_OK){
            int pos = data.getExtras().getInt("pos");
            ArrayList<DetailFeedVO> feedInfo = data.getExtras().getParcelableArrayList("feedInfo");
            feedListByBoardNo.set(pos,feedInfo);
            feedRecyclerAdapter.notifyDataSetChanged();
            activity_home activity = (activity_home)getActivity();
            activity.is_closet_changed = true;
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

    public void showThisFeed(int pos){

    }


}
