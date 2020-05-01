package com.my.closet.clothes.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.clothes.vo.ClothesVO;

public interface ClothesController {
	
	public ModelAndView clotheslist(HttpServletRequest request, HttpServletResponse response) throws Exception; //모든 옷 리스트 조회
	public ResponseEntity<List<ClothesVO>> myAllClothes(HttpSession session, String page, String pageSize) throws Exception; //내 옷 전부 조회
	
	public ResponseEntity<ClothesVO> infoClothes(String no) throws Exception; //옷 정보 보기
	public ResponseEntity<ClothesVO> searchClothes(ClothesVO clothesVO) throws Exception; //특정 옷 조회
	
	public ResponseEntity<String> addClothes(MultipartHttpServletRequest multipartRequest,MultipartFile multipartFile) throws Exception; //옷 추가

	public ResponseEntity<String> modifyClothes(ClothesVO clothesVO) throws Exception; //옷 정보 수정
	public ResponseEntity<String> deleteClothes(String no) throws Exception; //옷 삭제
	
	
}
