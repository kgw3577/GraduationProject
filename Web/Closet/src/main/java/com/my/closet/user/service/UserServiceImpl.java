package com.my.closet.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.my.closet.closet.dao.ClosetDAO;
import com.my.closet.closet.vo.ClosetVO;
import com.my.closet.user.dao.UserDAO;
import com.my.closet.user.vo.LoginVO;
import com.my.closet.user.vo.UserVO;

@Service("userService")
@Transactional(propagation=Propagation.REQUIRED) //서비스 클래스의 모든 메서드에 트랜잭션을 적용
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ClosetDAO closetDAO;

	@Override
	public List<UserVO> listAllUsers() throws DataAccessException {
		List<UserVO> userList = userDAO.selectAllUsers();
		return userList;
	}

	@Override
	public UserVO infoUser(String id) throws DataAccessException {
		return userDAO.selectUser(id);
	}

	@Override
	public String join(UserVO userVO) throws DataAccessException {
		
		//회원 추가
		userDAO.addUser(userVO);
		
		//회원가입과 동시에 default 옷장 생성
		ClosetVO closetVO = new ClosetVO(userVO.getId(), "default", "no"); 
		String result = closetDAO.insertCloset(closetVO);
		
		//변경해야..
		return result;
	}

	@Override
	public String login(LoginVO loginVO) throws DataAccessException {
		String id = loginVO.getId();
		String pwd = loginVO.getPwd();
		return userDAO.verifyIdPwd(id, pwd);
	}

	@Override
	public String modifyUser(UserVO userVO) throws DataAccessException {
		return userDAO.updateUser(userVO);
	}

	@Override
	public String deleteAccount(String id) throws DataAccessException {
		return userDAO.deleteUser(id);
	}

}
