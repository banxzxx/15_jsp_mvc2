package boardBasic.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardBasic.dao.BoardDAO;
import boardBasic.dto.BoardDTO;


//1 	process 페이지를 서블렛으로 사용한다고 보면 된다
@WebServlet("/bWrite")
public class WriteBoard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = request.getRequestDispatcher("step1_boardBasicEx/bWrite.jsp"); 
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		BoardDTO boardDTO = new BoardDTO();
		//서블렛 내에서는 자바빈을 사용할 수 없다. 일일히 지정해주기
		boardDTO.setWriter(request.getParameter("writer"));
		boardDTO.setSubject(request.getParameter("subject"));
		boardDTO.setEmail(request.getParameter("email"));
		boardDTO.setPassword(request.getParameter("password"));
		boardDTO.setContent(request.getParameter("content"));
		
		BoardDAO.getInstance().insertBoard(boardDTO);
		
		
		//작성한 글을 데베로 옮긴 후 등록됐음을 알리는 것을 해당 클래스 안에서 하기 (가상의 html을 만들어주는것)
		// 클래스 내에 js 명령을 해주기 위해 response.getWriter() 해줌
		response.setContentType("text/html; charset=UTF-8"); // 한국어 인코딩을 위해 해주기  ( 안하면 밑에 jsScript변수 안 한국어를 입력받지 못한다 ) 
		PrintWriter pw = response.getWriter(); 
		
		String 	jsScript = "<script>";
				jsScript += "alert('등록되었습니다');";
				jsScript += "location.href='bList';";
				jsScript += "</script>";
				
				
		pw.print(jsScript);
		
		
	}
	
	

}

