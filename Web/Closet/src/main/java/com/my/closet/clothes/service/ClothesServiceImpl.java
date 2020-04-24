package com.my.closet.clothes.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.my.closet.clothes.dao.ClothesDAO;
import com.my.closet.clothes.vo.ClothesVO;
import com.my.closet.file.FileUploadController;

@Service("clothesService")
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

	@Override
	public ClothesVO infoClothes(String no) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteImage(String ImgFileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
