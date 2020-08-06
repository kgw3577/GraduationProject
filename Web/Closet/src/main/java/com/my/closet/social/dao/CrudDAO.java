
package com.my.closet.social.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.my.closet.board.vo.BoardVO;
import com.my.closet.social.vo.CommentFeedVO;
import com.my.closet.social.vo.FeedVO;
import com.my.closet.social.vo.FollowVO;

public interface CrudDAO {

	//팔로우
	public List<FollowVO> selectFollow(FollowVO followFilter) throws DataAccessException; //팔로우 select
	public String addFollow(FollowVO followInfo) throws DataAccessException; //팔로우 추가
	public String deleteFollow(FollowVO followInfo) throws DataAccessException; //팔로우 삭제
	//하트
	//댓글 좋아요
}
