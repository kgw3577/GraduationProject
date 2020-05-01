package com.my.closet.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.user.service.UserService;
import com.my.closet.user.vo.LoginVO;
import com.my.closet.user.vo.UserVO;

@RestController("userController")
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

	//Logger 클래스 객체 가져오기
	private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	UserVO userVO;
	
	//회원 리스트 보기(웹)
	@Override
	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	public ModelAndView userlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(getViewName(request));
		List<UserVO> userList = userService.listAllUsers();
		mav.addObject("userList", userList);
		return mav;
	}

	//내 정보 확인
	@Override
	@RequestMapping(value = "/myInfo/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserVO> myInfo(@PathVariable("id") String id) throws Exception {

		UserVO userInfo = null;
		try {
			userInfo = userService.infoUser(id);
		} catch (Exception e) {
			return new ResponseEntity<UserVO>(userInfo, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<UserVO>(userInfo, HttpStatus.OK);
		//자동으로 JSON으로 변환해서 보내줌.
	}

	//회원 가입
	@Override
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public ResponseEntity<String> join(@RequestBody UserVO user) throws Exception {
		// @RequestBody : 전송된 파라미터를 userVO 해당 속성에 자동으로 설정 (JSON을 VO로 자동 변환)
		String answer = null;
		try {
			answer = userService.join(user);

			logger.info("info 레벨 - ID : " + user.getId()); //로그 메시지 레벨을 info로 설정
			logger.info("info 레벨 - Password : " + user.getPwd());
			logger.info("info 레벨 - 이름 : " + user.getName());
			logger.info("info 레벨 - 성별 : " + user.getGender());
			
		} catch (Exception e) {
			return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<String>(answer, HttpStatus.OK);
	}

	//로그인
	@Override
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody LoginVO loginVO, HttpSession session) throws Exception {
		
		try {
			LoginVO loVO = (LoginVO) session.getAttribute("login");
			System.out.println("세션에 저장된 userID : "+loVO.getId());
		}catch(Exception e) {
			System.out.println("세션 정보 없음");
		}
		
		logger.info("ID : " + loginVO.getId());
		logger.info("Password : " + loginVO.getPwd());
		String answer = null;
		try {
			answer = userService.login(loginVO);
		} catch (Exception e) {
			session.invalidate();
			return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		if (answer.equals("true")) //로그인 성공시
			session.setAttribute("login", loginVO); //세션에 로그인 정보 (새로) 바인딩
		else //실패시
			session.invalidate(); //세션 날림
		
		return new ResponseEntity<String>(answer, HttpStatus.OK);
	}

	//회원정보 수정
	@Override
	@RequestMapping(value = "/modify", method = RequestMethod.PUT)
	public ResponseEntity<String> modifyUser(@RequestBody UserVO user) throws Exception {
		String answer = null;
		try {
			answer = userService.modifyUser(user);
		} catch (Exception e) {
			return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<String>(answer, HttpStatus.OK);
	}

	//회원 삭제
	@Override
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAccount(@PathVariable("id") String id) throws Exception {
		String answer = null;
		try {
			answer = userService.deleteAccount(id);
		} catch (Exception e) {
			return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<String>(answer, HttpStatus.OK);
	}
	
	//jsp 양식 보기(웹)
	@Override
	@RequestMapping(value = "/*Form", method = RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(getViewName(request));
		return mav;
	}

	private String getViewName(HttpServletRequest request) throws Exception {
		// 요청명 구하는 함수. 첫번째 요청명까지 포함. /user/join.do면 -> user/join만 추출

		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String viewName = uri.substring(begin, end);
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
		}
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
		}
		return viewName;
	}
}
