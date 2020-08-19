package com.Project.Closet.social.detailFeed;

import android.media.Image;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.VO.CommentFeedVO;
import com.Project.Closet.HTTP.VO.DetailFeedVO;
import com.Project.Closet.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ChildCloAdapter extends RecyclerView.Adapter<ChildCloAdapter.CustomViewHolder> {

    ArrayList<DetailFeedVO> childCloList;

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
        protected ImageView iv_clothes;
        protected TextView tv_clothes_identifier;

        public CustomViewHolder(View view) {
            super(view);
            this.iv_clothes = view.findViewById(R.id.iv_clothes);
            this.tv_clothes_identifier = view.findViewById(R.id.tv_clothes_identifier);
        }
    }

    public ChildCloAdapter(ArrayList<DetailFeedVO> list) {
        this.childCloList = list;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_child_clo, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        DetailFeedVO childClo = childCloList.get(position);

        //옷 이미지 설정
        Glide.with(viewholder.itemView.getContext()).load(Global.baseURL+ childClo.getCloImagePath()).into(viewholder.iv_clothes);
        //옷 식별자 설정
        String final_identifier="";
        String identifier=childClo.getCloIdentifier().replace("_", " ");
        if(childClo.getCloBrand()!=null && !childClo.getCloBrand().isEmpty())
            final_identifier = childClo.getCloBrand()+" "+identifier;
        else
            final_identifier = identifier;

        viewholder.tv_clothes_identifier.setText(final_identifier);
    }

    @Override
    public int getItemCount() {
        return (null != childCloList ? childCloList.size() : 0);
    }

}