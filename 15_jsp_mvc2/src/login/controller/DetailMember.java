package login.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.dao.MemberDAO;

@WebServlet("/detailMember")
public class DetailMember extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		
		//객체 정보 들고오기
		request.setAttribute("memberDTO", MemberDAO.getInstance().getMemberDetail((String)session.getAttribute("memberId")));
		
		RequestDispatcher dis = request.getRequestDispatcher("step2_loginEx/mDetail.jsp");
		dis.forward(request, response);
		
	}


}
