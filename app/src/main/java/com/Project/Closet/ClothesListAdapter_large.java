package com.Project.Closet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClothesListAdapter_large extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<String> items;
    int item_layout;
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
    private OnItemClickListener mListener = null ;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }
    public ClothesListAdapter_large(Context context, List<String> items, int item_layout) {
        this.mContext=context;
        this.items=items;
        this.item_layout=item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_large,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        final String item=items.get(position);
        Glide.with(myViewHolder.itemView.getContext()).load(item).into(myViewHolder.iv_Cloth);
    }

    @Override
    public int getItemCount() {
        Log.e("1",""+this.items.size());
        return this.items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_Cloth;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_Cloth=(ImageView) itemView.findViewById(R.id.cardview_cloth_iv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mListener != null) {
                            mListener.onItemClick(v, pos) ;
                        }
                    }
                }
            });
        }
    }
}