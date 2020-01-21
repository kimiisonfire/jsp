package com.jsp.action.member;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.dto.MemberVO;
import com.jsp.service.MemberService;
import com.jsp.service.MemberServiceImpl;
import com.jsp.utils.GetUploadPath;

public class MemberRemoveAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = "member/removeSuccess";
		String id = request.getParameter("id");
		
		MemberService service = MemberServiceImpl.getInstance();
		
		
		MemberVO member;
		try {
			// 이미지 파일 삭제
			member = service.getMember(id);
			String fileName = member.getPicture();
			String savedPath = GetUploadPath.getUploadPath("member.picture.upload");
			
			File file = new File(savedPath + File.separator + fileName); 
			if (file.exists() && !(fileName.equals("noImage.jpg")) ) {
				file.delete();
			}
			
			// 삭제되는 회원이 로그인 회원인 경우 로그아웃해야함
			MemberVO loginUser = (MemberVO)request.getSession().getAttribute("loginUser");
			if (loginUser.getId().equals(member.getId())) {
				request.getSession().invalidate();
			}
			
			// 회원 삭제
			request.setAttribute("member", member);
			service.remove(id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return url;
	}

}
