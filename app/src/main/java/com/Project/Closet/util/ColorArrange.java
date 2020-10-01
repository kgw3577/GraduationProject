package com.Project.Closet.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ColorArrange  implements Comparable<ColorArrange>, Parcelable {
    int main_color;
    int sub_color;
    Set<Integer> other_colors;
    public int arrange_score;
    public int balance_score=50;
    public int total_score;

    public ColorArrange(){
    }

    public ColorArrange(int main_color, int sub_color){
        this.main_color = main_color;
        this.sub_color = sub_color;
        other_colors = findOtherColors(main_color, sub_color);

        arrange_score = Utils.score_rule[main_color][sub_color];
    }

    protected ColorArrange(Parcel in) {
        main_color = in.readInt();
        sub_color = in.readInt();
        arrange_score = in.readInt();
        balance_score = in.readInt();
        total_score = in.readInt();
    }

    public static final Creator<ColorArrange> CREATOR = new Creator<ColorArrange>() {
        @Override
        public ColorArrange createFromParcel(Parcel in) {
            return new ColorArrange(in);
        }

        @Override
        public ColorArrange[] newArray(int size) {
            return new ColorArrange[size];
        }
    };

    public int getMain_color() {
        return main_color;
    }

    public int getSub_color() {
        return sub_color;
    }

    public Set<Integer> getOther_colors() {
        return other_colors;
    }

    public void setBalance_score(int balance_score) {
        this.balance_score = balance_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

    public Set<Integer> findOtherColors(int main_color, int sub_color){
        Set<Integer> other_colors = new HashSet<>();

        //무채색 계열 추가
        //other_colors.addAll(Utils.Color.achromatic_colors);
        //메인 컬러와 같은 계열의 색 추가
        other_colors.addAll(findAffiliation(main_color));
        //서브 컬러와 같은 계열의 색 추가
        other_colors.addAll(findAffiliation(sub_color));

        return other_colors;
    }

    public List<Integer> findAffiliation(int color){
        if(Utils.ColorNum.white_colors.contains(color)) {
            return Utils.ColorNum.white_colors;
        }else if(Utils.ColorNum.beige_colors.contains(color)) {
            return Utils.ColorNum.beige_colors;
        }else if(Utils.ColorNum.soft_colors.contains(color)) {
            return Utils.ColorNum.soft_colors;
        }else if(Utils.ColorNum.pink_color.contains(color)){
            return Utils.ColorNum.pink_color;
        }else if(Utils.ColorNum.purple_color.contains(color)){
            return Utils.ColorNum.purple_color;
        }else if(Utils.ColorNum.red_colors.contains(color)){
            return Utils.ColorNum.red_colors;
        }else if(Utils.ColorNum.sky_blue_colors.contains(color)){
            return Utils.ColorNum.sky_blue_colors;
        }else if(Utils.ColorNum.blue_colors.contains(color)){
            return Utils.ColorNum.blue_colors;
        }else if(Utils.ColorNum.navy_colors.contains(color)){
            return Utils.ColorNum.navy_colors;
        }else if(Utils.ColorNum.green_colors.contains(color)){
            return Utils.ColorNum.green_colors;
        }else if(Utils.ColorNum.yellow_colors.contains(color)){
            return Utils.ColorNum.yellow_colors;
        }else if(Utils.ColorNum.gray_color.contains(color)){
            return Utils.ColorNum.gray_color;
        }else if(Utils.ColorNum.black_color.contains(color)){
            return Utils.ColorNum.black_color;
        }
        else return Collections.emptyList();
    }

    @Override
    public int compareTo(ColorArrange colorArrange) { //내림차순 비교
        if (this.total_score < colorArrange.total_score) {
            return 1;
        } else if (this.total_score > colorArrange.total_score) {
            return -1;
        }
        return 0;
    }

    public void describe(){
        Log.d("colorArrange 클래스",
                "main_color: "+ Utils.getKey(Utils.colorNumMap,main_color)
                        + " sub_color: "+ Utils.getKey(Utils.colorNumMap,sub_color)
                        + " 배색 점수 : "+arrange_score
                        + " 조화 점수 : "+balance_score
                        + " 종합 점수 : "+total_score);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(main_color);
        dest.writeInt(sub_color);
        dest.writeInt(arrange_score);
        dest.writeInt(balance_score);
        dest.writeInt(total_score);
    }
}
