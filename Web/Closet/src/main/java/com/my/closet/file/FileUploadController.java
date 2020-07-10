package com.my.closet.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.my.closet.user.vo.LoginVO;

@Controller
public class FileUploadController {
	
	private static String CURR_IMAGE_REPO_PATH;

	// server.xml : <Context docBase="/home/ubuntu/repo/clothes_image/"
	// path="/Closet/repo/clothes_image/" reloadable="true"/>

	// 업로드 양식 보기 (웹)(윈도우/옷)
	@RequestMapping(value = "/upload/winform")
	public String winform() {
		return "windowsUploadForm";
	}
	// 업로드 양식 보기 (웹)(리눅스/옷)
	@RequestMapping(value = "/upload/awsform")
	public String awsform() {
		return "awsUploadForm";
	}

	@RequestMapping(value = "/upload/{object}", method = RequestMethod.POST)
	public ModelAndView upload(@PathVariable("object") String obj, MultipartHttpServletRequest multipartRequest,
			HttpServletResponse response) throws Exception {
		String userID="";
		
		if (obj.equals("clothes"))
			CURR_IMAGE_REPO_PATH = "/home/ubuntu/repo/clothes_image/";
		else if (obj.equals("codi"))
			CURR_IMAGE_REPO_PATH = "/home/ubuntu/repo/codi_image/";
		else if(obj.equals("windows"))
			CURR_IMAGE_REPO_PATH = "C:\\repo\\clothes_image\\"; // 윈도우 테스트용 (옷)

		multipartRequest.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<?> enu = multipartRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			System.out.println(name + ", " + value);
			map.put(name, value);
		}


		//세션으로부터 유저아이디 받아오기
		HttpSession httpSession = multipartRequest.getSession(false);
		if(httpSession ==null) {
			System.out.println("세션 정보 없음");
			userID = "a";
		}
		else {
			LoginVO loginVO = (LoginVO) httpSession.getAttribute("login");
			userID = loginVO.getId();
			System.out.println("userID : "+userID);
		}
		map.put("userID",userID);
		
		String new_name = userID+"_"+System.currentTimeMillis() + ".jpg"; //사용자명과 현재 시간으로 파일이름 만들기
		List<?> fileList = fileProcess(new_name, multipartRequest); //파일 저장, 원본파일 이름 리스트 받아옴. String.
		
		
		map.put("userID",userID);
		map.put("fileList", fileList);
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map);
		mav.setViewName("windowsUploadResult");
		if(obj.equals("clothes")||obj.equals("clothes"))
			mav.setViewName("awsUploadResult");
		return mav;
	}
	
	
	//service 클래스 내부에서 쓸 함수.
	public Map<String, Object> upload(String obj, MultipartHttpServletRequest multipartRequest) throws Exception {

		String userID;
		
		if (obj.equals("clothes"))
			CURR_IMAGE_REPO_PATH = "/home/ubuntu/repo/clothes_image/";
		else if (obj.equals("codi"))
			CURR_IMAGE_REPO_PATH = "/home/ubuntu/repo/codi_image/";
		else if (obj.equals("board"))
			CURR_IMAGE_REPO_PATH = "/home/ubuntu/repo/board_image/";
		else if(obj.equals("windows"))
			CURR_IMAGE_REPO_PATH = "C:\\repo\\clothes_image\\"; // 윈도우 테스트용 (옷)

		multipartRequest.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		
		Enumeration<?> enu = multipartRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			System.out.println(name + ", " + value);
			map.put(name, value);
		}

		//세션으로부터 유저아이디 받아오기
		HttpSession httpSession = multipartRequest.getSession(false);
		if(httpSession ==null) {
			System.out.println("세션 정보 없음");
			userID = "a";
		}
		else {
			LoginVO loginVO = (LoginVO) httpSession.getAttribute("login");
			userID = loginVO.getId();
			System.out.println("userID : "+userID);
			
		}
		
		
		String new_name = userID+"_"+System.currentTimeMillis() + ".jpg"; //사용자명과 현재 시간으로 파일이름 만들기
		List<?> fileList = fileProcess(new_name, multipartRequest); //파일 저장, 원본파일 이름 리스트 받아옴. String.
		
		String filePath=null;
		
		if(obj.equals("clothes"))
			filePath = "/download/clothes?imageFileName="+new_name;
		if(obj.equals("codi"))
			filePath = "/download/codi?imageFileName="+new_name;
		if(obj.equals("board"))
			filePath = "/download/board?imageFileName="+new_name;
		else if(obj.equals("windows"))
			filePath = "/download/windows?imageFileName="+new_name;

		map.put("userID",userID);
		map.put("fileName", new_name);
		map.put("filePath", filePath);
		
		return map;  //옷 정보가 담긴 해쉬맵을 반환함
	}

	private List<String> fileProcess(String new_name, MultipartHttpServletRequest multipartRequest) throws Exception {
		List<String> fileList = new ArrayList<String>();
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			String originalFileName = mFile.getOriginalFilename();
			fileList.add(originalFileName);
			File file = new File(CURR_IMAGE_REPO_PATH + new_name);
			if (mFile.getSize() != 0) { // File Null Check
				if (!file.exists()) { // 경로에 파일이 없으면
					if (file.getParentFile().mkdirs()) { // 그 경로에 해당하는 디렉터리를 만든 후
						file.createNewFile(); // 파일을 생성
					}
				}
				mFile.transferTo(new File(CURR_IMAGE_REPO_PATH + new_name)); // 임시로 저장된 multipartFile을 실제 파일로 전송
			}
		}
		return fileList;
	}
	
	private String fileProcess(MultipartFile mFile) throws Exception {
		String originalFileName = mFile.getOriginalFilename();
		String fileName = mFile.getName(); //수정
	
		File file = new File(CURR_IMAGE_REPO_PATH + fileName);
		if (mFile.getSize() != 0) { // File Null Check
			if (!file.exists()) { // 경로에 파일이 없으면
				if (file.getParentFile().mkdirs()) { // 그 경로에 해당하는 디렉터리를 만든 후
					file.createNewFile(); // 파일을 생성
				}
			}
			mFile.transferTo(new File(CURR_IMAGE_REPO_PATH + originalFileName)); // 임시로 저장된 multipartFile을 실제 파일로 전송
		}
		return originalFileName;
	}
	
	
}
