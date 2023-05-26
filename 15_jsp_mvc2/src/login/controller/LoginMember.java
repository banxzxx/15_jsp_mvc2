package login.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.dao.MemberDAO;
import login.dto.MemberDTO;

@WebServlet("/loginMember")
public class LoginMember extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = request.getRequestDispatcher("step2_loginEx/mLogin.jsp");
		dis.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberId(request.getParameter("memberId"));
		memberDTO.setPasswd(request.getParameter("passwd"));
		
		String jsScript = "";
		if (MemberDAO.getInstance().loginMember(memberDTO)) {
			HttpSession session = request.getSession();
			session.setAttribute("memberId", request.getParameter("memberId"));
			
			jsScript = "<script>";
			jsScript += "alert('로그인 되었습니다.');";
			jsScript += "location.href='detailMember';";
			jsScript += "</script>";
			
		}
		else {
			 jsScript = "<script>";
			 jsScript += "alert('아이디와 패스워드를 확인하세요.');";
			 jsScript += "history.go(-1);";
			 jsScript += "</script>";
		}
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);

	}

}
