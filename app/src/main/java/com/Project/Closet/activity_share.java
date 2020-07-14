package com.Project.Closet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.Project.Closet.home.activity_home;

import java.util.ArrayList;

public class activity_share extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_share);

        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.post_list) ;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        SharePostAdapter adapter = new SharePostAdapter(list) ;
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SharePostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(activity_share.this, activity_post.class);
                startActivity(intent);
            }
        });
    }
}