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

public class ClothesListAdapter_small extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<String> items;
    int item_layout;
    public ClothesListAdapter_small(Context context, List<String> items, int item_layout) {
        this.mContext=context;
        this.items=items;
        this.item_layout=item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cloth_small,null);
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
        }
    }
}