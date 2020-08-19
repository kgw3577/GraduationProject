package com.Project.Closet.HTTP.VO;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailFeedVO implements Parcelable {

    private String writerID, writerName, pfImagePath, pfContents,
            if_following, if_hearting,
            boardNo, imagePath, contents, regDate,
            cloNo, cloImagePath, cloIdentifier, cloBrand;
    private int numHeart, numComment;



    public DetailFeedVO(){

    }


    protected DetailFeedVO(Parcel in) {
        writerID = in.readString();
        writerName = in.readString();
        pfImagePath = in.readString();
        pfContents = in.readString();
        if_following = in.readString();
        if_hearting = in.readString();
        boardNo = in.readString();
        imagePath = in.readString();
        contents = in.readString();
        regDate = in.readString();
        cloNo = in.readString();
        cloImagePath = in.readString();
        cloIdentifier = in.readString();
        cloBrand = in.readString();
        numHeart = in.readInt();
        numComment = in.readInt();
    }

    public static final Creator<DetailFeedVO> CREATOR = new Creator<DetailFeedVO>() {
        @Override
        public DetailFeedVO createFromParcel(Parcel in) {
            return new DetailFeedVO(in);
        }

        @Override
        public DetailFeedVO[] newArray(int size) {
            return new DetailFeedVO[size];
        }
    };

    public String getWriterID() {
        return writerID;
    }



    public void setWriterID(String writerID) {
        this.writerID = writerID;
    }



    public String getWriterName() {
        return writerName;
    }



    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }



    public String getPfImagePath() {
        return pfImagePath;
    }



    public void setPfImagePath(String pfImagePath) {
        this.pfImagePath = pfImagePath;
    }



    public String getPfContents() {
        return pfContents;
    }



    public void setPfContents(String pfContents) {
        this.pfContents = pfContents;
    }



    public String getIf_following() {
        return if_following;
    }



    public void setIf_following(String if_following) {
        this.if_following = if_following;
    }



    public String getIf_hearting() {
        return if_hearting;
    }



    public void setIf_hearting(String if_hearting) {
        this.if_hearting = if_hearting;
    }



    public String getBoardNo() {
        return boardNo;
    }



    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }



    public String getImagePath() {
        return imagePath;
    }



    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }



    public String getContents() {
        return contents;
    }



    public void setContents(String contents) {
        this.contents = contents;
    }



    public String getRegDate() {
        return regDate;
    }



    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }



    public String getCloNo() {
        return cloNo;
    }



    public void setCloNo(String cloNo) {
        this.cloNo = cloNo;
    }



    public String getCloImagePath() {
        return cloImagePath;
    }



    public void setCloImagePath(String cloImagePath) {
        this.cloImagePath = cloImagePath;
    }



    public String getCloIdentifier() {
        return cloIdentifier;
    }



    public void setCloIdentifier(String cloIdentifier) {
        this.cloIdentifier = cloIdentifier;
    }



    public String getCloBrand() {
        return cloBrand;
    }



    public void setCloBrand(String cloBrand) {
        this.cloBrand = cloBrand;
    }



    public int getNumHeart() {
        return numHeart;
    }



    public void setNumHeart(int numHeart) {
        this.numHeart = numHeart;
    }



    public int getNumComment() {
        return numComment;
    }



    public void setNumComment(int numComment) {
        this.numComment = numComment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(writerID);
        dest.writeString(writerName);
        dest.writeString(pfImagePath);
        dest.writeString(pfContents);
        dest.writeString(if_following);
        dest.writeString(if_hearting);
        dest.writeString(boardNo);
        dest.writeString(imagePath);
        dest.writeString(contents);
        dest.writeString(regDate);
        dest.writeString(cloNo);
        dest.writeString(cloImagePath);
        dest.writeString(cloIdentifier);
        dest.writeString(cloBrand);
        dest.writeInt(numHeart);
        dest.writeInt(numComment);
    }
}
