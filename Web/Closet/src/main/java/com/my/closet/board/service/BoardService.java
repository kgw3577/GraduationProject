package com.my.closet.board.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.my.closet.board.vo.BoardVO;

public interface BoardService {
	
	public List<BoardVO> boardlist() throws DataAccessException; //모든 게시글 리스트 조회. 웹 관리용.
	
	public List<BoardVO> listAllBoard(String page, String pageSize) throws DataAccessException; //모든 게시글 리스트 조회
	public List<BoardVO> listAllBoard_Clothes(String page, String pageSize) throws DataAccessException; //모든 옷 게시글 조회
	public List<BoardVO> listAllBoard_Codi(String page, String pageSize) throws DataAccessException; //모든 코디 게시글 조회
	
	public List<BoardVO> myAllBoard(HttpSession session, String page, String pageSize) throws DataAccessException; //내 게시글 전부 조회
	public List<BoardVO> usersAllBoard(String userID, String page, String pageSize) throws DataAccessException; //특정 유저 게시글 전부 조회
	
	public BoardVO infoBoard(String boardNo) throws DataAccessException; //특정 게시글 조회
	public List<BoardVO> searchBoard(BoardVO boardFilter, String page, String pageSize) throws DataAccessException; //특정 조건의 게시글 리스트 조회
	
	public String winAddBoard(MultipartHttpServletRequest multipartRequest) throws DataAccessException;
	public String addBoard(MultipartHttpServletRequest multipartRequest) throws DataAccessException; //게시글 추가
	
	public String modifyBoard(BoardVO boardInfo) throws DataAccessException; //게시글 수정
	public String deleteBoard(String boardNo) throws DataAccessException; //게시글 삭제 (1,2,3,4,5로 여러 개 가능) --주의
	
}
