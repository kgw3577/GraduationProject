package com.my.closet.social.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.my.closet.board.vo.BoardVO;
import com.my.closet.social.vo.CommentFeedVO;
import com.my.closet.social.vo.ExpandedFeedVO;
import com.my.closet.social.vo.FeedVO;
import com.my.closet.social.vo.FollowVO;
import com.my.closet.social.vo.UserspaceVO;

@Repository("socialDAO")
public class SocialDAOImpl implements SocialDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<FeedVO> showAllFeed(FollowVO followFilter) throws DataAccessException {
		List<FeedVO> feedList = sqlSession.selectList("mapper.social.showFeed",followFilter);
		return feedList;
	}
	
	@Override
	public List<FeedVO> showFollowFeed(FollowVO followFilter) throws DataAccessException {
		List<FeedVO> feedList = sqlSession.selectList("mapper.social.showFollowFeed",followFilter);
		return feedList;
	}
	
	@Override
	public List<FeedVO> searchFeed(ExpandedFeedVO feedFilter) throws DataAccessException {
		List<FeedVO> feedList = sqlSession.selectList("mapper.social.searchFeed",feedFilter);
		return feedList;
	}
	
	@Override
	public List<FeedVO> showHeartFeed(FollowVO followFilter) throws DataAccessException {
		List<FeedVO> feedList = sqlSession.selectList("mapper.social.showHeartFeed",followFilter);
		return feedList;
	}
	
	
	
	@Override
	public List<CommentFeedVO> showCommentInBoard(BoardVO boardFilter) throws DataAccessException {
		List<CommentFeedVO> commentList = sqlSession.selectList("mapper.social.showCommentInBoard",boardFilter);
		return commentList;
	}
	
	
	@Override
	public UserspaceVO showUserSpace(FollowVO followFilter) throws DataAccessException {
		UserspaceVO userspaceInfo = sqlSession.selectOne("mapper.social.showUserSpace",followFilter);
		return userspaceInfo;
	}

}
