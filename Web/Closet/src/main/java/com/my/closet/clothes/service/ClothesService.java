package com.my.closet.clothes.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.my.closet.clothes.vo.ClothesVO;

public interface ClothesService {
	
	public List<ClothesVO> listAllClothes() throws DataAccessException; //모든 옷의 옷 정보 리스트 조회
	public List<ClothesVO> myAllClothes(HttpSession session, ClothesVO clothesVO) throws DataAccessException; //내 옷 전부 조회
	
	public ClothesVO infoClothes(String no) throws DataAccessException; //특정 옷의 옷 정보 조회 -- int? String?
	public List<ClothesVO> searchClothes(HttpSession session, ClothesVO clothesVO) throws DataAccessException; //특정 조건의 옷 정보 리스트 조회
	
	public String winAddClothes(MultipartHttpServletRequest multipartRequest) throws DataAccessException;
	public String addClothes(MultipartHttpServletRequest multipartRequest) throws DataAccessException; //옷 추가
	public String modifyClothes(ClothesVO clothesInfo) throws DataAccessException; //옷 정보 수정
	//사진을 변경할 경우의 수를 생각해야 함.
	public String deleteClothes(String no) throws DataAccessException; //옷 삭제 (1,2,3,4,5로 여러 개 가능) --주의
	//FileDeleteController 구현 후 그 함수 사용.
	
}
