package com.my.closet.clothes.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.my.closet.clothes.vo.ClothesVO;
import com.my.closet.user.vo.UserVO;

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
		
		ClothesVO cloVO = new ClothesVO();
		cloVO.setCloNo(Integer.parseInt(no));
		
		ClothesVO clothes = sqlSession.selectOne("mapper.clothes.searchClothes",cloVO);
		return clothes;
	}

	@Override
	public List<ClothesVO> selectClothesByList(HashMap keywordMap) throws DataAccessException {
		// cloInfo는 일일이  put, detailCategory는 리스트로 들어가있을 것..
		List<ClothesVO> clolist = sqlSession.selectList("mapper.clothes.searchClothesByList", keywordMap);

		
		return clolist;
	}
	
	@Override
	public List<ClothesVO> selectClothes(ClothesVO clothesVO) throws DataAccessException {
		List<ClothesVO> clolist = sqlSession.selectList("mapper.clothes.searchClothes", clothesVO);
		return clolist;
	}

	@Override
	public String addClothes(Map<String, Object> clothesMap) throws DataAccessException {

		System.out.println("쿼리 실행 전");
		int result = sqlSession.insert("mapper.clothes.insertClothes", clothesMap);
		
		System.out.println("쿼리 실행");
		
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
	public String addClothesData(ClothesVO cloInfo) throws DataAccessException {

		System.out.println("insert 쿼리 실행 전");
		int result = sqlSession.insert("mapper.clothes.insertClothesData", cloInfo);
		System.out.println("insert 쿼리 실행");
		
		if (result == 1) {
			System.out.println("insert 성공");
			return "ok"; //insert 성공
		}
		else {
			System.out.println("insert 실패");
			return "fail"; //insert 실패
		}		
	}	
	
	

	@Override
	public String updateClothes(ClothesVO clothesInfo) throws DataAccessException {
		int result = sqlSession.update("mapper.clothes.updateClothes",clothesInfo);
		System.out.println(result);
		
		if (result==1)
			return "ok"; //update 성공
		else
			return "fail"; //update 실패
	}

	@Override
	public String deleteClothes(String no) throws DataAccessException {
		int result = sqlSession.delete("mapper.clothes.deleteClothes",Integer.parseInt(no));
		System.out.println(result);
		
		if (result==1)
			return "ok";
		else
			return "fail";
	}
	

}
