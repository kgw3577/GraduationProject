package com.my.closet.codi.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.my.closet.codi.vo.CodiVO;

public interface CodiDAO {

	public List<CodiVO> selectAllCodi() throws DataAccessException; //모든 코디 선택
	public CodiVO selectThisCodi(String codiNo) throws DataAccessException; //codiNo로 코디 하나 선택
	public List<CodiVO> selectCodi(CodiVO codiFilter) throws DataAccessException; //조건으로 코디 선택
	
	public String addCodi(Map<String, Object> codiInfo) throws DataAccessException; //코디 추가 (예외적으로 해쉬맵으로 받음)
	public String updateCodi(CodiVO codiInfo) throws DataAccessException; //코디 정보 수정
	public String deleteCodi(String codiNo) throws DataAccessException; //코디 삭제 (1,2,3,4,5로 여러 개 가능) --주의

}
