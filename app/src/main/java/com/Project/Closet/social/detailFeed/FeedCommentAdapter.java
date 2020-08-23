package com.Project.Closet.social.detailFeed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.VO.CommentFeedVO;
import com.Project.Closet.HTTP.VO.CommentVO;
import com.Project.Closet.HTTP.VO.DetailFeedVO;
import com.Project.Closet.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FeedCommentAdapter extends RecyclerView.Adapter<FeedCommentAdapter.CustomViewHolder> {

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
        protected TextView tv_commenterID;
        protected TextView post_comment_comment;
        protected ImageView post_comment_profile;


        public CustomViewHolder(View view) {
            super(view);
            this.post_comment_nickname = (TextView) view.findViewById(R.id.tv_commenterName);
            this.tv_commenterID = (TextView) view.findViewById(R.id.tv_commenterID);
            this.post_comment_comment = (TextView) view.findViewById(R.id.tv_comment);
            this.post_comment_profile = (ImageView) view.findViewById(R.id.post_comment_profile);

            final LinearLayout ll_delete = view.findViewById(R.id.ll_delete);
            final LinearLayout ll_comment = view.findViewById(R.id.ll_comment);
            TextView tv_delete = view.findViewById(R.id.tv_delete);

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // TODO : use pos.
                        Intent intent;
                        Context context = v.getContext();

                        switch (v.getId()) {
                            case R.id.ll_comment :
                                if(ll_delete.getVisibility()==View.GONE) {
                                    ll_delete.setVisibility(View.VISIBLE);
                                }else if(ll_delete.getVisibility()==View.VISIBLE) {
                                    ll_delete.setVisibility(View.GONE);
                                }
                                break;
                            case R.id.tv_delete:
                                activity_thisFeed activity =(activity_thisFeed)v.getContext();
                                try {
                                    activity.deleteComment(pos);
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            default :
                                break;
                        }

                        if(mListener!=null) {
                            //mListener.onItemClick(v,pos);
                        }
                    }
                }
            };


            ll_comment.setOnClickListener(onClickListener);
            ll_delete.setOnClickListener(onClickListener);
            tv_delete.setOnClickListener(onClickListener);

        }
    }


    public FeedCommentAdapter(ArrayList<CommentFeedVO> list) {
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


        Glide.with(viewholder.itemView.getContext()).load(Global.baseURL+commentList.get(position).getPfImagePath()).into(viewholder.post_comment_profile);

        viewholder.post_comment_nickname.setText(commentList.get(position).getCommenterName());
        viewholder.tv_commenterID.setText("@"+commentList.get(position).getCommenterID());
        viewholder.post_comment_comment.setText(commentList.get(position).getContents());
    }

    @Override
    public int getItemCount() {
        return (null != commentList ? commentList.size() : 0);
    }






}