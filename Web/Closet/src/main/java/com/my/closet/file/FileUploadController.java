package com.my.closet.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {

	private static String CURR_IMAGE_REPO_PATH;

	// server.xml : <Context docBase="/home/ubuntu/repo/clothes_image/"
	// path="/Closet/repo/clothes_image/" reloadable="true"/>

	// 업로드 양식 보기 (웹)(윈도우/옷)
	@RequestMapping(value = "/upload/form")
	public String form() {
		return "uploadForm";
	}
	// 업로드 양식 보기 (웹)(리눅스/옷)
	@RequestMapping(value = "/upload/form2")
	public String form2() {
		return "uploadForm2";
	}

	@RequestMapping(value = "/upload/{object}", method = RequestMethod.POST)
	public ModelAndView upload(@PathVariable("object") String obj, MultipartHttpServletRequest multipartRequest,
			HttpServletResponse response) throws Exception {

		if (obj.equals("clothes"))
			CURR_IMAGE_REPO_PATH = "/home/ubuntu/repo/clothes_image/";
		else if (obj.equals("codi"))
			CURR_IMAGE_REPO_PATH = "/home/ubuntu/repo/codi_image/";
		else CURR_IMAGE_REPO_PATH = "C:\\repo\\clothes_image\\"; // 윈도우 테스트용 (옷)

		multipartRequest.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<?> enu = multipartRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			System.out.println(name + ", " + value);
			map.put(name, value);
		}

		List<?> fileList = fileProcess(multipartRequest);
		map.put("fileList", fileList);
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map);
		mav.setViewName("result");
		if(obj.equals("clothes"))
			mav.setViewName("result2");
		return mav;
	}

	private List<String> fileProcess(MultipartHttpServletRequest multipartRequest) throws Exception {
		List<String> fileList = new ArrayList<String>();
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			String originalFileName = mFile.getOriginalFilename();
			fileList.add(originalFileName);
			File file = new File(CURR_IMAGE_REPO_PATH + fileName);
			if (mFile.getSize() != 0) { // File Null Check
				if (!file.exists()) { // 경로에 파일이 없으면
					if (file.getParentFile().mkdirs()) { // 그 경로에 해당하는 디렉터리를 만든 후
						file.createNewFile(); // 파일을 생성
					}
				}
				mFile.transferTo(new File(CURR_IMAGE_REPO_PATH + originalFileName)); // 임시로 저장된 multipartFile을 실제 파일로 전송
			}
		}
		return fileList;
	}
}
