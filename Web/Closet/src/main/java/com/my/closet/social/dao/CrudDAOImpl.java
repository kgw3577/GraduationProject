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
import com.my.closet.social.vo.CommentVO;
import com.my.closet.social.vo.FeedVO;
import com.my.closet.social.vo.FollowVO;
import com.my.closet.social.vo.HeartVO;
import com.my.closet.user.vo.UserVO;

@Repository("crudDAO")
public class CrudDAOImpl implements CrudDAO {

	@Autowired
	private SqlSession sqlSession;

	// 조건으로 팔로우 선택
	@Override
	public List<FollowVO> selectFollow(FollowVO followFilter) throws DataAccessException {
		List<FollowVO> followList = sqlSession.selectList("mapper.social_crud.searchFollow", followFilter);
		return followList;
	}

	// 팔로우 추가
	@Override
	public String addFollow(FollowVO followInfo) throws DataAccessException {

		System.out.println("insert 쿼리 실행 전");
		int result = sqlSession.insert("mapper.social_crud.insertFollow", followInfo);
		System.out.println("insert 쿼리 실행");

		String numFollow = followInfo.getFollowedID();

		if (result == 1) {
			System.out.println("insert 성공");
			return numFollow; // insert 성공
		} else {
			System.out.println("insert 실패");
			return "fail"; // insert 실패
		}
		
		
		
	}

	// 팔로우 삭제
	@Override
	public String deleteFollow(FollowVO followInfo) throws DataAccessException {
		int result = sqlSession.delete("mapper.social_crud.deleteFollow", followInfo);

		if (result == 1) {
			System.out.println("delete 성공");
			int res = sqlSession.selectOne("mapper.social_crud.numFollow", followInfo);
			return Integer.toString(res); // insert 성공
		} else {
			System.out.println("delete 실패");
			return "fail"; // insert 실패
		}
		
	}

	
	
	
	/*하트*/
	// 조건으로 하트 선택
	@Override
	public List<HeartVO> selectHeart(HeartVO heartFilter) throws DataAccessException {
		List<HeartVO> heartList = sqlSession.selectList("mapper.social_crud.searchHeart", heartFilter);
		return heartList;
	}

	// 하트 추가
	@Override
	public String addHeart(HeartVO heartFilter) throws DataAccessException {

		System.out.println("insert 쿼리 실행 전");
		int result = sqlSession.insert("mapper.social_crud.insertHeart", heartFilter);
		System.out.println("insert 쿼리 실행");
		
		String numHeart = heartFilter.getBoardNo();

		if (result == 1) {
			System.out.println("insert 성공");
			return numHeart; // insert 성공
		} else {
			System.out.println("insert 실패");
			return "fail"; // insert 실패
		}
	}

	// 하트 삭제
	@Override
	public String deleteHeart(HeartVO heartFilter) throws DataAccessException {
		int result = sqlSession.delete("mapper.social_crud.deleteHeart", heartFilter);


		if (result == 1) {
			System.out.println("delete 성공");
			int res = sqlSession.selectOne("mapper.social_crud.numHeart", heartFilter);
			return Integer.toString(res); // insert 성공
		} else {
			System.out.println("delete 실패");
			return "fail"; // insert 실패
		}
	}	
	
	
	
	
	// 코멘트 추가
	@Override
	public String addComment(CommentVO commentFilter) throws DataAccessException {

		System.out.println("insert 쿼리 실행 전");
		int result = sqlSession.insert("mapper.social_crud.insertComment", commentFilter);
		System.out.println("insert 쿼리 실행");
		
		String numComment = commentFilter.getBoardNo();

		if (result == 1) {
			System.out.println("insert 성공");
			return numComment; // insert 성공
		} else {
			System.out.println("insert 실패");
			return "fail"; // insert 실패
		}
	}

	// 코멘트 삭제
	@Override
	public String deleteComment(CommentVO commentFilter) throws DataAccessException {
		int result = sqlSession.delete("mapper.social_crud.deleteComment", commentFilter);


		if (result == 1) {
			System.out.println("delete 성공");
			int res = sqlSession.selectOne("mapper.social_crud.numComment", commentFilter);
			return Integer.toString(res); // insert 성공
		} else {
			System.out.println("delete 실패");
			return "fail"; // insert 실패
		}
	}		
		
	
	
}
