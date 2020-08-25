package com.my.closet.clothes.controller;

import java.util.HashMap;
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

import com.my.closet.clothes.vo.ClothesVO;

public interface ClothesController {
	
	public ModelAndView clotheslist(HttpServletRequest request, HttpServletResponse response) throws Exception; //모든 옷 리스트 조회
	public ResponseEntity<List<ClothesVO>> myAllClothes(String userID, String page, String pageSize) throws Exception; //내 옷 전부 조회
	
	public ResponseEntity<ClothesVO> infoClothes(String no) throws Exception; //옷 정보 보기
	public ResponseEntity<List<ClothesVO>> searchClothes(ClothesVO clothesVO, String userID, String page, String pageSize) throws Exception; //옷 찾기
	public ResponseEntity<List<ClothesVO>> searchClothesByList(
			@RequestBody HashMap map,
			@RequestParam String userID, 
			@RequestParam String mode,
			@RequestParam String page, 
			@RequestParam String pageSize) throws Exception; //리스트로 옷 찾기
	
	public ResponseEntity<String> addClothes(MultipartHttpServletRequest multipartRequest,MultipartFile multipartFile) throws Exception; //옷 추가
	public ResponseEntity<String> addClothesFrData(@RequestBody ClothesVO cloInfo) throws Exception;
	
	public ResponseEntity<String> modifyClothes(ClothesVO clothesInfo) throws Exception; //옷 정보 수정
	public ResponseEntity<String> deleteClothes(String no) throws Exception; //옷 삭제
	
}
