package com.my.closet.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.board.vo.BoardVO;
import com.my.closet.user.vo.LoginVO;
import com.my.closet.user.vo.UserVO;

public interface UserController {
	
	public ModelAndView userlist(HttpServletRequest request, HttpServletResponse response) throws Exception; //모든 사용자의 회원 정보 리스트 조회
	
	public ResponseEntity<UserVO> myInfo(String userID) throws Exception; //특정 사용자의 회원 정보 조회
	public ResponseEntity<List<UserVO>> searchUser(UserVO userFilter, String page, String pageSize) throws Exception; //특정 조건의 사용자 리스트 조회
	
	public ResponseEntity<String> join(UserVO userInfo) throws Exception; //회원 가입
	public ResponseEntity<String> login(LoginVO loginVO, HttpSession session) throws Exception; //로그인
	//아이디 찾기
	//비밀번호 찾기
	public ResponseEntity<String> modifyUser(UserVO userInfo) throws Exception; //회원 정보 수정
	public ResponseEntity<String> modifyProfileImage(MultipartHttpServletRequest multipartRequest, MultipartFile multipartFile) throws Exception; //회원가입
	
	
	public ResponseEntity<String> deleteAccount(String userID) throws Exception; //회원탈퇴

	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
