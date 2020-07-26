package com.Project.Closet.HTTP.VO;

public class BoardVO {

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

}

