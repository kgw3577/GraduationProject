package com.my.closet.clothes.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.my.closet.clothes.vo.ClothesVO;

@Repository("clothesDAO")
public class ClothesDAOImpl implements ClothesDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<ClothesVO> selectAllClothes() throws DataAccessException {
		List<ClothesVO> clolist = sqlSession.selectList("mapper.clothes.searchClothes", new ClothesVO());
		return clolist;
	}

	@Override
	public ClothesVO selectThisClothes(String no) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClothesVO> selectClothes(ClothesVO clothesVO) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addClothes(ClothesVO clothesVO) throws DataAccessException {

		int result = sqlSession.insert("mapper.clothes.insertClothes", clothesVO);
		
		if (result == 1)
			return "ok"; //insert 성공
		else
			return "fail"; //insert 실패
	}

	@Override
	public String updateClothes(ClothesVO clothesVO) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteClothes(String no) throws DataAccessException {
		// TODO Auto-generated method stub
		
		
		/*
		 여러 행 삭제 :
		 $param = "1,2,3,4,5";
		 delete from table where id in ($param);
		 
		 쿼리문에서 in 으로 처리
		 */
		
		
		return null;
	}

}
