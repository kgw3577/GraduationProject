package com.Project.Closet.util;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.Project.Closet.HTTP.VO.ClothesVO;

import java.util.List;

public class ListDiffCallback extends DiffUtil.Callback {
    private final List<ClothesVO> oldList;
    private final List<ClothesVO> newList;
    public ListDiffCallback(List<ClothesVO> oldList, List<ClothesVO> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }
    @Override
    public int getOldListSize() {
        return oldList.size();
    }
    @Override
    public int getNewListSize() {
        return newList.size();
    }
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getNo() == newList.get(newItemPosition).getNo();
    }
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        ClothesVO oldItem = oldList.get(oldItemPosition);
        ClothesVO newItem = newList.get(newItemPosition);
        return oldItem.getFileName().equals(newItem.getFileName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

}