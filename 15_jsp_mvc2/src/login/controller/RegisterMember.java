package login.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import login.dao.MemberDAO;
import login.dto.MemberDTO;

@WebServlet("/registerMember")
public class RegisterMember extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dis = request.getRequestDispatcher("step2_loginEx/mRegister.jsp");
		dis.forward(request, response);
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String file_repo_path = "C:\\Users\\19_web_bjw\\git\\15_jsp_mvc2\\15_jsp_mvc2\\WebContent\\step2_loginEx\\memberImg";
		MultipartRequest multi = new MultipartRequest(request, file_repo_path , 1024 * 1024 * 30 , "utf-8" , new DefaultFileRenamePolicy()); // 파일 업로드
		
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberId(multi.getParameter("memberId"));
		memberDTO.setMemberNm(multi.getParameter("memberNm"));
		memberDTO.setPasswd(multi.getParameter("passwd"));
		memberDTO.setSex(multi.getParameter("sex"));
		memberDTO.setBirthDt(multi.getParameter("birthDt"));
		memberDTO.setHp(multi.getParameter("hp"));
		if (multi.getParameter("smsstsYn") == null) memberDTO.setSmsstsYn("N");								// 체크박스가 선택이 안되어있으면 N 지정
		else										  memberDTO.setSmsstsYn(multi.getParameter("smsstsYn"));    // 체크박스가 선택이 되어있으면 Y지정
		memberDTO.setEmail(multi.getParameter("email"));
		if (multi.getParameter("emailstsYn") == null) memberDTO.setEmailstsYn("N");								// 체크박스가 선택이 안되어있으면 N 지정
		else 											memberDTO.setEmailstsYn(multi.getParameter("emailstsYn"));	// 체크박스가 선택이 되어있으면 Y지정						
		memberDTO.setZipcode(multi.getParameter("zipcode"));
		memberDTO.setRoadAddress(multi.getParameter("roadAddress"));
		memberDTO.setJibunAddress(multi.getParameter("jibunAddress"));
		memberDTO.setNamujiAddress(multi.getParameter("namujiAddress"));
		
		
		Enumeration files =  multi.getFileNames();						// <input type="file"> 엘리먼트들을 반환
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");		// 파일명에 업로드 날짜를 추가하기 위한 형식 생성
		String imgNm = "";
		String originalFileName = "";
		if (files.hasMoreElements()) {															// <input type="file">이 존재하면
			String element = (String)files.nextElement();										// 엘리먼트를 읽어옴
			originalFileName = multi.getOriginalFileName(element);								// 업로드한 파일 이름을 읽어옴
			imgNm = sdf.format(new Date()) + "_" + UUID.randomUUID() + "_" + originalFileName;	// '날짜_해쉬함수_업로드파일명' 형식으로 파일명 생성 (UUID.randomUUID() : 해쉬함수 생성)
		}
		memberDTO.setImgNm(imgNm);																// '날짜_해쉬함수_업로드파일명' 형식으로 DTO에 set
		
		
		File file = new File(file_repo_path + originalFileName);								// 기존에 업로드한 파일을 읽어옴
		File renameFile = new File(file_repo_path + imgNm);										// 변환된 파일명으로 새로운 파일을 생성
		file.renameTo(renameFile);																// 기존에 업로드한 파일을 변환된 파일명으로 이름 변경
		
		MemberDAO.getInstance().registerMember(memberDTO);
		
		String jsScript = "<script>";
			   jsScript += "alert('회원가입 되었습니다.');";
			   jsScript += "location.href='mainMember';";
			   jsScript += "</script>";
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);
	
	}
	

}
