package com.my.closet.clothes.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.my.closet.clothes.vo.ClothesVO;

public interface ClothesService {
	
	public List<ClothesVO> listAllClothes() throws DataAccessException; //모든 옷의 옷 정보 리스트 조회
	public ClothesVO infoClothes(String no) throws DataAccessException; //특정 옷의 옷 정보 조회 -- int? String?
	public List<ClothesVO> searchClothes(ClothesVO clothesVO) throws DataAccessException; //특정 조건의 옷 정보 리스트 조회
	
	public String winAddClothes(MultipartHttpServletRequest multipartRequest) throws DataAccessException;
	public String addClothes(MultipartHttpServletRequest multipartRequest) throws DataAccessException; //옷 추가
	//upload controller 포함시켜서? upload()실행하고, hashmap 파일 받아서 그걸로 dao 하기.
	public String modifyClothes(ClothesVO clothesVO) throws DataAccessException; //옷 정보 수정
	//사진을 변경할 경우의 수를 생각해야 함.
	public String deleteClothes(String no) throws DataAccessException; //옷 삭제 (1,2,3,4,5로 여러 개 가능) --주의
	
	public String deleteImage(String ImgFileName); //옷 사진 삭제 로직
	//db 삭제시 이미지도 같이 삭제 해야 함. 이것도 따로 클래스 만드는 게 좋을 수도.
}
