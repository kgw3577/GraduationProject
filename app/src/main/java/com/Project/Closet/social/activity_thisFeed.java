package com.Project.Closet.social;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.Service.SocialService;
import com.Project.Closet.HTTP.VO.CommentFeedVO;
import com.Project.Closet.HTTP.VO.FeedVO;
import com.Project.Closet.PostCommentAdapter;
import com.Project.Closet.R;
import com.Project.Closet.util.NumFormat;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class activity_thisFeed extends AppCompatActivity {

    String writerID, writerName, pfImagePath, contents, regDate;
    int numHeart, numComment, boardNo;

    ImageView iv_profileImage;
    TextView tv_writerName, tv_contents, tv_regDate;

    //int gridsize=2;
    String pageSize="10";

    int page=0;
    RecyclerView rv_comment_list;
    ArrayList<CommentFeedVO> commentList = new ArrayList();

    //리사이클러뷰 어댑터
    PostCommentAdapter commentListAdapter;
    Call<List<CommentFeedVO>> commentListCall; // 게시글 VO 리스트를 응답으로 받는 http 요청

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post);

        ArrayList<FeedVO> selectedFeedList = getIntent().getExtras().getParcelableArrayList("selectedFeedList");
        FeedVO feed = selectedFeedList.get(0);

        writerID = feed.getWriterID();
        writerName = feed.getWriterName();
        pfImagePath = feed.getPfImagePath();
        contents = feed.getContents();
        regDate = feed.getRegDate();
        numHeart = feed.getNumHeart();
        numComment = feed.getNumComment();
        boardNo = feed.getBoardNo();



        iv_profileImage = findViewById(R.id.iv_profileImage);
        tv_writerName = findViewById(R.id.tv_writerName);
        tv_contents = findViewById(R.id.tv_contents);
        tv_regDate = findViewById(R.id.tv_regDate);


        //작성 시간 포매팅
        long ts = Timestamp.valueOf(regDate).getTime();
        regDate = NumFormat.formatTimeString(ts);
        //수 포매팅
        //numHeart = NumFormat.formatNumString(numHeart);
        //numComment  = NumFormat.formatNumString(numComment);

        Glide.with(this).load(Global.baseURL+pfImagePath).into(iv_profileImage);
        tv_writerName.setText(writerName);
        tv_contents.setText(contents);
        tv_regDate.setText(regDate);




        //현재 페이지수와 함께 웹서버에 댓글 데이터 요청
        new networkTask().execute(Integer.toString(page),Integer.toString(boardNo));

        //리사이클러뷰 어댑터 초기화
        commentListAdapter = new PostCommentAdapter(commentList);
        commentListAdapter.setOnItemClickListener(new PostCommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

            }
        });



        rv_comment_list = (RecyclerView) findViewById(R.id.rv_comment_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        rv_comment_list.setLayoutManager(mLinearLayoutManager);
        rv_comment_list.setAdapter(commentListAdapter);
        rv_comment_list.setNestedScrollingEnabled(true);
        rv_comment_list.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (!rv_comment_list.canScrollVertically(-1)) {
                    //스크롤이 최상단이면 데이터를 갱신한다
                    //page = 0;
                    //new networkTask().execute(Integer.toString(page));
                    //Log.e("test","데이터 갱신");
                }
                else if (!rv_comment_list.canScrollVertically(1)) {
                    //스크롤이 최하단이면 웹서버에 다음 페이지 옷 데이터 요청
                    new networkTask().execute(Integer.toString(++page),Integer.toString(boardNo));
                    Log.e("test","페이지 수 증가");
                }
                else {
                }
            }
        });


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_comment_list.getContext(),
                mLinearLayoutManager.getOrientation());
        rv_comment_list.addItemDecoration(dividerItemDecoration);

    }


    public class networkTask extends AsyncTask<String, Void, List<CommentFeedVO>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            startTime = Util.getCurrentTime();
        }


        @Override
        protected List<CommentFeedVO> doInBackground(String... params) {


            commentListCall = SocialService.getRetrofit(getBaseContext()).showCommentInBoard(params[1],params[0], pageSize);
            //인자 params[0]은 page. [1]은 boardNo

            try {
                return commentListCall.execute().body();

                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<CommentFeedVO> comments) {
            super.onPostExecute(comments);
            if(comments!=null) {
                for(CommentFeedVO e:comments) {
                    //게시글 데이터를 받아온 후 이미지 url 리스트를 갱신
                    commentList.add(e);
                }
                commentListAdapter.notifyDataSetChanged();
            }
        }
    }
}