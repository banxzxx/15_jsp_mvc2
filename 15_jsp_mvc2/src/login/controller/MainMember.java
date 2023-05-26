package login.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mainMember")
public class MainMember extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dis = request.getRequestDispatcher("step2_loginEx/mMain.jsp");
		dis.forward(request, response);
	
	}
	
	
	// 여기서 아이디 중복확인 : servlet -> dao -> js
	
	
	/* 전체 순서 : MainMember.java -> mMain.jsp -> 회원가입 RegisterMember.java -> mRegister.jsp (CheckDuplicateId 들림) -> RegisterMember.java (post) 회원등록
	 											-> 로그인 LoginMember.java -> mLogin.jsp -> LoginMember.java (post) 아이디 비밀번호 확인)
	 			
	 			   로그인이 된 경우 : DetailMember.java -> mDetail.jsp 	-> 내 정보 수정 UpdateMember.java -> mUpdate.jsp -> 수정하기 UpdateMember.java (post)
	 			   														-> 로그아웃 LogoutMember.java
	 			   														-> 회원탈퇴 DeleteMember.java
	 			   
	  
	 
	 */
	
}
