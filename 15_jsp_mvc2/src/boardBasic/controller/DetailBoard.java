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
//4
@WebServlet("/bDetail")
public class DetailBoard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// bList에서 가져온 보드아이디를 통해 데베에서 해당 보드 아이디의 DTO 정보 가져와서 세팅하기
		// boardId의 자료형에 맞게 long자료형으로 형변환해서 가져오기
		BoardDTO boardDTO = BoardDAO.getInstance().getBoardDetail(Long.parseLong(request.getParameter("boardId")), true);
		
		//System.out.println(boardDTO);
		
		request.setAttribute("boardDTO", boardDTO);
		
		RequestDispatcher dis = request.getRequestDispatcher("step1_boardBasicEx/bDetail.jsp"); 
		dis.forward(request, response);
		
	}

}
