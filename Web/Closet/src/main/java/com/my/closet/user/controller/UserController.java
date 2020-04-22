package com.my.closet.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.user.vo.LoginVO;
import com.my.closet.user.vo.UserVO;

public interface UserController {
	
	public ModelAndView userlist(HttpServletRequest request, HttpServletResponse response) throws Exception; //모든 사용자의 회원 정보 리스트 조회
	public ResponseEntity<UserVO> myInfo(String no) throws Exception; //특정 사용자의 회원 정보 조회
	public ResponseEntity<String> join(UserVO user) throws Exception; //회원가입
	public ResponseEntity<String> login(LoginVO loginVO) throws Exception; //로그인
	//아이디 찾기
	//비밀번호 찾기
	public ResponseEntity<String> modifyUser(UserVO user) throws Exception; //회원 정보 수정
	public ResponseEntity<String> deleteAccount(String id) throws Exception; //회원탈퇴

	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
