package com.Project.Closet.share;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.PostCommentAdapter;
import com.Project.Closet.R;
import com.Project.Closet.post_comment;

import java.util.ArrayList;

public class activity_post extends AppCompatActivity {
    private ArrayList<post_comment> mArrayList;
    private PostCommentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.post_comment_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mArrayList = new ArrayList<>();

        mAdapter = new PostCommentAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        for(int i = 0; i<10;i++) {
            post_comment postComment = new post_comment("NickTEST", "액티비티에서 추가한 댓글", String.valueOf(i) + "");
            mArrayList.add(postComment);
        }

        mAdapter.notifyDataSetChanged();
    }
}