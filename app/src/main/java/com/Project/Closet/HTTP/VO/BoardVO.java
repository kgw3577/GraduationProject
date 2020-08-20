package com.Project.Closet.HTTP.VO;

import android.os.Parcel;
import android.os.Parcelable;

public class BoardVO implements Parcelable {

    private int boardNo; // PRIMARY KEY. AUTO INCREMENT
    private String userID; //FOREIGN KEY(USER). not null
    private String fileName;
    private String filePath;
    private String contents;
    private String regDate; // DEFAULT CURRENT_TIMESTAMP



    //생성자
    public BoardVO() {
        System.out.println("CloBoardVO 생성자 호출");
    }


    protected BoardVO(Parcel in) {
        boardNo = in.readInt();
        userID = in.readString();
        fileName = in.readString();
        filePath = in.readString();
        contents = in.readString();
        regDate = in.readString();
    }

    public static final Creator<BoardVO> CREATOR = new Creator<BoardVO>() {
        @Override
        public BoardVO createFromParcel(Parcel in) {
            return new BoardVO(in);
        }

        @Override
        public BoardVO[] newArray(int size) {
            return new BoardVO[size];
        }
    };

    //게터&세터
    public int getBoardNo() {
        return boardNo;
    }


    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }


    public String getUserID() {
        return userID;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }


    public String getFileName() {
        return fileName;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getFilePath() {
        return filePath;
    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(boardNo);
        dest.writeString(userID);
        dest.writeString(fileName);
        dest.writeString(filePath);
        dest.writeString(contents);
        dest.writeString(regDate);
    }
}

