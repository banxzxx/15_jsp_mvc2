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
import boardAdvance.dto.ReplyDTO;

@WebServlet("/replyDelete")
public class ReplyDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("replyDTO", BoardAdvanceDAO.getInstance().getReplyDetail(Long.parseLong(request.getParameter("replyId"))));
		
		RequestDispatcher dis = request.getRequestDispatcher("step3_boardAdvanceEx/reply/replyDelete.jsp");
		dis.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		ReplyDTO replyDTO = new ReplyDTO();
		replyDTO.setReplyId(Long.parseLong(request.getParameter("replyId")));
		replyDTO.setBoardId(Long.parseLong(request.getParameter("boardId")));
		replyDTO.setPasswd(request.getParameter("passwd"));

		String jsScript = "";
		if (BoardAdvanceDAO.getInstance().deleteReply(replyDTO)) {
			jsScript = "<script>";
			jsScript += "alert('삭제 되었습니다.');";
			jsScript += "location.href='boardDetail?boardId=" + replyDTO.getBoardId() + "'";
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
