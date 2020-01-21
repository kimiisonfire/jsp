package com.jsp.action.pds;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.dto.AttachVO;
import com.jsp.dto.PdsVO;
import com.jsp.service.PdsServiceImpl;
import com.jsp.utils.MakeFileName;

public class PdsDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = "pds/detail";
		
		int pno = Integer.parseInt(request.getParameter("pno"));
		
		// UUID + $$ + 파일명 --> 파일명으로 복구
		try {
			PdsVO pds = PdsServiceImpl.getInstance().read(pno);
			List<AttachVO> renameAttachList = 
					MakeFileName.parseFileNameFromAttaches(pds.getAttachList(), "\\$\\$");
			pds.setAttachList(renameAttachList);
			request.setAttribute("pds", pds);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return url;
	}

}
