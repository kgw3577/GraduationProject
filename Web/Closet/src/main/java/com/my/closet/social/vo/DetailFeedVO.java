package com.my.closet.social.vo;

import org.springframework.stereotype.Component;

@Component("detailFeedVO")
public class DetailFeedVO {

	private String userID, userName, userPfImagePath, userPfContents,
	userNumBoard, userNumFollower, userNumFollowing,
	user_if_following, 
	user_following_friendsID, user_followig_friendsName, user_followig_friendsImgPath,
	board_if_hearting, 
	boardNo, boardImagePath, boardContents, boardRegDate,
	cloNo, cloLocation, cloKind, cloCategory,
	cloDetailCategory, cloColor, cloIdentifier, 
	cloSeason, cloBrand, cloImagePath, cloUserID;
	private int board_numHeart, board_numComment, board_numChild;
	
	public int getBoard_numChild() {
		return board_numChild;
	}


	public void setBoard_numChild(int board_numChild) {
		this.board_numChild = board_numChild;
	}


	//임시
	private int pageStart =-1;
	private int pageSize =-1;
	
	
	

	public DetailFeedVO(){
		
	}


	public int getPageStart() {
		return pageStart;
	}


	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}



	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserPfImagePath() {
		return userPfImagePath;
	}


	public void setUserPfImagePath(String userPfImagePath) {
		this.userPfImagePath = userPfImagePath;
	}


	public String getUserPfContents() {
		return userPfContents;
	}


	public void setUserPfContents(String userPfContents) {
		this.userPfContents = userPfContents;
	}


	public String getUserNumBoard() {
		return userNumBoard;
	}


	public void setUserNumBoard(String userNumBoard) {
		this.userNumBoard = userNumBoard;
	}


	public String getUserNumFollower() {
		return userNumFollower;
	}


	public void setUserNumFollower(String userNumFollower) {
		this.userNumFollower = userNumFollower;
	}


	public String getUserNumFollowing() {
		return userNumFollowing;
	}


	public void setUserNumFollowing(String userNumFollowing) {
		this.userNumFollowing = userNumFollowing;
	}


	public String getUser_if_following() {
		return user_if_following;
	}


	public void setUser_if_following(String user_if_following) {
		this.user_if_following = user_if_following;
	}


	public String getUser_following_friendsID() {
		return user_following_friendsID;
	}


	public void setUser_following_friendsID(String user_following_friendsID) {
		this.user_following_friendsID = user_following_friendsID;
	}


	public String getUser_followig_friendsName() {
		return user_followig_friendsName;
	}


	public void setUser_followig_friendsName(String user_followig_friendsName) {
		this.user_followig_friendsName = user_followig_friendsName;
	}


	public String getUser_followig_friendsImgPath() {
		return user_followig_friendsImgPath;
	}


	public void setUser_followig_friendsImgPath(String user_followig_friendsImgPath) {
		this.user_followig_friendsImgPath = user_followig_friendsImgPath;
	}


	public String getBoard_if_hearting() {
		return board_if_hearting;
	}


	public void setBoard_if_hearting(String board_if_hearting) {
		this.board_if_hearting = board_if_hearting;
	}


	public String getBoardNo() {
		return boardNo;
	}


	public void setBoardNo(String boardNo) {
		this.boardNo = boardNo;
	}


	public String getBoardImagePath() {
		return boardImagePath;
	}


	public void setBoardImagePath(String boardImagePath) {
		this.boardImagePath = boardImagePath;
	}


	public String getBoardContents() {
		return boardContents;
	}


	public void setBoardContents(String boardContents) {
		this.boardContents = boardContents;
	}


	public String getBoardRegDate() {
		return boardRegDate;
	}


	public void setBoardRegDate(String boardRegDate) {
		this.boardRegDate = boardRegDate;
	}


	public String getCloNo() {
		return cloNo;
	}


	public void setCloNo(String cloNo) {
		this.cloNo = cloNo;
	}


	public String getCloLocation() {
		return cloLocation;
	}


	public void setCloLocation(String cloLocation) {
		this.cloLocation = cloLocation;
	}


	public String getCloKind() {
		return cloKind;
	}


	public void setCloKind(String cloKind) {
		this.cloKind = cloKind;
	}


	public String getCloCategory() {
		return cloCategory;
	}


	public void setCloCategory(String cloCategory) {
		this.cloCategory = cloCategory;
	}


	public String getCloDetailCategory() {
		return cloDetailCategory;
	}


	public void setCloDetailCategory(String cloDetailCategory) {
		this.cloDetailCategory = cloDetailCategory;
	}


	public String getCloColor() {
		return cloColor;
	}


	public void setCloColor(String cloColor) {
		this.cloColor = cloColor;
	}


	public String getCloIdentifier() {
		return cloIdentifier;
	}


	public void setCloIdentifier(String cloIdentifier) {
		this.cloIdentifier = cloIdentifier;
	}


	public String getCloSeason() {
		return cloSeason;
	}


	public void setCloSeason(String cloSeason) {
		this.cloSeason = cloSeason;
	}


	public String getCloBrand() {
		return cloBrand;
	}


	public void setCloBrand(String cloBrand) {
		this.cloBrand = cloBrand;
	}


	public String getCloImagePath() {
		return cloImagePath;
	}


	public void setCloImagePath(String cloImagePath) {
		this.cloImagePath = cloImagePath;
	}


	public String getCloUserID() {
		return cloUserID;
	}


	public void setCloUserID(String cloUserID) {
		this.cloUserID = cloUserID;
	}


	public int getBoard_numHeart() {
		return board_numHeart;
	}


	public void setBoard_numHeart(int board_numHeart) {
		this.board_numHeart = board_numHeart;
	}


	public int getBoard_numComment() {
		return board_numComment;
	}


	public void setBoard_numComment(int board_numComment) {
		this.board_numComment = board_numComment;
	}
	
	
}
