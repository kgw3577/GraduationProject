package com.Project.Closet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


//어댑터 : 리사이클러뷰의 아이템 뷰를 생성하는 역할을 함
//뷰 홀더 : 아이템 뷰를 저장하는 객체
//아이템 뷰 : 각각의 카드뷰 한 개
public class ClothesListAdapter_small extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<String> items; //이미지 url 리스트
    int item_layout; //리사이클러뷰 레이아웃. fragment_recyclerview임.

    public interface OnItemClickListener {
        void onItemClick(View v, int position, ImageView iv_Clothes);
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }


    //생성자에서 데이터 리스트 객체를 전달받음.
    public ClothesListAdapter_small(Context context, List<String> items, int item_layout) {
        this.mContext=context;
        this.items=items;
        this.item_layout=item_layout;
    }

    //뷰홀더 객체 생성하며 리턴 (아이템뷰를 위한)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_small,null);
        return new ViewHolder(v);
    }

    //position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        final String item=items.get(position);
        Glide.with(myViewHolder.itemView.getContext()).load(item).into(myViewHolder.iv_Clothes);
    }

    //아이템 개수 반환
    @Override
    public int getItemCount() {
        Log.e("1",""+this.items.size());
        return this.items.size();
    }

    //뷰 홀더 : 아이템 뷰를 저장하는 객체
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_Clothes; //각각의 카드뷰 안에 있는 옷 이미지를 띄우는 이미지뷰

        public ViewHolder(View itemView) {
            super(itemView);
            iv_Clothes=(ImageView) itemView.findViewById(R.id.cardview_cloth_iv);

            // 아이템 클릭 이벤트 처리.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mListener != null) {
                            mListener.onItemClick(v, pos, iv_Clothes) ;
                        }
                        // 데이터 리스트로부터 아이템 데이터 참조.
                        //RecyclerItem item = mData.get(pos) ;
                    }
                }
            });
        }
    }
}