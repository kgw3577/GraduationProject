package com.my.closet.board.controller;

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

import com.my.closet.cloBoard.service.CloBoardService;
import com.my.closet.cloBoard.vo.CloBoardVO;
import com.my.closet.user.controller.UserControllerImpl;


@RestController("cloBoardController")
@RequestMapping("/cloBoard")
public class BoardControllerImpl implements BoardController {

	//Logger 클래스 객체 가져오기
	private static final Logger logger = LoggerFactory.getLogger(BoardControllerImpl.class);
		
	@Autowired
	private BoardService cloBoardService;
	@Autowired
	BoardVO cloBoardVO;
	
	//코디 추가 테스트용 폼(웹. 관리용)
	@RequestMapping(value = "/addform")
	public ModelAndView addTestform() {
		return new ModelAndView("cloBoard/addTestUploadForm");
	}
	
	//전체 코디 조회 (웹. 관리용)
	@Override
	@RequestMapping(value = "/cloBoardlist", method = RequestMethod.GET)
	public ModelAndView cloBoardlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(getViewName(request));
		List<BoardVO> cloBoardList = cloBoardService.listAllCloBoard();
		mav.addObject("cloBoardList", cloBoardList);
		return mav;
	}
	
	//내 코디 전부 조회
	@Override
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<BoardVO>> myAllCloBoard(HttpSession session, @RequestParam String page, @RequestParam String pageSize) throws Exception{
		List<BoardVO> myCloBoardList;
		try{
			BoardVO cloBoardFilter = new BoardVO();
			if(!page.isEmpty()&&!pageSize.isEmpty()) {
				int pageInt = Integer.parseInt(page);
				int pageSizeInt = Integer.parseInt(pageSize);
				cloBoardFilter.setPageStart(pageInt*pageSizeInt);
				cloBoardFilter.setPageSize(pageSizeInt);
			}
			myCloBoardList = cloBoardService.myAllCloBoard(session, cloBoardFilter);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<BoardVO>>(Collections.<BoardVO>emptyList(), HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<BoardVO>>(myCloBoardList, HttpStatus.OK);
	}
	
	//코디 정보 보기
	@Override
	@RequestMapping(value = "/info/{cloBoardNo}", method = RequestMethod.GET)
	public ResponseEntity<BoardVO> infoCloBoard(@PathVariable("cloBoardNo") String cloBoardNo) throws Exception {
		BoardVO cloBoardInfo;
		try {
			cloBoardInfo = cloBoardService.infoCloBoard(cloBoardNo);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<BoardVO>(new BoardVO(), HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<BoardVO>(cloBoardInfo, HttpStatus.OK);
	}

	//코디 찾기
	@Override
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<List<BoardVO>> searchCloBoard(HttpSession session, BoardVO cloBoardFilter, @RequestParam String page, @RequestParam String pageSize) throws Exception {
		List<BoardVO> searched_cloBoardList;
		try{
			if(!page.isEmpty()&&!pageSize.isEmpty()) {
				int pageInt = Integer.parseInt(page);
				int pageSizeInt = Integer.parseInt(pageSize);
				cloBoardFilter.setPageStart(pageInt*pageSizeInt);
				cloBoardFilter.setPageSize(pageSizeInt);
			}
			searched_cloBoardList = cloBoardService.searchCloBoard(session, cloBoardFilter);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<BoardVO>>(Collections.<BoardVO>emptyList(), HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<BoardVO>>(searched_cloBoardList, HttpStatus.OK);
	}

	//코디 추가하기
	@Override
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers="Content-Type=multipart/form-data")
	public ResponseEntity<String> addCloBoard(MultipartHttpServletRequest multipartRequest,
			@RequestPart(value = "file", required = false) MultipartFile multipartFile) throws Exception{
		
		logger.debug("#create: multipartFile({})", multipartFile);
		//System.out.println(multipartFile.getName()); //윈도우 테스트시 x
		
		String answer = null;
		try {
			answer = cloBoardService.addCloBoard(multipartRequest);
			//윈도우 시험용 : winAddCloBoard
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<String>(answer, HttpStatus.OK);
	}

	//코디 정보 수정
	@Override
	@RequestMapping(value = "/modify", method = RequestMethod.PUT)
	public ResponseEntity<String> modifyCloBoard(@RequestBody BoardVO cloBoardInfo) throws Exception {
		System.out.println("받아온 코디 정보 : "+cloBoardInfo.getPlace()+cloBoardInfo.getFileName());
				
		String answer = null;
		try {
			answer = cloBoardService.modifyCloBoard(cloBoardInfo);
		} catch (Exception e) {
			return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<String>(answer, HttpStatus.OK);
	}

	//코디 삭제
	@Override
	@RequestMapping(value = "/delete/{cloBoardNo}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCloBoard(@PathVariable("cloBoardNo") String cloBoardNo) throws Exception {
		String answer;
		try {
			answer= cloBoardService.deleteCloBoard(cloBoardNo);
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
