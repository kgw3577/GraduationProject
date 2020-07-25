package com.my.closet.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.my.closet.user.vo.UserVO;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	//모든 사용자
	@Override
	public List<UserVO> selectAllUsers() throws DataAccessException {
		List<UserVO> userlist = sqlSession.selectList("mapper.user.searchUser", new UserVO());
		return userlist;
	}
	//id로 사용자 하나 선택
	@Override
	public UserVO selectThisUser(String userID) throws DataAccessException {
		UserVO userInfo = (UserVO) sqlSession.selectOne("mapper.user.searchUser", new UserVO(userID));
		return userInfo;
	}
	//조건으로 사용자 선택
	@Override
	public List<UserVO> selectUser(UserVO userFilter) throws DataAccessException {
		List<UserVO> userlist = sqlSession.selectList("mapper.user.searchUser", userFilter);
		return userlist;
	}
	
	//사용자 추가
	@Override
	public String addUser(UserVO userInfo) throws DataAccessException {

		System.out.println("insert 쿼리 실행 전");
		int result = sqlSession.insert("mapper.user.insertUser", userInfo);
		System.out.println("insert 쿼리 실행");
		
		if (result == 1) {
			System.out.println("insert 성공");
			return "ok"; //insert 성공
		}
		else {
			System.out.println("insert 실패");
			return "fail"; //insert 실패
		}		
	}


	//사용자 정보 수정
	@Override
	public String updateUser(UserVO userInfo) throws DataAccessException {
		int result = sqlSession.update("mapper.user.updateUser",userInfo);
		
		if (result==1)
			return "ok"; //update 성공
		else
			return "fail"; //update 실패
	}

	//사용자 삭제
	@Override
	public String deleteUser(String userID) throws DataAccessException {
		int result = sqlSession.delete("mapper.user.deleteUser",userID);
		
		if (result==1)
			return "ok";
		else
			return "fail";
	}
	
	//해당 유저 존재여부 확인
	@Override
	public String checkExistUser(UserVO userInfo) throws DataAccessException{
		String userID = userInfo.getUserID();
		String email = userInfo.getEmail();
		
		if(userID != null) {
			// 해당 ID를 가진 사용자가 있는지 검색
			UserVO selectedUser = (UserVO) sqlSession.selectOne("mapper.user.searchUser", new UserVO(userID));
			// 검색 결과가 있고 중복된 ID가 있으면 "id" 반환
			if (selectedUser != null) {
				if (selectedUser.getUserID().equals(userID))
					return "id";
			}
		} else if(email != null) {
			// 해당 이메일을 가진 사용자가 있는지 검색
			UserVO userFilter = new UserVO();
			userFilter.setEmail(email);
			UserVO selectedUser = (UserVO) sqlSession.selectOne("mapper.user.searchUser", userFilter);
			// 검색 결과가 있고 중복된 이메일이 있으면 "email" 반환
			if (selectedUser != null) {
				if (selectedUser.getEmail().equals(email))
					return "email";
			}		
		}
		// 어느 조건에도 해당 안 될 시 "ok" 반환
		return "ok";
	}
	
	//ID, 패스워드 유효성 확인
	@Override
	public String verifyIdPwd(String userID, String pwd) throws DataAccessException {
		
		// ID와 비밀번호가 일치하는 사용자가 있는지 검색
		UserVO selectedUser = (UserVO) sqlSession.selectOne("mapper.user.searchUser", new UserVO(userID, pwd));

		// 검색 결과가 있고 ID와 비밀번호가 일치하면 "true" 반환
		if (selectedUser != null) {
			if (selectedUser.getUserID().equals(userID) && selectedUser.getPwd().equals(pwd))
				return "true";
		}
		return "false";
	}

}
