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

@WebServlet("/bDelete")
public class DeleteBoard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BoardDTO boardDTO = BoardDAO.getInstance().getBoardDetail(Long.parseLong(request.getParameter("boardId")), false);
		request.setAttribute("boardDTO" , boardDTO);
		
		RequestDispatcher dis = request.getRequestDispatcher("step1_boardBasicEx/bDelete.jsp");
		dis.forward(request, response);
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBoardId(Long.parseLong(request.getParameter("boardId")));
		boardDTO.setPassword(request.getParameter("password"));
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw = response.getWriter();	
		
		String jsScript = "";
		if (BoardDAO.getInstance().deleteBoard(boardDTO)) {
			 jsScript = "<script>";
			 jsScript += "alert('삭제 되었습니다.');";
			 jsScript += "location.href='bList';";
			 jsScript += "</script>";
		}
		else {
			 jsScript = "<script>";
			 jsScript += "alert('패스워드를 확인하세요.');";
			 jsScript += "history.go(-1);";
			 jsScript += "</script>";
		}
		
		pw.print(jsScript);
		
	}
	
	
}
