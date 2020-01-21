package com.jsp.action.pds;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jsp.action.Action;
import com.jsp.dao.AttachDAO;
import com.jsp.dao.AttachDAOImpl;
import com.jsp.dto.AttachVO;
import com.jsp.dto.PdsVO;
import com.jsp.service.PdsService;
import com.jsp.service.PdsServiceImpl;
import com.jsp.utils.GetUploadPath;
import com.jsp.utils.MakeFileName;

public class PdsModifyAction implements Action {

	private PdsService service = PdsServiceImpl.getInstance();

	// 업로드 파일 환경 설정
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url;

		// request 파일 첨부 여부 확인.
		if (!ServletFileUpload.isMultipartContent(request)) {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('등록에 실패했습니다.');");
			out.println("</script>");
			out.flush();
			out.close();
			return null;
		}

		// 업로드를 위한 upload 환경설정 적용.
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 저장을 위한 threshold memory 적용.
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// 임시 저장 위치 결정.
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload upload = new ServletFileUpload(factory);

		// 업로드 파일의 크기 적용.
		upload.setFileSizeMax(MAX_FILE_SIZE);

		// 업로드 request 크기 적용.
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// 실제 저장 경로를 설정.
		String uploadPath = GetUploadPath.getUploadPath("pds.upload");

		File file = new File(uploadPath);
		if (!file.mkdirs()) {
			System.out.println(uploadPath + "가 이미 존재하거나 생성을 실패했습니다.");
		}

		int pno = -1;
		String title = null;
		String content = null;
		String writer = null;

		PdsVO pds = new PdsVO();
		List<AttachVO> attachList = new ArrayList<AttachVO>();

		// request로 부터 받는 파일을 추출해서 저장.
		try {
			List<FileItem> formItems = upload.parseRequest(request);

			System.out.println(" 111   reqSize = " + formItems.get(0).getName());
			
			// 파일 개수 확인.
			if (formItems != null && formItems.size() > 0) {
				
				List<String> deleteFile = new ArrayList<String>();

				for (FileItem item : formItems) {
					
					System.out.println(" 222     -  "+item.getName());
					
					if (!item.isFormField()) { // 파일가져오기
						
						//summernote의 files 를 제외함.
						if(!item.getFieldName().equals("uploadFile")) continue;
						
						String fileName = new File(item.getName()).getName();
						fileName = MakeFileName.toUUIDFileName(fileName, "$$");
						String filePath = uploadPath + File.separator
								+ fileName;
						File storeFile = new File(filePath);

						// local HDD 에 저장.
						item.write(storeFile);

						// DB에 저장할 attach에 file 내용 추가.
						AttachVO attach = new AttachVO();
						attach.setFileName(fileName);
						attach.setUploadPath(uploadPath);
						attach.setFileType(fileName.substring(fileName
								.lastIndexOf(".") + 1));

						attachList.add(attach);

						System.out.println("upload file : " + attach);

						request.setAttribute("message", "업로드 되었습니다.");
					} else { // parameter 가져오기
						switch (item.getFieldName()) {
						case "title":
							title = item.getString("utf-8");
							break;
						case "writer":
							writer = item.getString("utf-8");
							break;
						case "content":
							content = item.getString("utf-8");
							break;
						case "pno":
							pno = Integer.parseInt(item.getString("utf-8"));
							break;
						case "deleteFile":
							int ano = Integer.parseInt(item.getString("utf-8"));
							AttachDAO attachDAO = new AttachDAOImpl();
							AttachVO attach=attachDAO.selectAttachByAno(ano);
							
							String filePath = attach.getUploadPath() 
									+ File.separator + attach.getFileName();
							File targetFile = new File(filePath);
							
							targetFile.delete();
							
							attachDAO.deleteAttach(ano);
							
							deleteFile.add(item.getString("utf-8"));
							
							break;
						}
					}
				}
				
				pds.setTitle(title);
				pds.setContent(content);
				pds.setWriter(writer);
				pds.setPno(pno);
				pds.setAttachList(attachList);
				
				service.modify(pds);
				
				System.out.println("receive pds : \n" + pds);
				System.out.println("deleteFile : \n" + deleteFile);
			}
			url="redirect:pds/detail.do?pno=" + pno;
		} catch (Exception e) {
			e.printStackTrace();
			url="error/500";
		}

		return url;
	}

}
