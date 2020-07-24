package com.my.closet.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FileDownloadController {
	private static String CURR_IMAGE_REPO_PATH;

	// private static String CURR_IMAGE_REPO_PATH = "C:\\repo\\clothes_image"; //윈도우
	// 테스트용
	// private static String CURR_IMAGE_REPO_PATH =
	// "/home/ubuntu/repo/clothes_image/";

	@RequestMapping("/download/{object}")
	protected void download(@PathVariable("object") String obj, @RequestParam("imageFileName") String imageFileName,
			HttpServletResponse response) throws Exception {
		
		
		switch(obj) {
		case "clothes":
			CURR_IMAGE_REPO_PATH = "/home/ubuntu/repo/clothes_image/";
			break;
		case "codi":
			CURR_IMAGE_REPO_PATH = "/home/ubuntu/repo/codi_image/";
			break;
		case "board":
			CURR_IMAGE_REPO_PATH = "/home/ubuntu/repo/board_image/";
			break;
		case "profile":
			CURR_IMAGE_REPO_PATH = "/home/ubuntu/repo/profile_image/";
			break;
		case "windows":
			CURR_IMAGE_REPO_PATH = "C:\\repo\\clothes_image\\"; // 윈도우 테스트용
			break;
		}
		
		

		OutputStream out = response.getOutputStream();
		String downFile = CURR_IMAGE_REPO_PATH + imageFileName; // "\\" +
		File file = new File(downFile); //파일 생성

		
		//파일 전송하기
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment; fileName=" + imageFileName);
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[1024 * 8];
		while (true) {
			int count = in.read(buffer); // 버퍼를 이용해 한 번에 8KB씩 브라우저로 전송
			if (count == -1)
				break;
			out.write(buffer, 0, count);
		}
		in.close();
		out.close();
	}

}
