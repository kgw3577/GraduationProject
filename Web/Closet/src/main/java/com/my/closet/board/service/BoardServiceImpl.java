package com.my.closet.board.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.my.closet.board.dao.BoardDAO;
import com.my.closet.board.vo.BoardVO;
import com.my.closet.file.FileUploadController;
import com.my.closet.user.vo.LoginVO;


@Service("boardService")
@Transactional(propagation=Propagation.REQUIRED) //서비스 클래스의 모든 메서드에 트랜잭션을 적용
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private FileUploadController uploadCon;
	
	
	
	
	//모든 게시글 리스트 조회. 웹 관리용.
	@Override
	public List<BoardVO> boardlist() throws DataAccessException {
		return boardDAO.boardlist();
	}
	
	
	//모든 게시글 리스트 조회.
	@Override
	public List<BoardVO> listAllBoard(String page, String pageSize) throws DataAccessException {
		
		//페이지 필터 생성
		BoardVO boardFilter = new BoardVO();
		if(!page.isEmpty()&&!pageSize.isEmpty()) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			boardFilter.setPageStart(pageInt*pageSizeInt);
			boardFilter.setPageSize(pageSizeInt);
		}
		List<BoardVO> boardList = boardDAO.selectAllBoard(boardFilter);
		return boardList;
	}
	//모든 옷 게시글 조회
	@Override
	public List<BoardVO> listAllBoard_Clothes(String page, String pageSize) throws DataAccessException {
		//페이지 필터 생성
		BoardVO boardFilter = new BoardVO();
		if(!page.isEmpty()&&!pageSize.isEmpty()) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			boardFilter.setPageStart(pageInt*pageSizeInt);
			boardFilter.setPageSize(pageSizeInt);
		}
		List<BoardVO> boardList = boardDAO.selectAllBoard_Clothes(boardFilter);
		return boardList;		
	}
	//모든 코디 게시글 조회
	@Override
	public List<BoardVO> listAllBoard_Codi(String page, String pageSize) throws DataAccessException {
		//페이지 필터 생성
		BoardVO boardFilter = new BoardVO();
		if(!page.isEmpty()&&!pageSize.isEmpty()) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			boardFilter.setPageStart(pageInt*pageSizeInt);
			boardFilter.setPageSize(pageSizeInt);
		}
		List<BoardVO> boardList = boardDAO.selectAllBoard_Codi(boardFilter);
		return boardList;
	}
	
	
	//내 게시글 전부 조회
	@Override
	public List<BoardVO> myAllBoard(HttpSession session, String page, String pageSize) throws DataAccessException {
		//세션으로부터 내 아이디 가져오기
		LoginVO loginVO;
		try {
			loginVO = (LoginVO) session.getAttribute("login");
			System.out.println("세션에 저장된 userID : "+loginVO.getId());
		}catch(Exception e) {
			System.out.println("세션을 찾을 수 없음.");
			return null;
		}
		//내 아이디로 필터 생성
		BoardVO boardFilter = new BoardVO();
		boardFilter.setUserID(loginVO.getId());
		//페이지 필터 적용
		if(!page.isEmpty()&&!pageSize.isEmpty()) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			boardFilter.setPageStart(pageInt*pageSizeInt);
			boardFilter.setPageSize(pageSizeInt);
		}
		//해당 필터로 검색
		return boardDAO.selectBoard(boardFilter);	
	}
	
	//특정 유저 게시글 전부 조회
	@Override
	public List<BoardVO> usersAllBoard(String userID, String page, String pageSize) throws DataAccessException {
		//해당 유저 아이디로 필터 생성
		BoardVO boardFilter = new BoardVO();
		boardFilter.setUserID(userID);
		//페이지 필터 적용
		if(!page.isEmpty()&&!pageSize.isEmpty()) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			boardFilter.setPageStart(pageInt*pageSizeInt);
			boardFilter.setPageSize(pageSizeInt);
		}		
		//해당 필터로 검색
		return boardDAO.selectBoard(boardFilter);	
	}
	
	//특정 게시글 조회
	@Override
	public BoardVO infoBoard(String boardNo) throws DataAccessException {
		return boardDAO.selectThisBoard(boardNo);
	}
	//특정 조건의 게시글 리스트 조회
	@Override
	public List<BoardVO> searchBoard(BoardVO boardFilter, String page, String pageSize) throws DataAccessException {
		//페이지 필터 적용
		if(!page.isEmpty()&&!pageSize.isEmpty()) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			boardFilter.setPageStart(pageInt*pageSizeInt);
			boardFilter.setPageSize(pageSizeInt);
		}	
		return boardDAO.selectBoard(boardFilter);
	}


	//게시글 추가
	@Override
	public String addBoard(MultipartHttpServletRequest multipartRequest) throws DataAccessException {
		//업로드 컨트롤러의 upload 함수로 사진 파일+파라미터 정보가 담긴 request를 한꺼번에 넘기고, 업로드 후에 해쉬맵으로 속성 정보를 받아온다.
		Map<String, Object> boardMap = new HashMap<String, Object>();
		try {
			boardMap=uploadCon.upload("board", multipartRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("업로드 콘 실패");
		}
		System.out.println("해쉬맵 생성 성공");
		for(Map.Entry<String, Object> elem : boardMap.entrySet()){
			 
            String key = elem.getKey();
            Object value = elem.getValue();
 
            System.out.println(key+" : "+value);
        }
		
		//받아온 해쉬맵을 이용해 dao에 데이터베이스 추가를 요청한다.
		return boardDAO.addBoard(boardMap);
	}
	//게시글 추가 (윈도우 테스트용)
	@Override
	public String winAddBoard(MultipartHttpServletRequest multipartRequest) throws DataAccessException {
		//업로드 컨트롤러의 upload 함수로 사진 파일+파라미터 정보가 담긴 request를 한꺼번에 넘기고, 업로드 후에 해쉬맵으로 속성 정보를 받아온다.
		Map<String, Object> boardMap = new HashMap<String, Object>();
		try {
			boardMap=uploadCon.upload("windows", multipartRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("업로드 콘 실패");
		}
		System.out.println("해쉬맵 생성 성공");
		for(Map.Entry<String, Object> elem : boardMap.entrySet()){
			 
            String key = elem.getKey();
            Object value = elem.getValue();
 
            System.out.println(key+" : "+value);
        }
		
		//받아온 해쉬맵을 이용해 dao에 데이터베이스 추가를 요청한다.
		return boardDAO.addBoard(boardMap);
	}

	@Override
	public String modifyBoard(BoardVO boardInfo) throws DataAccessException {
		return boardDAO.updateBoard(boardInfo);
	}

	@Override
	public String deleteBoard(String boardNo) throws DataAccessException {
		/*
		 여러 행 삭제 :
		 $param = "1,2,3,4,5";
		 delete from table where id in ($param);
		 
		 쿼리문에서 in 으로 처리
		 */
		//사진 삭제 처리도 해야 함.
		return boardDAO.deleteBoard(boardNo);
	}

	



}
