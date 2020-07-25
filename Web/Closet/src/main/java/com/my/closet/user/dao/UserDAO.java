package com.my.closet.user.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.my.closet.user.vo.UserVO;

public interface UserDAO {

	public List<UserVO> selectAllUsers() throws DataAccessException; //모든 사용자
	
	public UserVO selectThisUser(String userID) throws DataAccessException; //id로 사용자 하나 선택
	public List<UserVO> selectUser(UserVO userFilter) throws DataAccessException; //조건으로 사용자 선택
	
	public String addUser(UserVO userInfo) throws DataAccessException; //사용자 추가
	//아이디 찾기
	//비밀번호 찾기
	public String updateUser(UserVO userInfo) throws DataAccessException; //사용자 정보 수정
	public String deleteUser(String userID) throws DataAccessException; //사용자 삭제
	
	public String checkExistUser(UserVO userInfo) throws DataAccessException; //해당 유저 존재여부 확인
	public String verifyIdPwd(String userID, String pwd) throws DataAccessException; //ID, 패스워드 유효성 확인

}