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
import com.my.closet.social.vo.FeedVO;

@Repository("feedDAO")
public class FeedDAOImpl implements FeedDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<FeedVO> showAllFeed(BoardVO boardFilter) throws DataAccessException {
		List<FeedVO> feedList = sqlSession.selectList("mapper.social.showFeed",boardFilter);
		return feedList;
	}
	
	@Override
	public List<FeedVO> showFollowFeed(BoardVO boardFilter) throws DataAccessException {
		List<FeedVO> feedList = sqlSession.selectList("mapper.social.showFollowFeed",boardFilter);
		return feedList;
	}
	
	@Override
	public FeedVO showOneFeed(BoardVO boardFilter) throws DataAccessException {
		FeedVO feed = sqlSession.selectOne("mapper.social.showOneFeed",boardFilter);
		return feed;
	}
	
	@Override
	public List<CommentFeedVO> showCommentInBoard(BoardVO boardFilter) throws DataAccessException {
		List<CommentFeedVO> commentList = sqlSession.selectList("mapper.social.showCommentInBoard",boardFilter);
		return commentList;
	}

}
