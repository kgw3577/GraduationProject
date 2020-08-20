package com.my.closet.social.controller;

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
import com.my.closet.social.dao.CrudDAO;
import com.my.closet.social.dao.SocialDAO;
import com.my.closet.social.vo.CommentFeedVO;
import com.my.closet.social.vo.DetailFeedVO;
import com.my.closet.social.vo.ExpandedFeedVO;
import com.my.closet.social.vo.FeedVO;
import com.my.closet.social.vo.FollowVO;
import com.my.closet.social.vo.HeartVO;
import com.my.closet.social.vo.UserspaceVO;
import com.my.closet.user.vo.UserVO;
import com.my.closet.util.Util;

@RestController("socialController")
@RequestMapping("/social")
public class SocialControllerImpl implements SocialController {

	// Logger 클래스 객체 가져오기
	private static final Logger logger = LoggerFactory.getLogger(SocialControllerImpl.class);

	@Autowired
	private SocialDAO socialDAO;
	@Autowired
	private CrudDAO crudDAO;

	/* 피드 */
	// 모든 피드 리스트 조회. 웹 관리용.
	@Override
	@RequestMapping(value = "/feedlist", method = RequestMethod.GET)
	public ModelAndView feedlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(getViewName(request));
		List<FeedVO> feedList = socialDAO.showAllFeed(new FollowVO());
		mav.addObject("feedList", feedList);
		return mav;
	}
	// 피드 가져오기
	@Override
	@RequestMapping(value = "/feed/newest", method = RequestMethod.GET)
	public ResponseEntity<List<FeedVO>> showAllFeed(
			@RequestParam(value = "myID", required = false) String myID,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "pageSize", required = false) String pageSize) throws Exception {
		List<FeedVO> feedList;
		try {
			FollowVO followFilter = new FollowVO();
			followFilter.setFollowerID(myID);
			followFilter = Util.setPageFilter(followFilter, page, pageSize);
			feedList = socialDAO.showAllFeed(followFilter);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<FeedVO>>(Collections.<FeedVO>emptyList(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<FeedVO>>(feedList, HttpStatus.OK);
	}
	// 팔로우 피드 가져오기
	@Override
	@RequestMapping(value = "/feed/following/{userID}", method = RequestMethod.GET)
	public ResponseEntity<List<FeedVO>> showFollowFeed(@PathVariable("userID") String userID,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "pageSize", required = false) String pageSize) throws Exception {
		List<FeedVO> feedList;
		try {
			FollowVO followFilter = new FollowVO();
			followFilter.setFollowerID(userID);
			followFilter = Util.setPageFilter(followFilter, page, pageSize);
			feedList = socialDAO.showFollowFeed(followFilter);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<FeedVO>>(Collections.<FeedVO>emptyList(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<FeedVO>>(feedList, HttpStatus.OK);
	}
	// 피드 조건 검색
	@Override
	@RequestMapping(value = "/feed/search", method = RequestMethod.PUT)
	public ResponseEntity<List<FeedVO>> searchFeed(
			@RequestBody ExpandedFeedVO feedFilter,
			@RequestParam(value = "myID", required = false) String myID,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "pageSize", required = false) String pageSize) throws Exception {
		List<FeedVO> feedList;
		try {
			feedFilter.setMyID(myID);
			feedFilter = Util.setPageFilter(feedFilter, page, pageSize);
			feedList = socialDAO.searchFeed(feedFilter);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<FeedVO>>(Collections.<FeedVO>emptyList(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<FeedVO>>(feedList, HttpStatus.OK);
	}
	// 해당 사용자가 좋아요한 피드
	@Override
	@RequestMapping(value = "/space/{userID}/heart", method = RequestMethod.GET)
	public ResponseEntity<List<FeedVO>> showHeartFeed(@PathVariable("userID") String userID,
			@RequestParam(value = "myID", required = false) String myID,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "pageSize", required = false) String pageSize) throws Exception {
		List<FeedVO> feedList;
		try {
			FollowVO followFilter = new FollowVO();
			followFilter.setFollowerID(myID);
			followFilter.setFollowedID(userID);
			followFilter = Util.setPageFilter(followFilter, page, pageSize);
			feedList = socialDAO.showHeartFeed(followFilter);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<FeedVO>>(Collections.<FeedVO>emptyList(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<FeedVO>>(feedList, HttpStatus.OK);
	}
	
	
	// 해당 게시물 세부 내용 가져오기
	@Override
	@RequestMapping(value = "/feed/detail/{boardNo}", method = RequestMethod.GET)
	public ResponseEntity<List<DetailFeedVO>> showDetailFeed(@PathVariable("boardNo") String boardNo,
			@RequestParam("myID") String myID) throws Exception {
		List<DetailFeedVO> feedAndChildList;
		try {
			HeartVO feedFilter = new HeartVO();
			feedFilter.setBoardNo(boardNo);
			feedFilter.setHearterID(myID);
			feedAndChildList = socialDAO.showDetailFeed(feedFilter);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<DetailFeedVO>>(Collections.<DetailFeedVO>emptyList(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<DetailFeedVO>>(feedAndChildList, HttpStatus.OK);
	}
	

	// 해당 게시물 코멘트 가져오기
	@Override
	@RequestMapping(value = "/comment/{boardNo}", method = RequestMethod.GET)
	public ResponseEntity<List<CommentFeedVO>> showCommentInBoard(@PathVariable("boardNo") String boardNo,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "pageSize", required = false) String pageSize) throws Exception {
		List<CommentFeedVO> commentList;
		try {
			BoardVO boardFilter = genPageFilter(page, pageSize);
			boardFilter.setBoardNo(Integer.parseInt(boardNo));
			commentList = socialDAO.showCommentInBoard(boardFilter);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<CommentFeedVO>>(Collections.<CommentFeedVO>emptyList(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<CommentFeedVO>>(commentList, HttpStatus.OK);
	}

	
	/* 팔로우 */
	// 모든 팔로우 리스트 조회. 웹 관리용.
	@Override
	@RequestMapping(value = "/followlist", method = RequestMethod.GET)
	public ModelAndView followlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(getViewName(request));
		List<FollowVO> followList = crudDAO.selectFollow(new FollowVO());
		mav.addObject("followList", followList);
		return mav;
	}
	//팔로우 검색
	@Override
	@RequestMapping(value = "/follow/search", method = RequestMethod.GET)
	public ResponseEntity<List<FollowVO>> searchFollow(@RequestBody FollowVO followFilter) throws Exception {
		List<FollowVO> followList;
		try {
			followList = crudDAO.selectFollow(followFilter);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<FollowVO>>(Collections.<FollowVO>emptyList(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<FollowVO>>(followList, HttpStatus.OK);
	}
	
	//팔로우 상태 변경
	@Override
	@RequestMapping(value = "/follow/execute", method = RequestMethod.POST)
	public ResponseEntity<String> revertFollow(@RequestBody FollowVO followInfo) throws Exception {
		//@RequestBody : 전송된 파라미터를 FollowVO 해당 속성에 자동으로 설정 (JSON을 VO로 자동 변환)

		String answer = null;
		
		//팔로우 여부 체크
		List<FollowVO> existFollow = crudDAO.selectFollow(followInfo);
		
		if(existFollow.size() == 0) { //팔로우 되어 있지 않으면
			try {
				answer = crudDAO.addFollow(followInfo); //팔로우 추가
			} catch (Exception e) {
				return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
			}
			if(!"fail".equals(answer)) { //성공시
				
				return new ResponseEntity<String>(answer+"_following", HttpStatus.OK); //팔로우중 상태 메시지 보냄
			}
		}
		else { //팔로우된 상태면
			try {
				answer = crudDAO.deleteFollow(followInfo); //팔로우 삭제
			} catch (Exception e) {
				return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
			}
			if(!"fail".equals(answer)) { //성공시
				
				return new ResponseEntity<String>(answer+"_not_following", HttpStatus.OK); //팔로우중 상태 메시지 보냄
			}
		}
		return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	/*하트*/
	// 모든 하트 리스트 조회. 웹 관리용.
	@Override
	@RequestMapping(value = "/heartlist", method = RequestMethod.GET)
	public ModelAndView heartlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(getViewName(request));
		List<HeartVO> heartList = crudDAO.selectHeart(new HeartVO());
		mav.addObject("heartList", heartList);
		return mav;
	}
	//하트 검색
	@Override
	@RequestMapping(value = "/heart/search", method = RequestMethod.GET)
	public ResponseEntity<List<HeartVO>> searchHeart(@RequestBody HeartVO heartFilter) throws Exception {
		List<HeartVO> heartList;
		try {
			heartList = crudDAO.selectHeart(heartFilter);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<HeartVO>>(Collections.<HeartVO>emptyList(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<HeartVO>>(heartList, HttpStatus.OK);
	}
	
	//하트 상태 변경
	@Override
	@RequestMapping(value = "/heart/execute", method = RequestMethod.POST)
	public ResponseEntity<String> revertHeart(@RequestBody HeartVO heartInfo) throws Exception {
		//@RequestBody : 전송된 파라미터를 HeartVO 해당 속성에 자동으로 설정 (JSON을 VO로 자동 변환)

		String answer = null;
		
		//팔로우 여부 체크
		List<HeartVO> existHeart = crudDAO.selectHeart(heartInfo);
		
		if(existHeart.size() == 0) { //팔로우 되어 있지 않으면
			try {
				answer = crudDAO.addHeart(heartInfo); //팔로우 추가
			} catch (Exception e) {
				return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
			}
			if(!"fail".equals(answer)) { //성공시
				
				return new ResponseEntity<String>(answer+"_hearting", HttpStatus.OK); //팔로우중 상태 메시지 보냄
			}
		}
		else { //팔로우된 상태면
			try {
				answer = crudDAO.deleteHeart(heartInfo); //팔로우 삭제
			} catch (Exception e) {
				return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
			}
			if(!"fail".equals(answer)) { //성공시
				
				return new ResponseEntity<String>(answer+"_not_hearting", HttpStatus.OK); //팔로우하고있지않음 상태 메시지 보냄
			}
		}
		return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	
	
	
	
	
	/*유저스페이스*/
	@Override
	@RequestMapping(value = "/space/{userID}", method = RequestMethod.GET)
	public ResponseEntity<UserspaceVO> showUserspace(@PathVariable("userID") String userID, @RequestParam("myID") String myID) throws Exception {
		UserspaceVO userspaceInfo;
		try {
			FollowVO interUserFilter = new FollowVO();
			interUserFilter.setFollowerID(myID);
			interUserFilter.setFollowedID(userID);
			userspaceInfo = socialDAO.showUserSpace(interUserFilter);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<UserspaceVO>(new UserspaceVO(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<UserspaceVO>(userspaceInfo, HttpStatus.OK);
	}
	
	/*코디 추천*/
	@Override
	@RequestMapping(value = "/recommend/full/{userID}", method = RequestMethod.GET)
	public ResponseEntity<List<BoardVO>> recommendFull(@PathVariable("userID") String userID) throws Exception {
		List<BoardVO> boardList;
		
		try {
			BoardVO recoFilter = new BoardVO();
			recoFilter.setUserID(userID);
			recoFilter.setBoardNo((int) (Math.random() * 3) +2); //2~4개 뽑아줌
			boardList = socialDAO.recommendFull(recoFilter);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<BoardVO>>(Collections.<BoardVO>emptyList(),
					HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<BoardVO>>(boardList, HttpStatus.OK);
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
