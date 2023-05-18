package boardBasic.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardBasic.dao.BoardDAO;
import boardBasic.dto.BoardDTO;

@WebServlet("/bDetail")
public class DetailBoard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 // boardId의 자료형에 맞게 long자료형으로 형변환해서 가져오기
		BoardDTO boardDTO = BoardDAO.getInstance().getBoardDetail(Long.parseLong(request.getParameter("boardId")), true);
		
		//System.out.println(boardDTO);
		
		request.setAttribute("boardDTO", boardDTO);
		
		RequestDispatcher dis = request.getRequestDispatcher("step1_boardBasicEx/bDetail.jsp"); 
		dis.forward(request, response);
		
	}

}
