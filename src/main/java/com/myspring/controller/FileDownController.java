package com.myspring.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/file/*")
public class FileDownController {

	// jsp파일에서 다운받을 파일명을 받아와 스트림으로 전송해 준다.
	
	@RequestMapping("fileDownload")
	public void fileDownload(HttpServletRequest req, HttpServletResponse res) {
		String filename = req.getParameter("fileName");
		String readFilename = "";
		System.out.println("filename: "+filename);
		
		
			try {
				String browser = req.getHeader("User-Agent");
				// 파일 인코딩
				if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")) {
				filename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
				
			} else {
					filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
			}
			readFilename = "I:\\upload\\" + filename;
			System.out.println("readFilename: "+ readFilename);
			File file = new File(readFilename);
			if(!file.exists()) {
				return ;
		}
		
		// 파일명 지정
		res.setContentType("application/octer-stream");
		res.setHeader("Content-Transfer-Encoding", "binary;");
		res.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		
		try {
			OutputStream os = res.getOutputStream();
			FileInputStream fis = new FileInputStream(readFilename);
			
			int ncount = 0;
			byte[] bytes = new byte[512];
			
			while((ncount = fis.read(bytes)) != -1) {
				os.write(bytes, 0, ncount);
			}
			fis.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
