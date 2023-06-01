package boardAdvance.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardAdvance.dao.BoardAdvanceDAO;


@WebServlet("/boardDetail")
public class BoardDetail extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long boardId = Long.parseLong(request.getParameter("boardId"));
		request.setAttribute("mainBoardDTO", BoardAdvanceDAO.getInstance().getBoardDetail(boardId , true));
		request.setAttribute("allReplyCnt", BoardAdvanceDAO.getInstance().getAllReplyCnt(boardId));
		request.setAttribute("replyList", BoardAdvanceDAO.getInstance().getReplyList(boardId));
		
		RequestDispatcher dis = request.getRequestDispatcher("step3_boardAdvanceEx/board/boardDetail.jsp");
		dis.forward(request, response);
	
	}

}
