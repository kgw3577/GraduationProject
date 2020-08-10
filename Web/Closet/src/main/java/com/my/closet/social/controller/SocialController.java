package com.my.closet.social.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.social.vo.CommentFeedVO;
import com.my.closet.social.vo.ExpandedFeedVO;
import com.my.closet.social.vo.FeedVO;
import com.my.closet.social.vo.FollowVO;
import com.my.closet.social.vo.HeartVO;
import com.my.closet.social.vo.UserspaceVO;


public interface SocialController {
	
	public ModelAndView feedlist(HttpServletRequest request, HttpServletResponse response) throws Exception; //모든 피드 리스트 조회. 웹 관리용.
	
	/*피드*/
	public ResponseEntity<List<FeedVO>> showAllFeed(String myID, String page, String pageSize) throws Exception; //최신 피드 가져오기
	public ResponseEntity<List<FeedVO>> showFollowFeed(String userID, String page, String pageSize) throws Exception; //팔로우 피드
	public ResponseEntity<List<FeedVO>> searchFeed(ExpandedFeedVO feedFilter, String myID, String page,String pageSize) throws Exception; //피드 조건 검색
	public ResponseEntity<List<FeedVO>> showHeartFeed(String userID, String myID, String page,String pageSize) throws Exception; // 해당 사용자가 좋아요한 피드
	
	public ResponseEntity<List<CommentFeedVO>> showCommentInBoard(String boardNo, String page, String pageSize) throws Exception;
		
	/*팔로우*/
	public ModelAndView followlist(HttpServletRequest request, HttpServletResponse response) throws Exception; // 모든 팔로우 리스트 조회. 웹 관리용.
	public ResponseEntity<List<FollowVO>> searchFollow(@RequestBody FollowVO followFilter) throws Exception; //팔로우 검색
	public ResponseEntity<String> revertFollow(@RequestBody FollowVO followInfo) throws Exception; //팔로우 상태 변경
	
	/*하트*/
	public ModelAndView heartlist(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<List<HeartVO>> searchHeart(@RequestBody HeartVO heartFilter) throws Exception;
	public ResponseEntity<String> revertHeart(@RequestBody HeartVO heartInfo) throws Exception;
					
	/*유저스페이스*/
	public ResponseEntity<UserspaceVO> showUserspace(String userID, String myID) throws Exception;
		
}
