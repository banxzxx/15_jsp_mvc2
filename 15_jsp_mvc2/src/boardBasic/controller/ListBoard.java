package boardBasic.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardBasic.dao.BoardDAO;
import boardBasic.dto.BoardDTO;

//3
@WebServlet("/bList")
public class ListBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//뷰로 이동시 뷰가 필요한 정보 세팅해놓기
		// 현재 뷰가 필요로 하는건 DTO리스트 !
		
		
		ArrayList<BoardDTO> boardList = BoardDAO.getInstance().getBoardList();
//		for (BoardDTO boardDTO : boardList) {
//			System.out.println(boardDTO);
//		}

		request.setAttribute("boardList", boardList);

		RequestDispatcher dis = request.getRequestDispatcher("step1_boardBasicEx/bList.jsp"); 
		dis.forward(request, response);
	}



}
