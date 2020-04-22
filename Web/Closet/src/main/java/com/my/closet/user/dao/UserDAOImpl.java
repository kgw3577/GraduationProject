package com.my.closet.user.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.my.closet.user.vo.UserVO;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<UserVO> selectAllUsers() throws DataAccessException {
		List<UserVO> userlist = sqlSession.selectList("mapper.user.searchUser", new UserVO());
		return userlist;
	}

	@Override
	public UserVO selectUser(String id) throws DataAccessException {
		UserVO userInfo = (UserVO) sqlSession.selectOne("mapper.user.searchUser", new UserVO(id));
		return userInfo;
	}

	@Override
	public String addUser(UserVO userVO) throws DataAccessException {

		// 해당 ID를 가진 사용자가 있는지 검색
		String id = userVO.getId();
		UserVO selectedUser = (UserVO) sqlSession.selectOne("mapper.user.searchUser", new UserVO(id));

		// 검색 결과가 있고 중복된 ID가 있으면 "id" 반환
		if (selectedUser != null) {
			if (selectedUser.getId().equals(id))
				return "id";
		}
		// 검색 결과가 없으면 회원가입 실행
		else {
			int result = sqlSession.insert("mapper.user.insertUser", userVO);
			if (result == 1)
				return "ok";
			// JSONObject obj = new JSONObject();
			// obj.put("userID", id);
		}
		return "fail";
	}

	@Override
	public String verifyIdPwd(String id, String pwd) throws DataAccessException {
		
		// ID와 비밀번호가 일치하는 사용자가 있는지 검색
		UserVO selectedUser = (UserVO) sqlSession.selectOne("mapper.user.searchUser", new UserVO(id, pwd));

		// 검색 결과가 있고 ID와 비밀번호가 일치하면 "true" 반환
		if (selectedUser != null) {
			if (selectedUser.getId().equals(id) && selectedUser.getPwd().equals(pwd))
				return "true";
		}
		return "false";
	}

	@Override
	public String updateUser(UserVO userVO) throws DataAccessException {
		int result = sqlSession.update("mapper.user.updateUser",userVO);
		
		if (result==1)
			return "ok"; //update 성공
		else
			return "fail"; //update 실패
	}

	@Override
	public String deleteUser(String id) throws DataAccessException {
		int result = sqlSession.delete("mapper.user.deleteUser",id);
		
		if (result==1)
			return "ok";
		else
			return "fail";
	}

}
