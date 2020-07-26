package com.my.closet.social;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.my.closet.board.dao.BoardDAO;
import com.my.closet.board.service.BoardService;
import com.my.closet.board.vo.BoardVO;
import com.my.closet.social.vo.FeedVO;

@RestController("socialController")
@RequestMapping("/social")
public class SocialControllerImpl implements SocialController {

	// Logger 클래스 객체 가져오기
	private static final Logger logger = LoggerFactory.getLogger(SocialControllerImpl.class);

	@Autowired
	private BoardDAO boardDAO;

	// 모든 게시글 리스트 조회. 웹 관리용.
	@Override
	@RequestMapping(value = "/feedlist", method = RequestMethod.GET)
	public ModelAndView feedlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(getViewName(request));
		List<FeedVO> feedList = boardDAO.showAllFeed(new BoardVO());
		mav.addObject("feedList", feedList);
		return mav;
	}

	// 피드 가져오기
	@Override
	@RequestMapping(value = "/feed/share", method = RequestMethod.GET)
	public ResponseEntity<List<FeedVO>> showAllFeed(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "pageSize", required = false) String pageSize) throws Exception {
		List<FeedVO> feedList;
		try {
			BoardVO boardFilter = genPageFilter(page, pageSize);
			feedList = boardDAO.showAllFeed(boardFilter);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<FeedVO>>(Collections.<FeedVO>emptyList(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<FeedVO>>(feedList, HttpStatus.OK);
	}

	// 해당 게시물 코멘트 가져오기
	@Override
	@RequestMapping(value = "/comment/{boardNo}", method = RequestMethod.GET)
	public ResponseEntity<List<FeedVO>> showCommentInBoard(@PathVariable("boardNo") String boardNo,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "pageSize", required = false) String pageSize) throws Exception {
		List<FeedVO> commentList;
		try {
			BoardVO boardFilter = genPageFilter(page, pageSize);
			boardFilter.setBoardNo(Integer.parseInt(boardNo));
			commentList = boardDAO.showCommentInBoard(boardFilter);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<FeedVO>>(Collections.<FeedVO>emptyList(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<FeedVO>>(commentList, HttpStatus.OK);
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

	public BoardVO genPageFilter(String page, String pageSize) {
		// 페이지 필터 생성
		BoardVO boardFilter = new BoardVO();
		if (page != null && pageSize != null) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			boardFilter.setPageStart(pageInt * pageSizeInt);
			boardFilter.setPageSize(pageSizeInt);
		}
		return boardFilter;
	}
	
	public BoardVO setPageFilter(BoardVO boardFilter, String page, String pageSize) {
		// 페이지 필터 생성
		if (page != null && pageSize != null) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			boardFilter.setPageStart(pageInt * pageSizeInt);
			boardFilter.setPageSize(pageSizeInt);
		}
		return boardFilter;
	}

}
