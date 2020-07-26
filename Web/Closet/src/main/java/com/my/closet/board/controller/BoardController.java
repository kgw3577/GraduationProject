package com.my.closet.board.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.board.vo.BoardVO;

public interface BoardController {
	
	public ModelAndView boardlist(HttpServletRequest request, HttpServletResponse response) throws Exception; //모든 게시글 리스트 조회. 웹 관리용.
	
	
	public ResponseEntity<List<BoardVO>> AllBoard(String page, String pageSize) throws Exception; //모든 게시글 리스트 조회

	public ResponseEntity<List<BoardVO>> myAllBoard(HttpSession session, String page, String pageSize) throws Exception; //내 게시글 전부 조회
	public ResponseEntity<List<BoardVO>> usersAllBoard(String userID, String page, String pageSize) throws Exception; //특정 유저 게시글 전부 조회
	
	public ResponseEntity<BoardVO> infoBoard(String boardNo) throws Exception; //특정 게시글 조회
	public ResponseEntity<List<BoardVO>> searchBoard(BoardVO boardFilter, String page, String pageSize) throws Exception; //특정 조건의 게시글 리스트 조회
	
	public ResponseEntity<String> addBoard(MultipartHttpServletRequest multipartRequest, MultipartFile multipartFile) throws Exception; //게시글 추가

	public ResponseEntity<String> modifyBoard(BoardVO boardInfo) throws Exception; //게시글 수정
	public ResponseEntity<String> deleteBoard(String boardNo) throws Exception; //게시글 삭제
	
}
