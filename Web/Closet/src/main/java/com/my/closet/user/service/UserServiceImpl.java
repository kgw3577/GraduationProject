package com.my.closet.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.my.closet.user.dao.UserDAO;
import com.my.closet.user.vo.LoginVO;
import com.my.closet.user.vo.UserVO;

@Service("userService")
/*@Transactional(propagation=Propagation.REQUIRED) */
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;

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
		return userDAO.addUser(userVO);
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
