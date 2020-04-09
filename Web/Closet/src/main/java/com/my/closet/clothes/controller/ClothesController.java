package com.my.closet.clothes.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.clothes.vo.ClothesVO;

public interface ClothesController {
	
	public ModelAndView clotheslist(HttpServletRequest request, HttpServletResponse response) throws Exception; //모든 옷 리스트 조회
	public ResponseEntity<ClothesVO> myClothes(String no) throws Exception; //특정 옷 조회
	public ResponseEntity<String> addClothes(ClothesVO clothesVO) throws Exception; //옷 추가
	//
	public ResponseEntity<String> modifyClothes(ClothesVO clothesVO) throws Exception; //옷 정보 수정
	public ResponseEntity<String> deleteClothes(String no) throws Exception; //옷 삭제
}
