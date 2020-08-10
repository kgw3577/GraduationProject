package com.Project.Closet.social.subfragment;

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
import com.Project.Closet.HTTP.Service.SocialService;
import com.Project.Closet.HTTP.Session.preference.MySharedPreferences;
import com.Project.Closet.HTTP.VO.FeedVO;
import com.Project.Closet.R;
import com.Project.Closet.social.fragment_social;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/* 그리드 사이즈 조절 방법 :
어댑터 변경, 그리드 사이즈 변경, 페이지사이즈 변경
(small(4), medium(3), large(2)) 20p, 15p, 10p
*/

public class Fragment_Feed extends Fragment {

    fragment_social parentFragment;

    String identifier; //프래그먼트의 종류를 알려줌
    String size;

    int gridsize=2;
    String pagesize="8";

    int page=0;
    RecyclerView rv_post;
    ArrayList<String> ImageUrlList = new ArrayList<String>();
    ArrayList<FeedVO> feedList = new ArrayList();
    
    //리사이클러뷰 어댑터
    FeedRecyclerAdapter feedRecyclerAdapter;
    Call<List<FeedVO>> feedListCall; // 게시글 VO 리스트를 응답으로 받는 http 요청

    MySharedPreferences pref = MySharedPreferences.getInstanceOf(getContext());
    String userID = pref.getUserID();

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
        parentFragment = ((fragment_social)getParentFragment());


        Bundle args = getArguments(); // 데이터 받기
        if(args != null)
        {
            identifier = args.getString("identifier");
            size = args.getString("size");

            switch (size){
                case "small":
                    gridsize = 4; //스몰 그리드 4x5
                    pagesize="26"; //스몰 페이지 사이즈 25
                    break;
                case "medium":
                    gridsize = 3; //미디엄 그리드 3x4
                    pagesize="18"; //미디엄 페이지 사이즈 17
                    break;
                case "large":
                    gridsize = 2; //라지 그리드 2x3
                    pagesize="8"; //라지 페이지 사이즈 7
                    break;
                case "xLarge":
                    gridsize = 1; //X라지 그리드 1x2
                    pagesize="5"; //X라지 페이지 사이즈 5
                    break;
            }
        }


        //리사이클러뷰 어댑터 초기화
        feedRecyclerAdapter = new FeedRecyclerAdapter(feedList);

        feedRecyclerAdapter.setOnItemClickListener(new FeedRecyclerAdapter.OnItemClickListener() {

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





    public class networkTask extends AsyncTask<String, Void, List<FeedVO>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }


        @Override
        protected List<FeedVO> doInBackground(String... params) {
            String myID = MySharedPreferences.getInstanceOf(getContext()).getUserID();
            if(identifier==null)
                feedListCall = SocialService.getRetrofit(getActivity()).showNewestFeed(myID, params[0], pagesize);
            else{
                switch(identifier){
                    case "following" : //팔로잉 피드
                        feedListCall = SocialService.getRetrofit(getActivity()).showFollowingFeed(userID, params[0], pagesize);
                        break;
                    case "best" : //인기 피드
                        feedListCall = SocialService.getRetrofit(getActivity()).showNewestFeed(myID, params[0], pagesize);
                        break;
                    case "newest" : //최신 피드
                        feedListCall = SocialService.getRetrofit(getActivity()).showNewestFeed(myID, params[0], pagesize);
                        break;
                    default : //해당 유저 게시글 조회 -> 해당하는 case가 없을 경우 identifier가 userID임.
                        feedListCall = SocialService.getRetrofit(getActivity()).showNewestFeed(myID, params[0], pagesize);
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
        protected void onPostExecute(List<FeedVO> feeds) {
            super.onPostExecute(feeds);
            if(feeds!=null) {
                for(FeedVO e:feeds) {
                    //게시글 데이터를 받아온 후 이미지 url 리스트를 갱신
                    ImageUrlList.add(new String(Global.baseURL+e.getImagePath()));
                    feedList.add(e);
                }
                feedRecyclerAdapter.notifyDataSetChanged();
            }
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


}
