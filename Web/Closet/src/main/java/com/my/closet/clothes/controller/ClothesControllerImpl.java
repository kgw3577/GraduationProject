package com.my.closet.clothes.controller;

import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.clothes.dao.ClothesDAO;
import com.my.closet.clothes.service.ClothesService;
import com.my.closet.clothes.vo.ClothesVO;
import com.my.closet.user.controller.UserControllerImpl;
import com.my.closet.user.vo.UserVO;



//3. 500 에러 원인 해명
//4. 파일 삭제 유틸
//5. 한 번에 여러 개 삭제
//7. 하위 레코드가 남아 있으면 상위 레코드를 지울 수 없는 문제 해결
//8. 옷 정보 수정하기 구현


@RestController("clothesController")
@RequestMapping("/clothes")
public class ClothesControllerImpl implements ClothesController {

	//Logger 클래스 객체 가져오기
	private static final Logger logger = LoggerFactory.getLogger(ClothesControllerImpl.class);
	
	
	@Autowired
	private ClothesService clothesService;
	@Autowired
	private ClothesDAO clothesDAO;
	@Autowired
	ClothesVO clothesVO;
	
	//옷 추가 테스트용 폼(웹. 관리용)
	@RequestMapping(value = "/addform")
	public ModelAndView addTestform() {
		return new ModelAndView("clothes/addTestUploadForm");
	}
	
	//전체 옷 조회 (웹. 관리용)
	@Override
	@RequestMapping(value = "/clolist", method = RequestMethod.GET)
	public ModelAndView clotheslist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(getViewName(request));
		List<ClothesVO> clothesList = clothesService.listAllClothes();
		mav.addObject("clothesList", clothesList);
		return mav;
	}
	
	//내 옷 전부 조회
	@Override
	@RequestMapping(value = "/share", method = RequestMethod.GET)
	public ResponseEntity<List<ClothesVO>> myAllClothes(@RequestParam String userID, @RequestParam String page, @RequestParam String pageSize) throws Exception{
		List<ClothesVO> myclolist;
		try{
			ClothesVO cloVO = new ClothesVO();
			if(!page.isEmpty()&&!pageSize.isEmpty()) {
				int pageInt = Integer.parseInt(page);
				int pageSizeInt = Integer.parseInt(pageSize);
				cloVO.setPageStart(pageInt*pageSizeInt);
				cloVO.setPageSize(pageSizeInt);
			}
			myclolist = clothesService.myAllClothes(userID, cloVO);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<ClothesVO>>(Collections.<ClothesVO>emptyList(), HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<ClothesVO>>(myclolist, HttpStatus.OK);
	}
	
	//옷 정보 보기
	@Override
	@RequestMapping(value = "/info/{no}", method = RequestMethod.GET)
	public ResponseEntity<ClothesVO> infoClothes(@PathVariable("no") String no) throws Exception {
		ClothesVO clothesInfo;
		try {
			clothesInfo = clothesService.infoClothes(no);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ClothesVO>(new ClothesVO(), HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<ClothesVO>(clothesInfo, HttpStatus.OK);
	}

	//옷 찾기
	@Override
	@RequestMapping(value = "/search", method = RequestMethod.PUT)
	public ResponseEntity<List<ClothesVO>> searchClothes(@RequestBody ClothesVO clothesVO, @RequestParam String userID,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "pageSize", required = false) String pageSize) throws Exception {
		List<ClothesVO> searched_clolist;
		try{
			if(page!=null&&pageSize!=null&&!page.isEmpty()&&!pageSize.isEmpty()) {
				int pageInt = Integer.parseInt(page);
				int pageSizeInt = Integer.parseInt(pageSize);
				clothesVO.setPageStart(pageInt*pageSizeInt);
				clothesVO.setPageSize(pageSizeInt);
			}
			searched_clolist = clothesService.searchClothes(userID, clothesVO);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<ClothesVO>>(Collections.<ClothesVO>emptyList(), HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<ClothesVO>>(searched_clolist, HttpStatus.OK);
	}
	
	//옷 찾기
	@Override
	@RequestMapping(value = "/search/by_list", method = RequestMethod.PUT)
	public ResponseEntity<List<ClothesVO>> searchClothesByList(
			@RequestBody HashMap map,
			@RequestParam String userID, 
			@RequestParam String mode,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "pageSize", required = false) String pageSize) throws Exception {
		List<ClothesVO> searched_clolist;
		HashMap keywordMap = new HashMap();
		
		List<String> strArray = (List<String>) map.get("list");
		
		//String decodeResult = URLDecoder.decode(list, "UTF-8");
		//String[] strArray = decodeResult.split(",");
		
		if(map.containsKey("kind"))
			keywordMap.put("kind",map.get("kind"));
		keywordMap.put("userID",userID);
		keywordMap.put("location","private");
		keywordMap.put("mode", mode); //조건 칼럼(ex detailCategory), 그 리스트
		keywordMap.put(mode, strArray);
		
		if(page!=null&&pageSize!=null&&!page.isEmpty()&&!pageSize.isEmpty()) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			keywordMap.put("pageStart",pageInt*pageSizeInt);
			keywordMap.put("pageSize",pageSizeInt);
		}else {
			keywordMap.put("pageStart",-1);
			keywordMap.put("pageSize",-1);
		}
		System.out.println("////////////"+userID+mode+strArray);
		
		for(String str : strArray) {
			System.out.println(str);
		}
		
		
		try{
			searched_clolist = clothesDAO.selectClothesByList(keywordMap);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<ClothesVO>>(Collections.<ClothesVO>emptyList(), HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<List<ClothesVO>>(searched_clolist, HttpStatus.OK);
	}	

	//옷 추가하기
	@Override
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers="Content-Type=multipart/form-data")
	public ResponseEntity<String> addClothes(MultipartHttpServletRequest multipartRequest,
			@RequestPart(value = "file", required = false) MultipartFile multipartFile) throws Exception{
		
		logger.debug("#create: multipartFile({})", multipartFile);
		//System.out.println(multipartFile.getName()); //윈도우 테스트시 x
		
		String answer = null;
		try {
			answer = clothesService.addClothes(multipartRequest);
			//윈도우 시험용 : winAddClothes
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<String>(answer, HttpStatus.OK);
	}

	
	//데이터로 옷 추가
	@Override
	@RequestMapping(value = "/add/data", method = RequestMethod.POST)
	public ResponseEntity<String> addClothesFrData(@RequestBody ClothesVO cloInfo) throws Exception {
		//@RequestBody : 전송된 파라미터를 UserVO 해당 속성에 자동으로 설정 (JSON을 VO로 자동 변환)
		
		String answer = null;
		try {
			answer = clothesDAO.addClothesData(cloInfo);			
		} catch (Exception e) {
			return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<String>(answer, HttpStatus.OK);
	}	
	
	
	
	
	//옷 정보 수정
	@Override
	@RequestMapping(value = "/modify", method = RequestMethod.PUT)
	public ResponseEntity<String> modifyClothes(@RequestBody ClothesVO clothesInfo) throws Exception {
		System.out.println("받아온 옷 정보 : "+clothesInfo.getCloNo()+clothesInfo.getFavorite());
		
		String answer = null;
		try {
			answer = clothesService.modifyClothes(clothesInfo);
		} catch (Exception e) {
			return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<String>(answer, HttpStatus.OK);
	}

	//옷 삭제
	@Override
	@RequestMapping(value = "/delete/{cloNo}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteClothes(@PathVariable("cloNo") String cloNo) throws Exception {
		String answer = null;
		try {
			answer= clothesService.deleteClothes(cloNo);
			System.out.println("컨트롤러에서 받아온 응답 : "+answer);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(answer, HttpStatus.SERVICE_UNAVAILABLE);
		}
		// TODO Auto-generated method stub
		return new ResponseEntity<String>(answer, HttpStatus.OK);
	}
	
	private String getViewName(HttpServletRequest request) throws Exception {
		// 요청명 구하는 함수. 첫번째 요청명까지 포함. /user/join.do면 -> user/join만 추출

		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String viewName = uri.substring(begin, end);
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
		}
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
		}
		return viewName;
	}
	
}
