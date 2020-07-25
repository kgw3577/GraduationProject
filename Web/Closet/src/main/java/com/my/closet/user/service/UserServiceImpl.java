package com.my.closet.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
	
	int defaultimageNum = 12; //프로필 디폴트이미지 개수
	
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
	public String join(UserVO userInfo) throws DataAccessException {
		
		if(userInfo.getNickname()==null) //닉네임 디폴트 설정 = 아이디
			userInfo.setNickname(userInfo.getUserID());
		if(userInfo.getEmailChecked()==null) //이메일 체크여부 디폴트 설정
			userInfo.setEmailChecked("no");
		if(userInfo.getPfImageName()==null) { //프사 디폴트 설정
			Random random = new Random();
			String pfImageName = "default"+(random.nextInt(defaultimageNum)+1)+".png";
			String pfImagePath = "/download/profile?imageFileName="+pfImageName;
			userInfo.setPfImageName(pfImageName);
			userInfo.setPfImagePath(pfImagePath);
			System.out.println("디폴트 프사명 : "+pfImageName);
		}
		
		//회원 추가
		String result = userDAO.addUser(userInfo);
		if("fail".equals(result))
			return result;
		
		//회원가입과 동시에 default 옷장 생성
		ClosetVO closetVO = new ClosetVO(userInfo.getUserID(), "default", "no"); 
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
	public String modifyProfileImage(MultipartHttpServletRequest multipartRequest) throws DataAccessException {
		
		//업로드 컨트롤러의 upload 함수로 사진 파일+파라미터 정보가 담긴 request를 한꺼번에 넘기고, 업로드 후에 해쉬맵으로 속성 정보를 받아온다.
		Map<String, Object> infoMap = new HashMap<String, Object>();
		try {
			infoMap=uploadCon.upload("profile", multipartRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("업로드 콘 실패");
		}
		System.out.println("해쉬맵 생성 성공");
		
		UserVO profileImageInfo = new UserVO();
		profileImageInfo.setUserID(infoMap.get("userID").toString());
		profileImageInfo.setPfImageName(infoMap.get("fileName").toString());
		profileImageInfo.setPfImagePath(infoMap.get("filePath").toString());
		
		//받아온 해쉬맵을 이용해 dao에 데이터베이스 추가를 요청한다. (회원 추가)
		return userDAO.updateUser(profileImageInfo);

	}

	@Override
	public String winmodifyProfileImage(MultipartHttpServletRequest multipartRequest) throws DataAccessException {
		
		//업로드 컨트롤러의 upload 함수로 사진 파일+파라미터 정보가 담긴 request를 한꺼번에 넘기고, 업로드 후에 해쉬맵으로 속성 정보를 받아온다.
		Map<String, Object> infoMap = new HashMap<String, Object>();
		try {
			infoMap=uploadCon.upload("windows", multipartRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("업로드 콘 실패");
		}
		System.out.println("해쉬맵 생성 성공");
		
		UserVO profileImageInfo = new UserVO();
		profileImageInfo.setUserID(infoMap.get("userID").toString());
		profileImageInfo.setPfImageName(infoMap.get("fileName").toString());
		profileImageInfo.setPfImagePath(infoMap.get("filePath").toString());
		
		//받아온 해쉬맵을 이용해 dao에 데이터베이스 추가를 요청한다. (회원 추가)
		return userDAO.updateUser(profileImageInfo);		
	}
	
	
	
	@Override
	public String deleteAccount(String userID) throws DataAccessException {
		return userDAO.deleteUser(userID);
	}

}
