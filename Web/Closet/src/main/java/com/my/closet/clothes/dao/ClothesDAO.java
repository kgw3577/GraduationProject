package com.my.closet.clothes.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.my.closet.clothes.vo.ClothesVO;

public interface ClothesDAO {

	public List<ClothesVO> selectAllUsers() throws DataAccessException; //모든 옷 선택
	public ClothesVO selectUser(String no) throws DataAccessException; //no로 옷 선택 
	public String addClothes(ClothesVO clothesVO) throws DataAccessException; //옷 추가
	//아이디 찾기
	//비밀번호 찾기
	public String updateClothes(ClothesVO clothesVO) throws DataAccessException; //옷 정보 수정
	public String deleteClothes(String no) throws DataAccessException; //옷 삭제
	
}
