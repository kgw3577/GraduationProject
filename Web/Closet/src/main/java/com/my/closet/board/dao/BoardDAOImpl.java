package com.my.closet.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.my.closet.board.vo.BoardVO;

@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<BoardVO> selectAllBoard() throws DataAccessException {
		//빈 필터를 보냄으로써 모든 게시판 조회
		List<BoardVO> boardlist = sqlSession.selectList("mapper.board.searchBoard", new BoardVO());
		return boardlist;
	}
	@Override
	public List<BoardVO> selectAllBoard_Clothes() throws DataAccessException {
		//게시판 타입 '옷' 필터 생성
		BoardVO boardFilter = new BoardVO();
		boardFilter.setBoardType("clothes");
		
		//필터로 서치
		List<BoardVO> boardlist = sqlSession.selectList("mapper.board.searchBoard", boardFilter);
		return boardlist;
	}

	@Override
	public List<BoardVO> selectAllBoard_Codi() throws DataAccessException {
		//게시판 타입 '옷' 필터 생성
		BoardVO boardFilter = new BoardVO();
		boardFilter.setBoardType("codi");
		
		//필터로 서치
		List<BoardVO> boardlist = sqlSession.selectList("mapper.board.searchBoard", boardFilter);
		return boardlist;
	}

	
	
	
	
	@Override
	public BoardVO selectThisBoard(String boardNo) throws DataAccessException {
		//고유 번호 필터 생성
		BoardVO boardFilter = new BoardVO();
		boardFilter.setBoardNo(Integer.parseInt(boardNo));
		//필터로 서치
		BoardVO board = sqlSession.selectOne("mapper.board.searchBoard",boardFilter);
		return board;
	}

	@Override
	public List<BoardVO> selectBoard(BoardVO boardFilter) throws DataAccessException {
		//필터로 서치
		List<BoardVO> boardList = sqlSession.selectList("mapper.board.searchBoard", boardFilter);
		return boardList;
	}

	@Override
	public String addBoard(Map<String, Object> boardMap) throws DataAccessException {

		System.out.println("insert 쿼리 실행 전");
		int result = sqlSession.insert("mapper.board.insertBoard", boardMap);
		System.out.println("insert 쿼리 실행");
		
		if (result == 1) {
			System.out.println("쿼리 성공");
			return "ok"; //insert 성공
		}
		else {
			System.out.println("쿼리 실패");
			return "fail"; //insert 실패
		}			
	}

	@Override
	public String updateBoard(BoardVO boardInfo) throws DataAccessException {
		int result = sqlSession.update("mapper.board.updateBoard",boardInfo);
		
		if (result==1)
			return "ok"; //update 성공
		else
			return "fail"; //update 실패
	}

	@Override
	public String deleteBoard(String boardNo) throws DataAccessException {
		int result = sqlSession.delete("mapper.board.deleteBoard",boardNo);
		
		if (result==1)
			return "ok";
		else
			return "fail";
	}


}
