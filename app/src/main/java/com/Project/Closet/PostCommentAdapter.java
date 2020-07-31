package com.Project.Closet;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.HTTP.VO.CommentFeedVO;
import com.Project.Closet.util.CommentListAdapter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PostCommentAdapter extends RecyclerView.Adapter<PostCommentAdapter.CustomViewHolder> {

    ArrayList<CommentFeedVO> commentList;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView post_comment_nickname;
        protected TextView post_comment_comment;
        protected TextView post_comment_likes;
        protected ImageView post_comment_profile;


        public CustomViewHolder(View view) {
            super(view);
            this.post_comment_nickname = (TextView) view.findViewById(R.id.post_comment_nickname);
            this.post_comment_comment = (TextView) view.findViewById(R.id.post_comment_comment);
            this.post_comment_likes = (TextView) view.findViewById(R.id.post_comment_likes);
            this.post_comment_profile = (ImageView) view.findViewById(R.id.post_comment_profile);
        }
    }


    public PostCommentAdapter(ArrayList<CommentFeedVO> list) {
        this.commentList = list;
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

        //viewholder.post_comment_nickname.setGravity(Gravity.CENTER);
       // viewholder.post_comment_comment.setGravity(Gravity.CENTER);
       // viewholder.post_comment_likes.setGravity(Gravity.CENTER);


        Glide.with(viewholder.itemView.getContext()).load(Global.baseURL+commentList.get(position).getPfImagePath()).into(viewholder.post_comment_profile);

        viewholder.post_comment_nickname.setText(commentList.get(position).getCommenterName());
        viewholder.post_comment_comment.setText(commentList.get(position).getContents());
        viewholder.post_comment_likes.setText(commentList.get(position).getNumGood()+"");
    }

    @Override
    public int getItemCount() {
        return (null != commentList ? commentList.size() : 0);
    }

}