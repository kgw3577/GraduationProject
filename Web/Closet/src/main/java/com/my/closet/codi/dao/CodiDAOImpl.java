package com.my.closet.codi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.my.closet.codi.vo.CodiVO;

@Repository("codiDAO")
public class CodiDAOImpl implements CodiDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<CodiVO> selectAllCodi() throws DataAccessException {
		//빈 필터를 보냄으로써 모든 코디 조회
		List<CodiVO> codilist = sqlSession.selectList("mapper.codi.searchCodi", new CodiVO());
		return codilist;
	}

	@Override
	public CodiVO selectThisCodi(String codiNo) throws DataAccessException {
		
		CodiVO codiVO = new CodiVO();
		codiVO.setCodiNo(Integer.parseInt(codiNo));
		
		CodiVO codi = sqlSession.selectOne("mapper.codi.searchCodi",codiVO);
		return codi;
	}

	@Override
	public List<CodiVO> selectCodi(CodiVO codiFilter) throws DataAccessException {
		List<CodiVO> codiList = sqlSession.selectList("mapper.codi.searchCodi", codiFilter);
		return codiList;
	}

	@Override
	public String addCodi(Map<String, Object> codiInfo) throws DataAccessException {

		System.out.println("insert 쿼리 실행 전");
		int result = sqlSession.insert("mapper.codi.insertCodi", codiInfo);
		System.out.println("insert 쿼리 실행");
		
		if (result == 1) {
			System.out.println("쿼리 성공");
			return "ok"; //insert 성공
		}
		else {
			System.out.println("쿼리 실패");
			return "fail"; //insert 실패
		}			
	}

	@Override
	public String updateCodi(CodiVO codiInfo) throws DataAccessException {
		int result = sqlSession.update("mapper.codi.updateCodi",codiInfo);
		
		if (result==1)
			return "ok"; //update 성공
		else
			return "fail"; //update 실패
	}

	@Override
	public String deleteCodi(String codiNo) throws DataAccessException {
		int result = sqlSession.delete("mapper.codi.deleteCodi",codiNo);
		
		if (result==1)
			return "ok";
		else
			return "fail";
	}

}
