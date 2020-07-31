package com.my.closet.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.my.closet.board.vo.BoardVO;
import com.my.closet.social.vo.CommentFeedVO;
import com.my.closet.social.vo.FeedVO;

public interface BoardDAO {

	public List<BoardVO> boardlist() throws DataAccessException; //모든 게시판
	public List<BoardVO> selectAllBoard(BoardVO boardFilter) throws DataAccessException; //모든 게시판

	public BoardVO selectThisBoard(String boardNo) throws DataAccessException; //boardNo로 코디 하나 선택
	public List<BoardVO> selectBoard(BoardVO boardFilter) throws DataAccessException; //조건으로 코디 선택
	
	public String addBoard(Map<String, Object> boardMap) throws DataAccessException; //코디 추가 (예외적으로 해쉬맵으로 받음)
	public String updateBoard(BoardVO boardInfo) throws DataAccessException; //코디 정보 수정
	public String deleteBoard(String boardNo) throws DataAccessException; //코디 삭제 (1,2,3,4,5로 여러 개 가능) --주의

	public List<FeedVO> showAllFeed(BoardVO boardFilter) throws DataAccessException; //피드 가져오기
	public FeedVO showOneFeed(BoardVO boardFilter) throws DataAccessException;
	public List<CommentFeedVO> showCommentInBoard(BoardVO boardFilter) throws DataAccessException; //해당 게시글 댓글 가져오기
}
