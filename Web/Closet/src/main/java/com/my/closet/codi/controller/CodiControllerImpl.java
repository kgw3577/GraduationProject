package com.my.closet.codi.controller;

import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.codi.service.CodiService;
import com.my.closet.codi.vo.CodiVO;
import com.my.closet.user.controller.UserControllerImpl;


@RestController("codiController")
@RequestMapping("/codi")
public class CodiControllerImpl implements CodiController {

	//Logger 클래스 객체 가져오기
	private static final Logger logger = LoggerFactory.getLogger(CodiControllerImpl.class);
		
	@Autowired
	private CodiService codiService;
	@Autowired
	CodiVO codiVO;
	
	//코디 추가 테스트용 폼(웹. 관리용)
	@RequestMapping(value = "/addform")
	public ModelAndView addTestform() {
		return new ModelAndView("codi/addTestUploadForm");
	}
	
	//전체 코디 조회 (웹. 관리용)
	@Override
	@RequestMapping(value = "/codilist", method = RequestMethod.GET)
	public ModelAndView codilist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(getViewName(request));
		List<CodiVO> codiList = codiService.listAllCodi();
		mav.addObject("codiList", codiList);
		return mav;
	}
	
	//내 코디 전부 조회
	@Override
	@RequestMapping(value = "/share", method = RequestMethod.GET)
	public ResponseEntity<List<CodiVO>> myAllCodi(HttpSession session, @RequestParam String page, @RequestParam String pageSize) throws Exception{
		List<CodiVO> myCodiList;
		try{
			CodiVO codiFilter = new CodiVO();
			if(!page.isEmpty()&&!pageSize.isEmpty()) {
				int pageInt = Integer.parseInt(page);
				int pageSizeInt = Integer.parseInt(pageSize);
				codiFilter.setPageStart(pageInt*pageSizeInt);
				codiFilter.setPageSize(pageSizeInt);
			}
			myCodiList = codiService.myAllCodi(session, codiFilter);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<CodiVO>>(Collections.<CodiVO>emptyList(), HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<CodiVO>>(myCodiList, HttpStatus.OK);
	}
	
	//코디 정보 보기
	@Override
	@RequestMapping(value = "/info/{codiNo}", method = RequestMethod.GET)
	public ResponseEntity<CodiVO> infoCodi(@PathVariable("codiNo") String codiNo) throws Exception {
		CodiVO codiInfo;
		try {
			codiInfo = codiService.infoCodi(codiNo);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<CodiVO>(new CodiVO(), HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<CodiVO>(codiInfo, HttpStatus.OK);
	}

	//코디 찾기
	@Override
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<List<CodiVO>> searchCodi(HttpSession session, CodiVO codiFilter, @RequestParam String page, @RequestParam String pageSize) throws Exception {
		List<CodiVO> searched_codiList;
		try{
			if(!page.isEmpty()&&!pageSize.isEmpty()) {
				int pageInt = Integer.parseInt(page);
				int pageSizeInt = Integer.parseInt(pageSize);
				codiFilter.setPageStart(pageInt*pageSizeInt);
				codiFilter.setPageSize(pageSizeInt);
			}
			searched_codiList = codiService.searchCodi(session, codiFilter);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<CodiVO>>(Collections.<CodiVO>emptyList(), HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<CodiVO>>(searched_codiList, HttpStatus.OK);
	}

	//코디 추가하기
	@Override
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers="Content-Type=multipart/form-data")
	public ResponseEntity<String> addCodi(MultipartHttpServletRequest multipartRequest,
			@RequestPart(value = "file", required = false) MultipartFile multipartFile) throws Exception{
		
		logger.debug("#create: multipartFile({})", multipartFile);
		//System.out.println(multipartFile.getName()); //윈도우 테스트시 x
		
		String answer = null;
		try {
			answer = codiService.addCodi(multipartRequest);
			//윈도우 시험용 : winAddCodi
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<String>(answer, HttpStatus.OK);
	}

	//코디 정보 수정
	@Override
	@RequestMapping(value = "/modify", method = RequestMethod.PUT)
	public ResponseEntity<String> modifyCodi(@RequestBody CodiVO codiInfo) throws Exception {
		System.out.println("받아온 코디 정보 : "+codiInfo.getPlace()+codiInfo.getFileName());
				
		String answer = null;
		try {
			answer = codiService.modifyCodi(codiInfo);
		} catch (Exception e) {
			return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<String>(answer, HttpStatus.OK);
	}

	//코디 삭제
	@Override
	@RequestMapping(value = "/delete/{codiNo}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCodi(@PathVariable("codiNo") String codiNo) throws Exception {
		String answer;
		try {
			answer= codiService.deleteCodi(codiNo);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("fail", HttpStatus.SERVICE_UNAVAILABLE);
		}
		// TODO Auto-generated method stub
		return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
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
