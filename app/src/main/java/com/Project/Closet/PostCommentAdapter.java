package com.Project.Closet;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostCommentAdapter extends RecyclerView.Adapter<PostCommentAdapter.CustomViewHolder> {

    private ArrayList<post_comment> mList;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView post_comment_nickname;
        protected TextView post_comment_comment;
        protected TextView post_comment_likes;


        public CustomViewHolder(View view) {
            super(view);
            this.post_comment_nickname = (TextView) view.findViewById(R.id.post_comment_nickname);
            this.post_comment_comment = (TextView) view.findViewById(R.id.post_comment_comment);
            this.post_comment_likes = (TextView) view.findViewById(R.id.post_comment_likes);
        }
    }


    public PostCommentAdapter(ArrayList<post_comment> list) {
        this.mList = list;
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_comment, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.post_comment_nickname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.post_comment_comment.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.post_comment_likes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        viewholder.post_comment_nickname.setGravity(Gravity.CENTER);
        viewholder.post_comment_comment.setGravity(Gravity.CENTER);
        viewholder.post_comment_likes.setGravity(Gravity.CENTER);



        viewholder.post_comment_nickname.setText(mList.get(position).getNickname());
        viewholder.post_comment_comment.setText(mList.get(position).getComment());
        viewholder.post_comment_likes.setText(mList.get(position).getLikes());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}