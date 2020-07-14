package com.Project.Closet.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.VO.BoardVO;
import com.Project.Closet.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


//어댑터 : 리사이클러뷰의 아이템 뷰를 생성하는 역할을 함
//뷰 홀더 : 아이템 뷰를 저장하는 객체
//아이템 뷰 : 각각의 카드뷰 한 개
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    ArrayList<BoardVO> BoardList; //이미지 url 리스트

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    //생성자에서 데이터 리스트 객체를 전달받음.
    public PostAdapter(ArrayList<BoardVO> items) {
        this.BoardList=items;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_cardview_share, parent, false) ;
        PostAdapter.ViewHolder vh = new PostAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder, int position) {
        final BoardVO boardVO=BoardList.get(position);

        holder.tv_subject.setText(boardVO.getSubject());
        holder.tv_nickname.setText(boardVO.getUserID());

        Glide.with(holder.itemView.getContext()).load(Global.baseURL+boardVO.getFilePath()).into(holder.iv_image);
        Glide.with(holder.itemView.getContext()).load(Global.baseURL+boardVO.getFilePath()).into(holder.iv_profileImage);
        //프로필 이미지, 닉네임 추후 수정
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        Log.e("BoardListAdapter","아이템 개수 반환"+this.BoardList.size());
        return this.BoardList.size();
    }

    //뷰 홀더 : 아이템 뷰를 저장하는 객체
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_subject;
        TextView tv_nickname;
        ImageView iv_image;
        ImageView iv_profileImage;

        ViewHolder(View itemView) {
            super(itemView) ;
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // TODO : use pos.
                        if(mListener!=null) {
                            mListener.onItemClick(v,pos);
                        }
                    }
                }
            });
            // 뷰 객체에 대한 참조. (hold strong reference)
            tv_subject = itemView.findViewById(R.id.sharepost_subject);
            tv_nickname = itemView.findViewById(R.id.sharepost_nickname);
            iv_image = itemView.findViewById(R.id.sharepost_image);
            iv_profileImage = itemView.findViewById(R.id.sharepost_profile);
        }
    }
}