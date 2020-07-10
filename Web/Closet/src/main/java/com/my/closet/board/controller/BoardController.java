package com.my.closet.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.board.vo.BoardVO;

public interface BoardController {
	
	public ModelAndView cloBoardlist(HttpServletRequest request, HttpServletResponse response) throws Exception; //모든 코디 리스트 조회
	public ResponseEntity<List<BoardVO>> myAllCloBoard(HttpSession session, String page, String pageSize) throws Exception; //내 코디 전부 조회
	
	public ResponseEntity<BoardVO> infoCloBoard(String cloBoardNo) throws Exception; //코디 정보 보기
	public ResponseEntity<List<BoardVO>> searchCloBoard(HttpSession session, BoardVO cloBoardFilter, String page, String pageSize) throws Exception; //코디 찾기
	
	public ResponseEntity<String> addCloBoard(MultipartHttpServletRequest multipartRequest, MultipartFile multipartFile) throws Exception; //코디 추가

	public ResponseEntity<String> modifyCloBoard(BoardVO cloBoardInfo) throws Exception; //코디 정보 수정
	public ResponseEntity<String> deleteCloBoard(String cloBoardNo) throws Exception; //코디 삭제
	
}
