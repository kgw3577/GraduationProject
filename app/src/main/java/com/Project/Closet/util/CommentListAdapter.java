package com.Project.Closet.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.VO.CommentFeedVO;
import com.Project.Closet.HTTP.VO.FeedVO;
import com.Project.Closet.R;
import com.bumptech.glide.Glide;

import java.sql.Timestamp;
import java.util.ArrayList;


//어댑터 : 리사이클러뷰의 아이템 뷰를 생성하는 역할을 함
//뷰 홀더 : 아이템 뷰를 저장하는 객체
//아이템 뷰 : 각각의 카드뷰 한 개
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    ArrayList<CommentFeedVO> commentList; // 피드 리스트

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
    public CommentListAdapter(ArrayList<CommentFeedVO> items) {
        this.commentList =items;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public CommentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_cardview_share, parent, false) ;
        CommentListAdapter.ViewHolder vh = new CommentListAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(CommentListAdapter.ViewHolder holder, int position) {
        final CommentFeedVO feedMap = commentList.get(position);

        //작성 시간 포매팅
        long ts = Timestamp.valueOf(feedMap.getRegDate()).getTime();
        String regDate = NumFormat.formatTimeString(ts);
        //수 포매팅
        String numGood = NumFormat.formatNumString(feedMap.getNumGood());


        holder.tv_writerName.setText(feedMap.getCommenterName());
        //holder.tv_numGood.setText(numGood); //형식 : 223.7만
        holder.tv_contents.setText(feedMap.getContents());
        holder.tv_regDate.setText(regDate); //형식 : 6시간 전

        Glide.with(holder.itemView.getContext()).load(Global.baseURL+feedMap.getPfImagePath()).into(holder.iv_profileImage);
        // 해당 유저의 하트 여부도 받아와야 함. iv_heart -> 하트 색칠 여부

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return this.commentList.size();
    }

    //뷰 홀더 : 아이템 뷰를 저장하는 객체
    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_writerName, tv_numHeart, tv_numComment, tv_contents, tv_regDate;
        ImageView iv_profileImage, iv_image, iv_heart, iv_comment, iv_addToCloset;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            tv_writerName = itemView.findViewById(R.id.tv_writerName);
            tv_numHeart = itemView.findViewById(R.id.tv_numHeart);
            tv_numComment = itemView.findViewById(R.id.tv_numComment);
            tv_contents = itemView.findViewById(R.id.tv_contents);
            tv_regDate = itemView.findViewById(R.id.tv_regDate);

            iv_profileImage = itemView.findViewById(R.id.iv_profileImage);
            iv_image = itemView.findViewById(R.id.iv_image);
            iv_heart = itemView.findViewById(R.id.iv_heart);
            iv_comment = itemView.findViewById(R.id.iv_comment);
            iv_addToCloset = itemView.findViewById(R.id.iv_addToCloset);

            //아이템 클릭 이벤트 처리
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
        }
    }
}