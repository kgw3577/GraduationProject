package com.my.closet.social;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.social.vo.CommentFeedVO;
import com.my.closet.social.vo.FeedVO;


public interface SocialController {
	
	public ModelAndView feedlist(HttpServletRequest request, HttpServletResponse response) throws Exception; //모든 게시글 리스트 조회. 웹 관리용.
	
	public ResponseEntity<List<FeedVO>> showAllFeed(String page, String pageSize) throws Exception; //최신 피드 가져오기
	public ResponseEntity<FeedVO> showOneFeed(String boardNo, String page, String pageSize) throws Exception;
	public ResponseEntity<List<CommentFeedVO>> showCommentInBoard(@PathVariable("boardNo") String boardNo, @RequestParam String page, @RequestParam String pageSize) throws Exception;
		
	
}
