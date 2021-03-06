package com.Project.Closet.social.subfragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.Closet.Global;
import com.Project.Closet.HTTP.VO.DetailFeedVO;
import com.Project.Closet.HTTP.VO.FeedVO;
import com.Project.Closet.R;
import com.Project.Closet.social.detailFeed.activity_thisFeed;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


//어댑터 : 리사이클러뷰의 아이템 뷰를 생성하는 역할을 함
//뷰 홀더 : 아이템 뷰를 저장하는 객체
//아이템 뷰 : 각각의 카드뷰 한 개
public class UserspaceFeedRecyclerAdapter extends RecyclerView.Adapter<UserspaceFeedRecyclerAdapter.ViewHolder> {

    Context context;
    List<ArrayList<DetailFeedVO>> feedListByBoardNo; // 피드 리스트

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
    public UserspaceFeedRecyclerAdapter(List<ArrayList<DetailFeedVO>> items) {
        this.feedListByBoardNo =items;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public UserspaceFeedRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_cardview_grid, parent, false) ;
        UserspaceFeedRecyclerAdapter.ViewHolder vh = new UserspaceFeedRecyclerAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(UserspaceFeedRecyclerAdapter.ViewHolder holder, int position) {
        final ArrayList<DetailFeedVO> feedDataList = feedListByBoardNo.get(position);
        final DetailFeedVO feedInfo = feedDataList.get(0);
        Glide.with(holder.itemView.getContext()).load(Global.baseURL+feedInfo.getBoardImagePath()).into(holder.iv_image);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return this.feedListByBoardNo.size();
    }

    //뷰 홀더 : 아이템 뷰를 저장하는 객체
    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView iv_image;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            iv_image = itemView.findViewById(R.id.iv_codi_image);

            //아이템 클릭 이벤트 처리
            View.OnClickListener onClickListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // TODO : use pos.
                        Intent intent;
                        Context context = v.getContext();

                        final ArrayList<DetailFeedVO> selectedFeedDataList;
                        final DetailFeedVO feedInfo;

                        switch (v.getId()) {
                            default :

                                selectedFeedDataList = feedListByBoardNo.get(pos);

                                intent = new Intent(context, activity_thisFeed.class);
                                assert selectedFeedDataList != null;
                                intent.putParcelableArrayListExtra("selectedFeedList", selectedFeedDataList);

                                context.startActivity(intent);


                                //피드 상세 조회 (인스타 형식)
                                /*intent = new Intent(context, activity_space_detail.class);
                                assert feed != null;
                                intent.putExtra("feedList", feedList);
                                intent.putExtra("position", pos);
                                context.startActivity(intent);
                                 */
                        }

                        if(mListener!=null) {
                            //mListener.onItemClick(v,pos);
                        }
                    }
                }
            };

            CardView feed_card = itemView.findViewById(R.id.feed_card);
            feed_card.setOnClickListener(onClickListener);

        }
    }
}