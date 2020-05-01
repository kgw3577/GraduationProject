package com.my.closet.closet.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.my.closet.closet.vo.ClosetVO;

@Repository("closetDAO")
public class ClosetDAOImpl implements ClosetDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<ClosetVO> selectAllCloset() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClosetVO selectCloset(String userID, String closetName) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertCloset(ClosetVO closetVO) throws DataAccessException {
		
		/*
		// 해당 ID를 가진 사용자가 있는지 검색
		String id = userVO.getId();
		UserVO selectedUser = (UserVO) sqlSession.selectOne("mapper.user.searchUser", new UserVO(id));

		// 검색 결과가 있고 중복된 ID가 있으면 "id" 반환
		if (selectedUser != null) {
			if (selectedUser.getId().equals(id))
				return "id";
		}
		// 검색 결과가 없으면 회원가입 실행
		else {
			int result = sqlSession.insert("mapper.user.insertUser", userVO);
			if (result == 1)
				return "ok";
		}
		return "fail";
		*/
		
		//검사없이 임시로 간단하게 구현
		int result = sqlSession.insert("mapper.closet.insertCloset", closetVO);
		if (result == 1)
			return "ok";
		return "fail";
		
	}

	@Override
	public String updateCloset(ClosetVO closetVO) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteCloset(String userID, String closetName) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
