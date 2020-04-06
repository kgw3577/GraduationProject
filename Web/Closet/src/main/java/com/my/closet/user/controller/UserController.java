package com.my.closet.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.my.closet.user.vo.UserVO;

public interface UserController {
	
	public ModelAndView userlist(String answer, HttpServletRequest request, HttpServletResponse response) throws Exception; //모든 사용자의 회원 정보 리스트 조회
	public ModelAndView myInfo(String id, HttpServletRequest request, HttpServletResponse response) throws Exception; //특정 사용자의 회원 정보 조회 (미확인)
	public ModelAndView join(UserVO user, HttpServletRequest request, HttpServletResponse response) throws Exception; //회원가입
	public ModelAndView login(String id, String pwd, HttpServletRequest request, HttpServletResponse response) throws Exception; //로그인
	//아이디 찾기
	//비밀번호 찾기
	public ModelAndView modifyUser(UserVO user, HttpServletRequest request, HttpServletResponse response) throws Exception; //회원 정보 수정
	public ModelAndView deleteAccount(String id, HttpServletRequest request, HttpServletResponse response) throws Exception; //회원탈퇴

	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	
}
