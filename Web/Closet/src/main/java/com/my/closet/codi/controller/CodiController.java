package com.my.closet.codi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.codi.vo.CodiVO;

public interface CodiController {
	
	public ModelAndView codilist(HttpServletRequest request, HttpServletResponse response) throws Exception; //모든 코디 리스트 조회
	public ResponseEntity<List<CodiVO>> myAllCodi(String userID, String page, String pageSize) throws Exception; //내 코디 전부 조회
	
	public ResponseEntity<CodiVO> infoCodi(String codiNo) throws Exception; //코디 정보 보기
	public ResponseEntity<List<CodiVO>> searchCodi(String userID, CodiVO codiFilter, String page, String pageSize) throws Exception; //코디 찾기
	
	public ResponseEntity<String> addCodi(MultipartHttpServletRequest multipartRequest, MultipartFile multipartFile) throws Exception; //코디 추가

	public ResponseEntity<String> modifyCodi(CodiVO codiInfo) throws Exception; //코디 정보 수정
	public ResponseEntity<String> deleteCodi(String codiNo) throws Exception; //코디 삭제
	
}
