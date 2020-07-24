package com.my.closet.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.my.closet.closet.dao.ClosetDAO;
import com.my.closet.closet.vo.ClosetVO;
import com.my.closet.file.FileUploadController;
import com.my.closet.user.dao.UserDAO;
import com.my.closet.user.vo.LoginVO;
import com.my.closet.user.vo.UserVO;

@Service("userService")
@Transactional(propagation=Propagation.REQUIRED) //서비스 클래스의 모든 메서드에 트랜잭션을 적용
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private FileUploadController uploadCon;
	@Autowired
	private ClosetDAO closetDAO;

	@Override
	public List<UserVO> listAllUsers() throws DataAccessException {
		List<UserVO> userList = userDAO.selectAllUsers();
		return userList;
	}

	@Override
	public UserVO infoUser(String userID) throws DataAccessException {
		return userDAO.selectThisUser(userID);
	}
	
	@Override
	public List<UserVO> searchUser(UserVO userFilter, String page, String pageSize) throws DataAccessException{
		//페이지 필터 적용
		if(!page.isEmpty()&&!pageSize.isEmpty()) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			userFilter.setPageStart(pageInt*pageSizeInt);
			userFilter.setPageSize(pageSizeInt);
		}
		return userDAO.selectUser(userFilter);
	}

	@Override
	public String join(MultipartHttpServletRequest multipartRequest) throws DataAccessException {
		
		//업로드 컨트롤러의 upload 함수로 사진 파일+파라미터 정보가 담긴 request를 한꺼번에 넘기고, 업로드 후에 해쉬맵으로 속성 정보를 받아온다.
		Map<String, Object> userMap = new HashMap<String, Object>();
		try {
			userMap=uploadCon.upload("profile", multipartRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("업로드 콘 실패");
		}
		System.out.println("해쉬맵 생성 성공");
		for(Map.Entry<String, Object> elem : userMap.entrySet()){
			 
            String key = elem.getKey();
            Object value = elem.getValue();
 
            System.out.println(key+" : "+value);
        }
		
		//받아온 해쉬맵을 이용해 dao에 데이터베이스 추가를 요청한다. (회원 추가)
		String result = userDAO.addUser(userMap);
		if("fail".equals(result))
			return result;
		
		//회원가입과 동시에 default 옷장 생성
		ClosetVO closetVO = new ClosetVO(userMap.get("userID").toString(), "default", "no"); 
		result = closetDAO.insertCloset(closetVO);
		if("fail".equals(result))
			return "옷장 생성 실패";
		
		return "ok";
	}

	@Override
	public String winjoin(MultipartHttpServletRequest multipartRequest) throws DataAccessException {
		
		//업로드 컨트롤러의 upload 함수로 사진 파일+파라미터 정보가 담긴 request를 한꺼번에 넘기고, 업로드 후에 해쉬맵으로 속성 정보를 받아온다.
		Map<String, Object> userMap = new HashMap<String, Object>();
		try {
			userMap=uploadCon.upload("windows", multipartRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("업로드 콘 실패");
		}
		System.out.println("해쉬맵 생성 성공");
		for(Map.Entry<String, Object> elem : userMap.entrySet()){
			 
            String key = elem.getKey();
            Object value = elem.getValue();
 
            System.out.println(key+" : "+value);
        }
		
		//받아온 해쉬맵을 이용해 dao에 데이터베이스 추가를 요청한다. (회원 추가)
		String result = userDAO.addUser(userMap);
		if("fail".equals(result))
			return result;
		
		//회원가입과 동시에 default 옷장 생성
		ClosetVO closetVO = new ClosetVO(userMap.get("userID").toString(), "default", "no"); 
		result = closetDAO.insertCloset(closetVO);
		if("fail".equals(result))
			return "옷장 생성 실패";
		
		return "ok";
	}
	
	@Override
	public String login(LoginVO loginVO) throws DataAccessException {
		String userID = loginVO.getUserID();
		String pwd = loginVO.getPwd();
		return userDAO.verifyIdPwd(userID, pwd);
	}

	@Override
	public String modifyUser(UserVO userInfo) throws DataAccessException {
		return userDAO.updateUser(userInfo);
	}

	@Override
	public String deleteAccount(String userID) throws DataAccessException {
		return userDAO.deleteUser(userID);
	}

}
