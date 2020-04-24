package com.my.closet.clothes.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.clothes.service.ClothesService;
import com.my.closet.clothes.vo.ClothesVO;


@RestController("clothesController")
@RequestMapping("/clothes")
public class ClothesControllerImpl implements ClothesController {

	@Autowired
	private ClothesService clothesService;
	@Autowired
	ClothesVO clothesVO;
	
	//옷 추가 테스트용 폼(웹)
	@RequestMapping(value = "/addform")
	public ModelAndView addTestform() {
		return new ModelAndView("addTestUploadForm");
	}
	
	//전체 옷 조회 (웹용)
	@Override
	@RequestMapping(value = "/clolist", method = RequestMethod.GET)
	public ModelAndView clotheslist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(getViewName(request));
		List<ClothesVO> clothesList = clothesService.listAllClothes();
		mav.addObject("clothesList", clothesList);
		return mav;
	}

	//임시. 미구현.
	@Override
	@RequestMapping(value = "/myClothes/{no}", method = RequestMethod.GET)
	public ResponseEntity<ClothesVO> myClothes(@PathVariable("no") String no) throws Exception {
		// TODO Auto-generated method stub
		ClothesVO clothesInfo = new ClothesVO("candy","candy's closet","none.jpg");
		return new ResponseEntity<ClothesVO>(clothesInfo, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ClothesVO> searchClothes(ClothesVO clothesVO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	//옷 추가하기
	@Override
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<String> addClothes(MultipartHttpServletRequest multipartRequest) throws Exception {
		// 주의 : userID와 closetName이 일치하는 테이블이 이미 존재해야만 add가 가능함
		// 같은 이름인 파일이 있으면 그 위에 새로 덮어씌워지는 것 주의. 해결방법 찾아야 함.
		String answer = null;
		try {
			answer = clothesService.addClothes(multipartRequest);
			//윈도우 시험용 : winAddClothes
		} catch (Exception e) {
			return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<String>(answer, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> modifyClothes(ClothesVO clothesVO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> deleteClothes(String no) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
