package com.Project.Closet.social.detailFeed;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import com.Project.Closet.HTTP.VO.DetailFeedVO;
import com.Project.Closet.R;
import com.Project.Closet.util.NumFormat;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class activity_thisFeed extends AppCompatActivity {


    String writerID, writerName, pfImagePath, pfContents,
            if_following, if_hearting,
            boardNo, imagePath, contents, regDate,
            cloNo, cloImagePath, cloIdentifier, cloBrand;
    int numHeart, numComment;

    ImageView iv_profileImage, iv_heart, iv_image;
    TextView tv_writerName, tv_pfContents, tv_numHeart, tv_numComment,
            tv_contents, tv_regDate;

    //int gridsize=2;
    String pageSize="10";

    int page=0;
    RecyclerView rv_clothes_list;
    RecyclerView rv_comment_list;
    ArrayList<CommentFeedVO> commentList = new ArrayList();

    //리사이클러뷰 어댑터
    ChildCloAdapter childCloAdapter;
    FeedCommentAdapter commentListAdapter;

    Call<List<CommentFeedVO>> commentListCall; // 게시글 VO 리스트를 응답으로 받는 http 요청

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thisfeed);

        ArrayList<DetailFeedVO> selectedFeedList = getIntent().getExtras().getParcelableArrayList("selectedFeedList");
        DetailFeedVO feed = selectedFeedList.get(0);

        writerID = feed.getWriterID();
        writerName = feed.getWriterName();
        pfImagePath = feed.getPfImagePath();
        pfContents = feed.getPfContents();
        if_following = feed.getIf_following();
        if_hearting = feed.getIf_hearting();
        boardNo = feed.getBoardNo();
        imagePath = feed.getImagePath();
        contents = feed.getContents();
        regDate = feed.getRegDate();
        numHeart = feed.getNumHeart();
        numComment = feed.getNumComment();



        tv_writerName = findViewById(R.id.tv_writerName);
        tv_pfContents = findViewById(R.id.tv_pfContents);
        tv_numHeart = findViewById(R.id.tv_numHeart);
        tv_numComment = findViewById(R.id.tv_numComment);
        tv_contents = findViewById(R.id.tv_contents);
        tv_regDate = findViewById(R.id.tv_regDate);

        iv_profileImage = findViewById(R.id.iv_profileImage);
        iv_image = findViewById(R.id.iv_image);
        iv_heart = findViewById(R.id.iv_heart);



        //작성 시간 포매팅
        long ts = Timestamp.valueOf(regDate).getTime();
        regDate = NumFormat.formatTimeString(ts);
        //수 포매팅
        String numHeartstr = NumFormat.formatNumString(numHeart);
        String numCommentstr  = NumFormat.formatNumString(numComment);


        Glide.with(this).load(Global.baseURL+imagePath).into(iv_image);
        Glide.with(this).load(Global.baseURL+pfImagePath).into(iv_profileImage);
        if(if_hearting.contains("hearting")){
            iv_heart.setImageResource(R.drawable.heart_color);
        }else
            iv_heart.setImageResource(R.drawable.heart_empty);

        tv_writerName.setText(writerName);
        if(pfContents!= null && !pfContents.isEmpty())
            tv_pfContents.setText(pfContents);
        else
            tv_pfContents.setVisibility(View.GONE);
        tv_numHeart.setText(numHeartstr);
        tv_numComment.setText(numCommentstr);
        tv_contents.setText(contents);
        tv_regDate.setText(regDate);

        //현재 페이지수와 함께 웹서버에 댓글 데이터 요청
        new networkTask().execute(Integer.toString(page),boardNo);


        //옷 리사이클러뷰 어댑터 초기화
        childCloAdapter = new ChildCloAdapter(selectedFeedList);
        childCloAdapter.setOnItemClickListener(new ChildCloAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

            }
        });
        //댓글 리사이클러뷰 어댑터 초기화
        commentListAdapter = new FeedCommentAdapter(commentList);
        commentListAdapter.setOnItemClickListener(new FeedCommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

            }
        });

        //옷 리사이클러뷰
        rv_clothes_list = (RecyclerView) findViewById(R.id.rv_clothes_list);
        LinearLayoutManager nLinearLayoutManager = new LinearLayoutManager(this);
        rv_clothes_list.setLayoutManager(nLinearLayoutManager);
        rv_clothes_list.setAdapter(childCloAdapter);

        //댓글 리사이클러뷰
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
                    new networkTask().execute(Integer.toString(++page),boardNo);
                    Log.e("test","페이지 수 증가");
                }
                else {
                }
            }
        });

        DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(rv_comment_list.getContext(),
                nLinearLayoutManager.getOrientation());
        rv_clothes_list.addItemDecoration(dividerItemDecoration1);

        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(rv_comment_list.getContext(),
                mLinearLayoutManager.getOrientation());
        rv_comment_list.addItemDecoration(dividerItemDecoration2);

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