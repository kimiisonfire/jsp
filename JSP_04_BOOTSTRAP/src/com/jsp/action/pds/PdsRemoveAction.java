package com.jsp.action.pds;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.dto.AttachVO;
import com.jsp.service.PdsService;
import com.jsp.service.PdsServiceImpl;

public class PdsRemoveAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = "pds/removeSuccess";
		
		int pno = Integer.parseInt(request.getParameter("pno"));
		
		PdsService service = PdsServiceImpl.getInstance();
		
		// pno에 해당하는 attachList 확보 후 파일 삭제 + DB delete
		List<AttachVO> attachList = null;
		try {
			attachList = service.getPds(pno).getAttachList();
			
			// 각 attachList를 이용하여 파일 삭제
			for (AttachVO attach : attachList) {
				String storedFilePath = attach.getUploadPath() + File.separator
						+ attach.getFileName();
				File file = new File(storedFilePath);
				if (file.exists()) {
					file.delete();
				}
			}
			
			// DB 내용 삭제
			service.remove(pno);  	// pds 삭제 시 cascade로 attach 삭제
			
		} catch (SQLException e) {
			url = "error/500";
			e.printStackTrace();
		}
		
		return url;
	}

}
