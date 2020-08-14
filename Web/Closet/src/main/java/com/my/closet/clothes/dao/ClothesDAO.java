package com.my.closet.clothes.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.my.closet.clothes.vo.ClothesVO;

public interface ClothesDAO {

	public List<ClothesVO> selectAllClothes() throws DataAccessException; //모든 옷 선택
	public ClothesVO selectThisClothes(String no) throws DataAccessException; //no로 옷 하나 선택 -- int? String?
	public List<ClothesVO> selectClothes(ClothesVO clothesVO) throws DataAccessException; //조건으로 옷 선택
	
	public String addClothes(Map<String, Object> clothesMap) throws DataAccessException; //옷 추가 (예외적으로 해쉬맵으로 받음)
	public String updateClothes(ClothesVO clothesInfo) throws DataAccessException; //옷 정보 수정
	public String deleteClothes(String no) throws DataAccessException; //옷 삭제 (1,2,3,4,5로 여러 개 가능) --주의

}
