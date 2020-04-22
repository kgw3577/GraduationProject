package com.my.closet.clothes.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.my.closet.clothes.vo.ClothesVO;

public interface ClothesService {
	public List<ClothesVO> selectAllUsers() throws DataAccessException; //모든 옷 선택
	public ClothesVO selectUser(String id) throws DataAccessException; //id로 옷 선택 
	public String addClothes(ClothesVO clothesVO) throws DataAccessException; //옷 추가
	//
	public String uploadImage() throws DataAccessException; //옷 사진 파일 업로드 로직
	//아이디 찾기
	//비밀번호 찾기
	public String updateClothes(ClothesVO clothesVO) throws DataAccessException; //옷 정보 수정
	public String deleteClothes(String id) throws DataAccessException; //옷 삭제
}
