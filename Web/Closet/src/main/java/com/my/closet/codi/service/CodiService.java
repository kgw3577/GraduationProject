package com.my.closet.codi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.my.closet.codi.vo.CodiVO;

public interface CodiService {
	
	public List<CodiVO> listAllCodi() throws DataAccessException; //모든 코디의 코디 정보 리스트 조회. 웹 관리용.
	public List<CodiVO> myAllCodi(String userID, CodiVO codiFilter) throws DataAccessException; //내 코디 전부 조회
	
	public CodiVO infoCodi(String codiNo) throws DataAccessException; //특정 코디의 코디 정보 조회
	public List<CodiVO> searchCodi(String userID, CodiVO codiFilter) throws DataAccessException; //특정 조건의 코디 정보 리스트 조회
	
	public String winAddCodi(MultipartHttpServletRequest multipartRequest) throws DataAccessException; //웹 실험용 코디 추가
	public String addCodi(MultipartHttpServletRequest multipartRequest) throws DataAccessException; //코디 추가
	public String modifyCodi(CodiVO codiInfo) throws DataAccessException; //코디 정보 수정
	//사진을 변경할 경우의 수를 생각해야 함.
	public String deleteCodi(String codiNo) throws DataAccessException; //코디 삭제 (1,2,3,4,5로 여러 개 가능) --주의
	//FileDeleteController 구현 후 그 함수 사용.
}
