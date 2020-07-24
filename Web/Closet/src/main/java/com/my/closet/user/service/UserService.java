package com.my.closet.user.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.my.closet.board.vo.BoardVO;
import com.my.closet.user.vo.LoginVO;
import com.my.closet.user.vo.UserVO;

public interface UserService {

	public List<UserVO> listAllUsers() throws DataAccessException; //모든 사용자의 회원 정보 리스트 조회
	
	public UserVO infoUser(String userID) throws DataAccessException; //특정 사용자의 회원 정보 조회
	public List<UserVO> searchUser(UserVO userFilter, String page, String pageSize) throws DataAccessException; //조건으로 사용자 리스트 검색
	
	public String join(MultipartHttpServletRequest multipartRequest) throws DataAccessException; //회원가입
	public String winjoin(MultipartHttpServletRequest multipartRequest) throws DataAccessException; //회원가입
	public String login(LoginVO loginVO) throws DataAccessException; //로그인
	//아이디 찾기
	//비밀번호 찾기
	public String modifyUser(UserVO userInfo) throws DataAccessException; //회원 정보 수정
	public String deleteAccount(String userID) throws DataAccessException; //회원탈퇴
	//해당 옷장이랑 옷 데이터베이스, 사진 파일들 전부 같이 삭제해야 함.
	
}
