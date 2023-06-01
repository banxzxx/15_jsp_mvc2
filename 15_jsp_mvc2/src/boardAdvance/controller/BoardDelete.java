package boardAdvance.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardAdvance.dao.BoardAdvanceDAO;
import boardAdvance.dto.MainBoardDTO;

@WebServlet("/boardDelete")
public class BoardDelete extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("mainBoardDTO", BoardAdvanceDAO.getInstance().getBoardDetail(Long.parseLong(request.getParameter("boardId")), false));
		
		RequestDispatcher dis = request.getRequestDispatcher("step3_boardAdvanceEx/board/boardDelete.jsp");
		dis.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		MainBoardDTO mainBoardDTO = new MainBoardDTO();

		mainBoardDTO.setBoardId(Long.parseLong(request.getParameter("boardId")));
		mainBoardDTO.setPasswd(request.getParameter("passwd"));
		
		String jsScript = "";
		if (BoardAdvanceDAO.getInstance().deleteBoard(mainBoardDTO)) {
			 jsScript = "<script>";
			 jsScript += "alert('삭제 되었습니다.');";
			 jsScript += "location.href='boardList';";
			 jsScript += "</script>";
		} 
		else {
			 jsScript = "<script>";
			 jsScript += "alert('패스워드를 확인하세요.');";
			 jsScript += "history.go(-1);";
			 jsScript += "</script>";
		}
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);
		
	}
	
	
}
