package com.my.closet.clothes.service;

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

import com.my.closet.clothes.dao.ClothesDAO;
import com.my.closet.clothes.vo.ClothesVO;
import com.my.closet.file.FileUploadController;
import com.my.closet.user.vo.LoginVO;


@Service("clothesService")
@Transactional(propagation=Propagation.REQUIRED) //서비스 클래스의 모든 메서드에 트랜잭션을 적용
public class ClothesServiceImpl implements ClothesService {

	@Autowired
	private ClothesDAO clothesDAO;
	@Autowired
	private FileUploadController uploadCon;
	
	@Override
	public List<ClothesVO> listAllClothes() throws DataAccessException {
		List<ClothesVO> clothesList = clothesDAO.selectAllClothes();
		return clothesList;
	}
	
	//내 옷 전부 조회
	@Override
	public List<ClothesVO> myAllClothes(HttpSession session, ClothesVO clothesVO) throws DataAccessException {
		LoginVO loginVO;
		try {
			loginVO = (LoginVO) session.getAttribute("login");
			System.out.println("세션에 저장된 userID : "+loginVO.getId());
		}catch(Exception e) {
			System.out.println("세션을 찾을 수 없음.");
			return null;
		}
		clothesVO.setUserID(loginVO.getId());

		return clothesDAO.selectClothes(clothesVO);
	}

	@Override
	public ClothesVO infoClothes(String no) throws DataAccessException {
		return clothesDAO.selectThisClothes(no);
	}

	@Override
	public List<ClothesVO> searchClothes(ClothesVO clothesVO) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String winAddClothes(MultipartHttpServletRequest multipartRequest) throws DataAccessException {
		
		//업로드 컨트롤러의 upload 함수로 사진 파일+파라미터 정보가 담긴 request를 한꺼번에 넘기고, 업로드 후에 해쉬맵으로 속성 정보를 받아온다.
		Map<String, Object> clothesMap = new HashMap<String, Object>();
		try {
			clothesMap=uploadCon.upload("windows", multipartRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("업로드 콘 실패");
		}
		System.out.println("해쉬맵 생성 성공");
		for(Map.Entry<String, Object> elem : clothesMap.entrySet()){
			 
            String key = elem.getKey();
            Object value = elem.getValue();
 
            System.out.println(key+" : "+value);
 
        }
		
		//받아온 해쉬맵을 이용해 dao에 데이터베이스 추가를 요청한다.
		return clothesDAO.addClothes(clothesMap);
		
	}
	
	@Override
	public String addClothes(MultipartHttpServletRequest multipartRequest) throws DataAccessException {
		
		//업로드 컨트롤러의 upload 함수로 사진 파일+파라미터 정보가 담긴 request를 한꺼번에 넘기고, 업로드 후에 해쉬맵으로 속성 정보를 받아온다.
		Map<String, Object> clothesMap = new HashMap<String, Object>();
		try {
			clothesMap=uploadCon.upload("clothes", multipartRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("업로드 콘 실패");
		}
		System.out.println("해쉬맵 생성 성공");
		for(Map.Entry<String, Object> elem : clothesMap.entrySet()){
			 
            String key = elem.getKey();
            Object value = elem.getValue();
 
            System.out.println(key+" : "+value);
 
        }
		
		//받아온 해쉬맵을 이용해 dao에 데이터베이스 추가를 요청한다.
		return clothesDAO.addClothes(clothesMap);
		
	}
	
	

	@Override
	public String modifyClothes(ClothesVO clothesVO) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteClothes(String no) throws DataAccessException {
		/*
		 여러 행 삭제 :
		 $param = "1,2,3,4,5";
		 delete from table where id in ($param);
		 
		 쿼리문에서 in 으로 처리
		 */
		return clothesDAO.deleteClothes(no);
	}



}
