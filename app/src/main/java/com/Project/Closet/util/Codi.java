package com.Project.Closet.util;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.Project.Closet.HTTP.VO.ClothesVO;

import java.util.Objects;

public class Codi implements Comparable<Codi>, Parcelable {
    ClothesVO top;
    ClothesVO bottom;
    ClothesVO suit;
    ClothesVO outer;
    ClothesVO shoes;
    ClothesVO bag;
    ClothesVO accessory;

    ColorArrange colorArrange;
    int temperature=-1;


    protected Codi(Parcel in) {
        top = in.readParcelable(ClothesVO.class.getClassLoader());
        bottom = in.readParcelable(ClothesVO.class.getClassLoader());
        suit = in.readParcelable(ClothesVO.class.getClassLoader());
        outer = in.readParcelable(ClothesVO.class.getClassLoader());
        shoes = in.readParcelable(ClothesVO.class.getClassLoader());
        bag = in.readParcelable(ClothesVO.class.getClassLoader());
        accessory = in.readParcelable(ClothesVO.class.getClassLoader());
        colorArrange = in.readParcelable(ColorArrange.class.getClassLoader());
        temperature = in.readInt();
    }

    public Codi(){
        top = new ClothesVO();
        bottom = new ClothesVO();
        suit = new ClothesVO();
        outer = new ClothesVO();
        shoes = new ClothesVO();
        bag = new ClothesVO();
        accessory = new ClothesVO();
        colorArrange = new ColorArrange();
    }

    public Codi(ColorArrange colorArrange){
        top = new ClothesVO();
        bottom = new ClothesVO();
        suit = new ClothesVO();
        outer = new ClothesVO();
        shoes = new ClothesVO();
        bag = new ClothesVO();
        accessory = new ClothesVO();
        this.colorArrange = colorArrange;
    }




    public static final Creator<Codi> CREATOR = new Creator<Codi>() {
        @Override
        public Codi createFromParcel(Parcel in) {
            return new Codi(in);
        }

        @Override
        public Codi[] newArray(int size) {
            return new Codi[size];
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Codi)) return false;
        Codi codi = (Codi) o;
        return Objects.equals(top.getCloNo(), codi.top.getCloNo()) &&
                Objects.equals(bottom.getCloNo(), codi.bottom.getCloNo()) &&
                Objects.equals(suit.getCloNo(), codi.suit.getCloNo()) &&
                Objects.equals(outer.getCloNo(), codi.outer.getCloNo()) &&
                Objects.equals(shoes.getCloNo(), codi.shoes.getCloNo()) &&
                Objects.equals(bag.getCloNo(), codi.bag.getCloNo()) &&
                Objects.equals(accessory.getCloNo(), codi.accessory.getCloNo());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(top.getCloNo(), bottom.getCloNo(), suit.getCloNo(), outer.getCloNo(), shoes.getCloNo(),
                bag.getCloNo(), accessory.getCloNo());
    }

    public ClothesVO getTop() {
        return top;
    }

    public void setTop(ClothesVO top) {
        this.top = top;
    }

    public ClothesVO getBottom() {
        return bottom;
    }

    public void setBottom(ClothesVO bottom) {
        this.bottom = bottom;
    }

    public ClothesVO getSuit() {
        return suit;
    }

    public void setSuit(ClothesVO suit) {
        this.suit = suit;
    }

    public ClothesVO getOuter() {
        return outer;
    }

    public void setOuter(ClothesVO outer) {
        this.outer = outer;
    }

    public ClothesVO getShoes() {
        return shoes;
    }

    public void setShoes(ClothesVO shoes) {
        this.shoes = shoes;
    }

    public ClothesVO getBag() {
        return bag;
    }

    public void setBag(ClothesVO bag) {
        this.bag = bag;
    }

    public ClothesVO getAccessory() {
        return accessory;
    }

    public void setAccessory(ClothesVO accessory) {
        this.accessory = accessory;
    }

    public ColorArrange getColorArrange() {
        return colorArrange;
    }

    public void setColorArrange(ColorArrange colorArrange) {
        this.colorArrange = colorArrange;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(top, flags);
        dest.writeParcelable(bottom, flags);
        dest.writeParcelable(suit, flags);
        dest.writeParcelable(outer, flags);
        dest.writeParcelable(shoes, flags);
        dest.writeParcelable(bag, flags);
        dest.writeParcelable(accessory, flags);
        dest.writeParcelable(colorArrange, flags);
        dest.writeInt(temperature);
    }

    public void setPart(int index, ClothesVO clothes) {
        switch (index) {
            case Utils.Kind.TOP:
                setTop(clothes);
                break;
            case Utils.Kind.BOTTOM:
                setBottom(clothes);
                break;
            case Utils.Kind.SUIT:
                setSuit(clothes);
                break;
            case Utils.Kind.OUTER:
                setOuter(clothes);
                break;
            case Utils.Kind.SHOES:
                setShoes(clothes);
                break;
            case Utils.Kind.BAG:
                setBag(clothes);
                break;
            case Utils.Kind.ACCESSORY:
                setAccessory(clothes);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int compareTo(Codi codi) {
        if (this.colorArrange.total_score < codi.getColorArrange().total_score) {
            return 1;
        } else if (this.colorArrange.total_score > codi.getColorArrange().total_score) {
            return -1;
        }
        /*
        //같은 점수일 경우 옷 번호 순서대로 나열
        else if(this.colorArrange.total_score == codi.getColorArrange().total_score){
            ClothesVO thisTop = top;
            ClothesVO thisSuit = suit;
            ClothesVO codiTop = codi.getTop();
            ClothesVO codiSuit = codi.getSuit();
            if(thisTop.getCategory()!=null && codiTop.getCategory()!=null){
                return Integer.compare(thisTop.getCloNo(),codiTop.getCloNo());
            }else if(thisTop.getCategory()!=null && codiSuit.getCategory()!=null){
                return Integer.compare(thisTop.getCloNo(),codiSuit.getCloNo());
            }else if(thisSuit.getCategory()!=null && codiTop.getCategory()!=null){
                return Integer.compare(thisSuit.getCloNo(),codiTop.getCloNo());
            }else if(thisSuit.getCategory()!=null && codiSuit.getCategory()!=null){
                return Integer.compare(thisSuit.getCloNo(),codiSuit.getCloNo());
            }
        }*/
        return 0;
    }
}
