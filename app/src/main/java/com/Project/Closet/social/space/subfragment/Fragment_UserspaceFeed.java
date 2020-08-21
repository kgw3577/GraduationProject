package com.Project.Closet.social.space.subfragment;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.SocialService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.DetailFeedVO;
import com.Project.Closet.HTTP.VO.DetailFeedVO_Extended;
import com.Project.Closet.HTTP.VO.FeedVO;
import com.Project.Closet.R;
import com.Project.Closet.social.space.activity_space;
import com.Project.Closet.social.subfragment.UserspaceFeedRecyclerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/* 그리드 사이즈 조절 방법 :
어댑터 변경, 그리드 사이즈 변경, 페이지사이즈 변경
(small(4), medium(3), large(2)) 20p, 15p, 10p
*/

public class Fragment_UserspaceFeed extends Fragment {


    String identifier; //프래그먼트의 종류를 알려줌
    String size;

    int gridsize=2;
    String pageSize="8";

    int page=0;
    RecyclerView rv_post;

    List<ArrayList<DetailFeedVO>> feedListByBoardNo;
    HashMap<String, ArrayList<DetailFeedVO>> feedMapByBoardNo;
    
    //리사이클러뷰 어댑터
    UserspaceFeedRecyclerAdapter userspaceFeedRecyclerAdapter;
    Call<List<DetailFeedVO>> feedListCall; // 게시글 VO 리스트를 응답으로 받는 http 요청

    String targetID;
    MySharedPreferences pref = MySharedPreferences.getInstanceOf(getContext());

    public static Fragment_UserspaceFeed newInstance(String identifier, String size) {

        Bundle args = new Bundle();
        args.putString("identifier", identifier);  // 키값, 데이터
        args.putString("size", size);

        Fragment_UserspaceFeed fragment = new Fragment_UserspaceFeed();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        feedListByBoardNo= new ArrayList<>();
        feedMapByBoardNo = new HashMap<>();

        Bundle args = getArguments(); // 데이터 받기
        if(args != null)
        {
            identifier = args.getString("identifier");
            size = args.getString("size");

            switch (size){
                case "small":
                    gridsize = 4; //스몰 그리드 4x5
                    pageSize="150"; //스몰 페이지 사이즈 25*6
                    break;
                case "medium":
                    gridsize = 3; //미디엄 그리드 3x4
                    pageSize="102"; //미디엄 페이지 사이즈 17*6
                    break;
                case "large":
                    gridsize = 2; //라지 그리드 2x3
                    pageSize="42"; //라지 페이지 사이즈 7*6
                    break;
                case "xLarge":
                    gridsize = 1; //X라지 그리드 1x2
                    pageSize="30"; //X라지 페이지 사이즈 5*6
                    break;
            }
        }



        //리사이클러뷰 어댑터 초기화
        userspaceFeedRecyclerAdapter = new UserspaceFeedRecyclerAdapter(feedListByBoardNo);

        userspaceFeedRecyclerAdapter.setOnItemClickListener(new UserspaceFeedRecyclerAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View itemView, final int pos) {
            //
        }





    });
}

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //현재 페이지수와 함께 웹서버에 옷 데이터 요청
        new networkTask().execute(Integer.toString(page));

        activity_space activity = (activity_space) getActivity();
        targetID = activity.targetID;

        //리사이클러 뷰 설정하기
        View view = inflater.inflate(R.layout.layout_share, container, false);
        rv_post = (RecyclerView) view.findViewById(R.id.post_list);
        rv_post.setLayoutManager(new GridLayoutManager(getContext(), gridsize)); //그리드 사이즈 설정
        rv_post.setAdapter(userspaceFeedRecyclerAdapter);
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
            DetailFeedVO_Extended feedFilter = new DetailFeedVO_Extended();
            String myID = MySharedPreferences.getInstanceOf(getContext()).getUserID();
            if(identifier==null){
                feedFilter.setMyID(myID);
                feedFilter.setUserID(targetID);
                feedListCall = SocialService.getRetrofit(getActivity()).searchFeed(feedFilter, params[0], pageSize);
            } else{
                switch(identifier){
                    case "my" : //해당 유저의 피드
                        feedFilter.setMyID(myID);
                        feedFilter.setUserID(targetID);
                        feedListCall = SocialService.getRetrofit(getActivity()).searchFeed(feedFilter, params[0], pageSize);
                        break;
                    case "heart" : //해당 유저가 좋아요한 피드
                        feedFilter.setMyID(myID);
                        feedFilter.setUserID(targetID);
                        feedFilter.setMode("heart");
                        feedListCall = SocialService.getRetrofit(getActivity()).searchFeed(feedFilter,params[0], pageSize);
                        break;
                }
            }
            //인자 params[0]은 page.

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
                    System.out.println(thisBoardNo+"번 "+thisData.getCloIdentifier()+" "+numChild+"개 중");
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
                userspaceFeedRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }




    @Override
    public void onResume() {
        super.onResume();
    }

}
