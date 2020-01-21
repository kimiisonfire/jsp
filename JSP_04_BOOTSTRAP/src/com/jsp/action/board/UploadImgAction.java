package com.jsp.action.board;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jsp.action.Action;
import com.jsp.utils.GetUploadPath;
import com.jsp.utils.MakeFileName;

public class UploadImgAction implements Action {

	// 업로드 파일 환경설정
	private static final int MEMORY_THRESHOLD = 1024 * 500; 	// 500KB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 5; 	// 5MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 2; // 2MB
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = null;
		
		// request 파일 첨부 여부 확인
		if (!ServletFileUpload.isMultipartContent(request)) {
			return null;
		}
		
		// 업로드를 위한 환경설정 적용
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 저장을 위한 threshold memory 적용 - memory에 얼마나 할당할건지
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// 임시 저장 위치 결정 - 파일을 읽어올때 임시로 저장하는 위치가 있음
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 업로드 파일의 크기 적용
		upload.setFileSizeMax(MAX_FILE_SIZE);
		// 업로드 request 크기 적용
		upload.setSizeMax(MAX_REQUEST_SIZE);
		
		// 실제 저장 경로 설정 : /resources/images
		// 사용자에게 넘겨줄 url = urlPath | 실제 저장되는 물리적경로 = uploadPath
/*		String savePath   = "/resources/images";
		String urlPath    = request.getContextPath() + savePath;
		String uploadPath = request.getServletContext().getRealPath(savePath);*/
		// 위의 경로처리는 톰캣 배포경로에 저장되는거니까 재시작하면 날아감
		// 날아가지않게 c: 안에 저장하도록했음다
		String uploadPath = GetUploadPath.getUploadPath("board.img");
		
		File file = new File(uploadPath);
		if (!file.mkdirs()) {
			System.out.println(uploadPath + "가 이미 존재하거나 실패했습니다.");
		}
		
		try {
			List<FileItem> formItems = upload.parseRequest(request);
			// 파일 개수 확인
			if (formItems != null && formItems.size() > 0) {
				for (FileItem item : formItems) {	// form items 반복하여 꺼내기
					if (!item.isFormField()) {		// 파일일 경우 false 출력
						String fileName = MakeFileName.toUUIDFileName(".jpg","");
						String filePath = uploadPath + File.separator + fileName;
						File storeFile = new File(filePath);
						
						// local HDD에 저장
						item.write(storeFile);
						
						PrintWriter out = response.getWriter();
						/*String outStr = urlPath + "/" + fileName;*/
						String outStr = request.getContextPath() + "/getImg.do?fileName="+fileName;
						out.print(outStr);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return url;
	}

}
