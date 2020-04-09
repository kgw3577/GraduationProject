package com.my.closet.user.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.my.closet.user.vo.LoginVO;
import com.my.closet.user.vo.UserVO;

public interface UserService {

	public List<UserVO> listAllUsers() throws DataAccessException; //모든 사용자의 회원 정보 리스트 조회
	public UserVO infoUser(String id) throws DataAccessException; //특정 사용자의 회원 정보 조회 (미확인)
	public String join(UserVO userVO) throws DataAccessException; //회원가입
	public String login(LoginVO loginVO) throws DataAccessException; //로그인
	//아이디 찾기
	//비밀번호 찾기
	public String modifyUser(UserVO userVO) throws DataAccessException; //회원 정보 수정 (미확인)
	public String deleteAccount(String id) throws DataAccessException; //회원탈퇴
	
}
