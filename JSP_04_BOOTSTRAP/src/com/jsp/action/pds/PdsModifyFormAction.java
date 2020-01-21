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
import com.jsp.service.PdsService;
import com.jsp.service.PdsServiceImpl;
import com.jsp.utils.MakeFileName;

public class PdsModifyFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = "pds/modify";
		
		int pno = Integer.parseInt(request.getParameter("pno"));
		
		PdsVO pds = null; 
		
		try {
			pds = PdsServiceImpl.getInstance().getPds(pno);
			List<AttachVO> renamedAttachList = 
					MakeFileName.parseFileNameFromAttaches(pds.getAttachList(), "\\$\\$");
			pds.setAttachList(renamedAttachList);
			
			request.setAttribute("pds", pds);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return url;
	}

}
