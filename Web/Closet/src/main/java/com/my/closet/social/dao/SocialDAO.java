
package com.my.closet.social.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.my.closet.board.vo.BoardVO;
import com.my.closet.social.vo.CommentFeedVO;
import com.my.closet.social.vo.FeedVO;
import com.my.closet.social.vo.FollowVO;
import com.my.closet.social.vo.UserspaceVO;

public interface SocialDAO {

	public List<FeedVO> showAllFeed(BoardVO boardFilter) throws DataAccessException; //피드 가져오기
	public List<FeedVO> showFollowFeed(BoardVO boardFilter) throws DataAccessException; //팔로우 피드 가져오기
	public FeedVO showOneFeed(BoardVO boardFilter) throws DataAccessException;
	public List<CommentFeedVO> showCommentInBoard(BoardVO boardFilter) throws DataAccessException; //해당 게시글 댓글 가져오기

	public UserspaceVO showUserSpace(FollowVO followFilter) throws DataAccessException;
}
