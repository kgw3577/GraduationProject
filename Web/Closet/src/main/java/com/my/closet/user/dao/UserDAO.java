package com.my.closet.user.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.my.closet.user.vo.UserVO;

public interface UserDAO {

	public List<UserVO> selectAllUsers() throws DataAccessException; //모든 사용자 선택
	public UserVO selectUser(String id) throws DataAccessException; //id로 사용자 선택 (미확인)
	public String addUser(UserVO userVO) throws DataAccessException; //사용자 추가
	public String verifyIdPwd(String id, String pwd) throws DataAccessException; //ID, 패스워드 유효성 확인
	//아이디 찾기
	//비밀번호 찾기
	public String updateUser(UserVO userVO) throws DataAccessException; //사용자 정보 수정 (미확인)
	public String deleteUser(String id) throws DataAccessException; //사용자 삭제

}