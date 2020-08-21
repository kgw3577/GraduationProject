
package com.my.closet.social.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.my.closet.board.vo.BoardVO;
import com.my.closet.social.vo.CommentFeedVO;
import com.my.closet.social.vo.DetailFeedVO;
import com.my.closet.social.vo.DetailFeedVO_Extended;
import com.my.closet.social.vo.ExpandedFeedVO;
import com.my.closet.social.vo.FeedVO;
import com.my.closet.social.vo.FollowVO;
import com.my.closet.social.vo.HeartVO;
import com.my.closet.social.vo.UserspaceVO;

public interface SocialDAO {

	public List<FeedVO> showAllFeed(FollowVO followFilter) throws DataAccessException; //피드 가져오기
	public List<FeedVO> showFollowFeed(FollowVO followFilter) throws DataAccessException; //팔로우 피드 가져오기
	
	
	public List<DetailFeedVO> searchFeed(DetailFeedVO_Extended feedFilter) throws DataAccessException; //피드 조건 검색
	
	
	public List<FeedVO> showHeartFeed(FollowVO followFilter) throws DataAccessException; // 해당 사용자가 좋아요한 피드
	public List<DetailFeedVO> showDetailFeed(HeartVO feedFilter) throws DataAccessException;
	public List<CommentFeedVO> showCommentInBoard(BoardVO boardFilter) throws DataAccessException; //해당 게시글 댓글 가져오기

	public UserspaceVO showUserSpace(FollowVO followFilter) throws DataAccessException;
	
	public List<DetailFeedVO> recommendFull(HashMap<String, Object> recoFilter) throws DataAccessException;
}
