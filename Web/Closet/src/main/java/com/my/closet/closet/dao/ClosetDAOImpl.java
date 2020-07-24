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
