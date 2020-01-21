package com.jsp.action.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.utils.GetUploadPath;
import com.jsp.utils.MakeFileName;

public class GetImgAction implements Action {
	
	// img를 날려주는 클래스
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = null;
		
		// 파일명
		String fileName = request.getParameter("fileName");
		// 실제 저장 경로
		String savePath = GetUploadPath.getUploadPath("board.img");
		// 이 경로에서 파일 가져올 예정
		String filePath = savePath + File.separator + fileName;
		
		// 해당 경로에 있는 파일을 stream으로 가져오기
		File sendFile = new File(filePath);
		FileInputStream inStream = new FileInputStream(sendFile);
		
		
		// -- 내보내기위해 response 설정 --
		
		ServletContext context = request.getServletContext();
		// 파일 포맷으로 mime 결정
		String mimeType = context.getMimeType(filePath);
		
		// response 수정
		response.setContentType(mimeType);
		response.setContentLength((int) sendFile.length());
		
		// utf로 온 파일명을 iso로 바꿈. 들어온 파일명이 한글일수도 있으니까 encoding
		String downloadFileName =
				new String(sendFile.getName().getBytes("utf-8"), "ISO-8859-1");
		
		downloadFileName =
				MakeFileName.parseFileNameFromUUID(downloadFileName, "\\$\\$");
		
		// header setting~
		String headerKey = "Content-Disposition";
		String headerValue =
				String.format("attachment; filename=\'%s\'", downloadFileName);
		response.setHeader(headerKey, headerValue);
		
		// 파일 내보내기
		OutputStream outStream = response.getOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		
		while ( (bytesRead = inStream.read(buffer)) != -1 ) {
			outStream.write(buffer, 0, bytesRead);
		}
		
		inStream.close();
		outStream.close();
		
		return url;
	}

}
