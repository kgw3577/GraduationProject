package com.my.closet.codi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.my.closet.codi.dao.CodiDAO;
import com.my.closet.codi.vo.CodiVO;
import com.my.closet.file.FileUploadController;
import com.my.closet.user.vo.LoginVO;


@Service("codiService")
@Transactional(propagation=Propagation.REQUIRED) //서비스 클래스의 모든 메서드에 트랜잭션을 적용
public class CodiServiceImpl implements CodiService {

	@Autowired
	private CodiDAO codiDAO;
	@Autowired
	private FileUploadController uploadCon;
	
	@Override
	public List<CodiVO> listAllCodi() throws DataAccessException {
		List<CodiVO> codiList = codiDAO.selectAllCodi();
		return codiList;
	}
	
	//내 코디 전부 조회
	@Override
	public List<CodiVO> myAllCodi(HttpSession session, CodiVO codiFilter) throws DataAccessException {
		LoginVO loginVO;
		try {
			loginVO = (LoginVO) session.getAttribute("login");
			System.out.println("세션에 저장된 userID : "+loginVO.getUserID());
		}catch(Exception e) {
			System.out.println("세션을 찾을 수 없음.");
			return null;
		}
		//페이지 정보만 담긴 VO에 세션으로부터 받아온 유저아이디 정보 묶음
		codiFilter.setUserID(loginVO.getUserID());

		return codiDAO.selectCodi(codiFilter);
	}

	//코디 정보 보기
	@Override
	public CodiVO infoCodi(String codiNo) throws DataAccessException {
		return codiDAO.selectThisCodi(codiNo);
	}

	//코디 찾기
	@Override
	public List<CodiVO> searchCodi(HttpSession session, CodiVO codiFilter) throws DataAccessException {
		LoginVO loginVO;
		try {
			loginVO = (LoginVO) session.getAttribute("login");
			System.out.println("세션에 저장된 userID : "+loginVO.getUserID());
		}catch(Exception e) {
			System.out.println("세션을 찾을 수 없음.");
			return null;
		}
		codiFilter.setUserID(loginVO.getUserID());

		return codiDAO.selectCodi(codiFilter);
	}

	@Override
	public String winAddCodi(MultipartHttpServletRequest multipartRequest) throws DataAccessException {
		
		//업로드 컨트롤러의 upload 함수로 사진 파일+파라미터 정보가 담긴 request를 한꺼번에 넘기고, 업로드 후에 해쉬맵으로 속성 정보를 받아온다.
		Map<String, Object> codiInfo = new HashMap<String, Object>();
		try {
			codiInfo=uploadCon.upload("windows", multipartRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("업로드 콘 실패");
			return "fail";
		}
		System.out.println("해쉬맵 생성 성공. 해쉬맵을 출력합니다.");
		for(Map.Entry<String, Object> elem : codiInfo.entrySet()){
			 
            String key = elem.getKey();
            Object value = elem.getValue();
 
            System.out.println(key+" : "+value);
 
        }
		
		//받아온 해쉬맵을 이용해 dao에 데이터베이스 추가를 요청한다.
		return codiDAO.addCodi(codiInfo);
		
	}
	
	@Override
	public String addCodi(MultipartHttpServletRequest multipartRequest) throws DataAccessException {
		
		//업로드 컨트롤러의 upload 함수로 사진 파일+파라미터 정보가 담긴 request를 한꺼번에 넘기고, 업로드 후에 해쉬맵으로 속성 정보를 받아온다.
		Map<String, Object> codiInfo = new HashMap<String, Object>();
		try {
			codiInfo=uploadCon.upload("codi", multipartRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("업로드 콘 실패");
		}
		System.out.println("해쉬맵 생성 성공. 해쉬맵을 출력합니다.");
		for(Map.Entry<String, Object> elem : codiInfo.entrySet()){
			 
            String key = elem.getKey();
            Object value = elem.getValue();
 
            System.out.println(key+" : "+value);
 
        }
		
		//받아온 해쉬맵을 이용해 dao에 데이터베이스 추가를 요청한다.
		return codiDAO.addCodi(codiInfo);
		
	}	
	

	@Override
	public String modifyCodi(CodiVO codiInfo) throws DataAccessException {
		return codiDAO.updateCodi(codiInfo);
	}

	@Override
	public String deleteCodi(String codiNo) throws DataAccessException {
		/*
		 여러 행 삭제 :
		 $param = "1,2,3,4,5";
		 delete from table where id in ($param);
		 
		 쿼리문에서 in 으로 처리
		 */
		//사진 삭제 처리도 해야 함.
		return codiDAO.deleteCodi(codiNo);
	}



}
