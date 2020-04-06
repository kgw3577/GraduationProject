package com.my.closet.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.user.service.UserService;
import com.my.closet.user.vo.UserVO;

@Controller("userController")
//@RequestMapping("/user")
public class UserControllerImpl implements UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	UserVO userVO ;
	
	@Override
	@RequestMapping(value="/user/userlist.do" ,method = RequestMethod.GET)
	public ModelAndView userlist(@RequestParam(value="answer", required=false) String answer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(getViewName(request));
		List<UserVO> userList = userService.listAllUsers();
		mav.addObject("userList", userList);
		mav.addObject("answer", answer);
		return mav;
	}

	@Override
	@RequestMapping(value="/user/myInfo.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView myInfo(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView(getViewName(request));

		request.setCharacterEncoding("utf-8");
		UserVO userInfo = userService.infoUser(id);
		
		mav.addObject("info", userInfo);
		return mav;
	}

	@Override
	@RequestMapping(value="/user/join.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView join(@ModelAttribute("info") UserVO user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//@ModelAttribute : 전송된 파라미터를 userVO 해당 속성에 자동으로 설정 (자바빈 역할)
		//뷰에 user를 addObject 하지 않아도 됨. info 이름으로 바인딩되어있음.
		
		ModelAndView mav = new ModelAndView("redirect:/user/userlist.do");
		
		request.setCharacterEncoding("utf-8");
		String answer = userService.join(user);
		
		System.out.println("ID : "+user.getId());
		System.out.println("Password : "+user.getPwd());
		System.out.println("이름 : "+user.getName());
		System.out.println("성별 : "+user.getGender());
		
		mav.addObject("answer", answer);
		return mav;
	}

	@Override
	@RequestMapping(value="/user/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(@RequestParam("id") String id, @RequestParam("pwd") String pwd, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView(getViewName(request));
		
		request.setCharacterEncoding("utf-8");
		String answer = userService.login(id, pwd);
		
		System.out.println("ID : "+id);
		System.out.println("Password : "+pwd);
		
		mav.addObject("id", id);
		mav.addObject("pwd", pwd);
		mav.addObject("answer", answer);
		return mav;
	}

	@Override
	@RequestMapping(value="/user/modify.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView modifyUser(@ModelAttribute("info") UserVO user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("redirect:/user/userlist.do");
		
		request.setCharacterEncoding("utf-8");
		String answer = userService.modifyUser(user);
		
		mav.addObject("answer", answer);
		return mav;
	}

	@Override
	@RequestMapping(value="/user/delete.do", method = RequestMethod.GET)
	public ModelAndView deleteAccount(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("redirect:/user/userlist.do");
		
		request.setCharacterEncoding("utf-8");
		String answer = userService.deleteAccount(id);
		
		mav.addObject("answer", answer);
		return mav;
	}

	@Override
	@RequestMapping(value="/user/*Form.do", method = RequestMethod.GET)
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
