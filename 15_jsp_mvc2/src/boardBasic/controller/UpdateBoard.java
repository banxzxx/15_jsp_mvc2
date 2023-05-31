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


@WebServlet("/bUpdate")
public class UpdateBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//화면으로 넘어가기
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//bDetail에서 이동할 때 같이 가져온 보드아이디 값을 통해 DTO 정보 가져와서 세팅하기
		//해당 정보를 가져오는 메서드를 사용시 이는 detail 페이지가 아닌 update에서 사용하것이므로 false 인자 전송
		
		//여기서 돌리면 request.getP~("boardId")가 null이기 때문에 페이지를 찾을 수 없다고 뜬다. ( boardId가 null인애는 없으니 ) 
		//ListBoard에서 실행하고 들어가면 값을 반환해 해당 화면을 보여줄 수 있음
		BoardDTO boardDTO = BoardDAO.getInstance().getBoardDetail(Long.parseLong(request.getParameter("boardId")), false);
		request.setAttribute("boardDTO", boardDTO);
		
		RequestDispatcher dis = request.getRequestDispatcher("step1_boardBasicEx/bUpdate.jsp"); 
		dis.forward(request, response);
	}

	//process 역할 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 수정한 내용을 가져왔다 ~!!~ 이제 이걸처리해주기 !
		request.setCharacterEncoding("utf-8");
		
		// DAO에서 수정 처리를 할 DTO객체 선언해주기
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBoardId(Long.parseLong(request.getParameter("boardId")));
		boardDTO.setSubject(request.getParameter("subject"));
		boardDTO.setPassword(request.getParameter("password"));
		boardDTO.setContent(request.getParameter("content"));
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		
		String jsScript = "";
		if ( BoardDAO.getInstance().updateBoard(boardDTO) ) {
			jsScript = "<script>";
			jsScript += "alert('수정되었습니다');";
			jsScript += "location.href='bList';";
			jsScript += "</script>";
		}
		else {
			jsScript = "<script>";
			jsScript += "alert('check yout password');";
			jsScript += "history.go(-1);";
			jsScript += "</script>";
			
		}
		
		pw.print(jsScript);
	}

}
