package com.Project.Closet.social.space;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
import com.Project.Closet.social.subfragment.FeedRecyclerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/* 그리드 사이즈 조절 방법 :
어댑터 변경, 그리드 사이즈 변경, 페이지사이즈 변경
(small(4), medium(3), large(2)) 20p, 15p, 10p
*/

public class activity_space_detail extends AppCompatActivity {


    String identifier; //프래그먼트의 종류를 알려줌

    int gridsize=1;
    String pagesize="5";

    int page=0;
    RecyclerView rv_post;
    ArrayList<String> ImageUrlList = new ArrayList<String>();
    ArrayList<FeedVO> feedList = new ArrayList();
    
    //리사이클러뷰 어댑터
    FeedRecyclerAdapter feedRecyclerAdapter;
    Call<List<FeedVO>> feedListCall; // 게시글 VO 리스트를 응답으로 받는 http 요청

    MySharedPreferences pref = MySharedPreferences.getInstanceOf(getApplicationContext());
    String userID = pref.getUserID();


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ArrayList<FeedVO> feedList = getIntent().getExtras().getParcelableArray("feedList");
        int pos = getIntent().getExtras().getInt("position");


        //리사이클러뷰 어댑터 초기화
        feedRecyclerAdapter = new FeedRecyclerAdapter(feedList);
        feedRecyclerAdapter.setOnItemClickListener(new FeedRecyclerAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View itemView, final int pos) {
            //
        }

        ////page 계산!



    });

        //리사이클러 뷰 설정하기
        setContentView(R.layout.layout_share);
        rv_post = (RecyclerView) findViewById(R.id.post_list);
        rv_post.setLayoutManager(new GridLayoutManager(getApplicationContext(), gridsize)); //그리드 사이즈 설정
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
}




    public class networkTask extends AsyncTask<String, Void, List<FeedVO>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }


        @Override
        protected List<FeedVO> doInBackground(String... params) {
            String myID = MySharedPreferences.getInstanceOf(getApplicationContext()).getUserID();
            if(identifier==null)
                feedListCall = SocialService.getRetrofit(activity_space_detail.this).showNewestFeed(myID, params[0], pagesize);
            else{
                switch(identifier){
                    case "following" : //팔로잉 피드
                        feedListCall = SocialService.getRetrofit(activity_space_detail.this).showFollowingFeed(userID, params[0], pagesize);
                        break;
                    case "best" : //인기 피드
                        feedListCall = SocialService.getRetrofit(activity_space_detail.this).showNewestFeed(myID, params[0], pagesize);
                        break;
                    case "newest" : //최신 피드
                        feedListCall = SocialService.getRetrofit(activity_space_detail.this).showNewestFeed(myID, params[0], pagesize);
                        break;
                    default : //해당 유저 게시글 조회 -> 해당하는 case가 없을 경우 identifier가 userID임.
                        feedListCall = SocialService.getRetrofit(activity_space_detail.this).showNewestFeed(myID, params[0], pagesize);
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

}
