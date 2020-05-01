package com.my.closet.closet.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.my.closet.closet.vo.ClosetVO;

public interface ClosetDAO {
	public List<ClosetVO> selectAllCloset() throws DataAccessException; //모든 옷장 선택
	public ClosetVO selectCloset(String userID, String closetName) throws DataAccessException; //옷장 선택
	public String insertCloset(ClosetVO closetVO) throws DataAccessException; //옷장 추가
	public String updateCloset(ClosetVO closetVO) throws DataAccessException; //옷장 정보 수정
	public String deleteCloset(String userID, String closetName) throws DataAccessException; //옷장 삭제
}
