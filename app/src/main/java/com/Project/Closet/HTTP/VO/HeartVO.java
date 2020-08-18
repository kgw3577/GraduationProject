package com.Project.Closet.HTTP.VO;

public class HeartVO {
    private String boardNo, hearterID, regDate;

    //생성자
    public HeartVO() {
        System.out.println("HeartVO 생성자 호출");
    }
    public HeartVO(String boardNo, String hearterID) {
        this.boardNo=boardNo;
        this.hearterID=hearterID;
        System.out.println("HeartVO 생성자 호출");
    }


    public String getBoardNo() {
        return boardNo;
    }



    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }



    public String getHearterID() {
        return hearterID;
    }



    public void setHearterID(String hearterID) {
        this.hearterID = hearterID;
    }



    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

}
