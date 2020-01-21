package com.jsp.action.member;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.action.Action;
import com.jsp.dto.MemberVO;
import com.jsp.service.MemberService;
import com.jsp.service.MemberServiceImpl;
import com.jsp.utils.GetUploadPath;

public class MemberModifyAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = "redirect:member/detail.do?id=" + request.getParameter("id");
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String email = request.getParameter("email");
		String picture = request.getParameter("picture");
		String oldPicture = request.getParameter("oldPicture");
		String authority = request.getParameter("authority");
		String phone = request.getParameter("phone").replace("-", "");
		
		// 사진 변경 시 이전 사진 지우기
		if (picture.isEmpty()) { // 사용자가 사진을 변경하지 않았을 경우
			picture = oldPicture;
		} else if (!picture.equals(oldPicture)) { // 사용자가 사진을 변경했을 경우
			String savePath = GetUploadPath.getUploadPath("member.picture.upload");
			File target = new File(savePath + File.separator + oldPicture);
			if (target.exists()  && !(oldPicture.equals("noImage.jpg")) ) {
				target.delete();
			}
		}
		
		MemberVO member = new MemberVO();
		member.setEmail(email);
		member.setId(id);
		member.setPhone(phone);
		member.setPwd(pwd);
		member.setPicture(picture);
		member.setAuthority(authority);
		
		MemberService service = MemberServiceImpl.getInstance();
		
		try {
			// 수정된 정보로 member 테이블 수정
			service.modify(member);
			
			// 현재 로그인 세션의 정보를 바꾸기위해
			// 수정된 member의 id와 현재 로그인중인 loginuser의 id가 같으면 세션 attr 변경
			HttpSession session = request.getSession();
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			
			if (member.getId().equals(loginUser.getId())) {
				// 위에서 만들었던 member에는 없는 정보(enabled)가 있어서 새로받아와서 넣어줌 
				member = service.getMember(id);
				session.setAttribute("loginUser", member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return url;
	}

}
