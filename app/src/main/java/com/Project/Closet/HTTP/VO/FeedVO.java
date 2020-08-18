package com.Project.Closet.HTTP.VO;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class FeedVO implements Parcelable {
    private String writerID, writerName, pfImagePath, if_hearting, imagePath, contents, regDate;
    private int numHeart, numComment, boardNo;


    //생성자
    public FeedVO() {
        System.out.println("FeedVO 생성자 호출");
    }


    protected FeedVO(Parcel in) {
        writerID = in.readString();
        writerName = in.readString();
        pfImagePath = in.readString();
        if_hearting = in.readString();
        imagePath = in.readString();
        contents = in.readString();
        regDate = in.readString();
        numHeart = in.readInt();
        numComment = in.readInt();
        boardNo = in.readInt();
    }

    public static final Creator<FeedVO> CREATOR = new Creator<FeedVO>() {
        @Override
        public FeedVO createFromParcel(Parcel in) {
            return new FeedVO(in);
        }

        @Override
        public FeedVO[] newArray(int size) {
            return new FeedVO[size];
        }
    };

    //게터&세터
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




    public String getIf_hearting() {
        return if_hearting;
    }


    public void setIf_hearting(String if_hearting) {
        this.if_hearting = if_hearting;
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


    public int getBoardNo() {
        return boardNo;
    }


    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
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
        dest.writeString(if_hearting);
        dest.writeString(imagePath);
        dest.writeString(contents);
        dest.writeString(regDate);
        dest.writeInt(numHeart);
        dest.writeInt(numComment);
        dest.writeInt(boardNo);
    }
}
