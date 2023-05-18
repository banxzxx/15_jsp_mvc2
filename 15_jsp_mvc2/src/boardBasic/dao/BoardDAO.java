package boardBasic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import boardBasic.dto.BoardDTO;

public class BoardDAO {

	private BoardDAO() {}
	private static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance() {
		return instance;
	}
	
	private Connection conn 		= null;
	private PreparedStatement pstmt = null;
	private ResultSet rs 			= null;
	
	
	public void getConnection() {
		
		/*
		
			이클립스에서 Servers폴더에 있는 Context.xml파일에 아래의 설정 추가 
		
			<Resource 
				auth="Container" 
				driverClassName="com.mysql.cj.jdbc.Driver"
				loginTimeout="10" 
				maxWait="5000" 
				name="jdbc/pool" 
				username="root"
				password="1234" 
				type="javax.sql.DataSource"
				url="jdbc:mysql://localhost:3306/JSP_MVC2_EX?serverTimezone=Asia/Seoul&amp;useSSL=false"
			/> 
		
		 */
		
		try {
			
			Context initctx = new InitialContext();
			Context envctx = (Context) initctx.lookup("java:comp/env");       // lookup 메서드를 통해 context.xml 파일에 접근하여 자바환경 코드를 검색
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool"); 		  // <Context>태그안의 <Resource> 환경설정의 name이 jdbc/pool인 것을 검색
			conn = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void getClose() {
		
    	if (rs != null)    {try {rs.close();}   catch (SQLException e) {}}
    	if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
        if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
        
    }
	
	public void insertBoard(BoardDTO boardDTO ) {
		try {
			getConnection();
			
			String 	sql = "INSERT INTO BOARD(WRITER, EMAIL, SUBJECT, PASSWORD, ENROLL_DT, READ_CNT, CONTENT) ";
					sql += "VALUES(?, ?, ?, ?, NOW(), 0, ?)";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardDTO.getWriter());
			pstmt.setString(2, boardDTO.getEmail());
			pstmt.setString(3, boardDTO.getSubject());
			pstmt.setString(4, boardDTO.getPassword());
			pstmt.setString(5, boardDTO.getContent());
			pstmt.executeUpdate();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			getClose();
		}
	}
	
	public ArrayList<BoardDTO> getBoardList() {
		
		ArrayList<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		try {
			getConnection();
			
			pstmt = conn.prepareStatement("SELECT * FROM BOARD");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO boardDTO = new BoardDTO();
				boardDTO.setBoardId(rs.getLong("BOARD_ID"));
				boardDTO.setWriter(rs.getString("WRITER"));
				boardDTO.setSubject(rs.getString("SUBJECT"));
				boardDTO.setEnrollDt(rs.getDate("ENROLL_DT"));
				boardDTO.setReadCnt(rs.getLong("READ_CNT"));
				boardList.add(boardDTO); // 어레이리스트배열에 DTO변수 추가하기
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			getClose();
		}
		
		return boardList;
	}
	
	public BoardDTO getBoardDetail(long boardId, boolean isincreaseReadCnt) {

		BoardDTO boardDTO = new BoardDTO();

		try {
			
			getConnection();
			
			// delete update는 조회수를 올라가게 하면 안된다 
			if ( isincreaseReadCnt ) {
				pstmt = conn.prepareStatement("UPDATE BOARD SET READ_CNT = READ_CNT + 1 WHERE BOARD_ID = ?");
				pstmt.setLong(1, boardId);
				pstmt.executeUpdate();
			}
			
			//select 해보기 전에 조회수를 올리기
			pstmt = conn.prepareStatement("UPDATE BOARD SET READ_CNT = READ_CNT + 1 WHERE BOARD_ID = ?");
			pstmt.setLong(1, boardId);
			pstmt.executeUpdate();
			
			//임의의 BOARD_ID를 가진 row 정보 가져오기
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE BOARD_ID = ?");
			pstmt.setLong(1, boardId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				boardDTO.setBoardId(rs.getLong("BOARD_ID"));
				boardDTO.setWriter(rs.getString("WRITER"));
				boardDTO.setEmail(rs.getString("EMAIL"));
				boardDTO.setSubject(rs.getString("SUBJECT"));
				boardDTO.setEnrollDt(rs.getDate("ENROLL_DT"));
				boardDTO.setReadCnt(rs.getLong("READ_CNT"));
				boardDTO.setContent(rs.getString("CONTENT"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		return boardDTO;
		
	}
	
	// 수정하기, 삭제하기에 입력한 비밀번호가 맞는지 확인하는 함수
	public boolean checkValidateMember(BoardDTO boardDTO) {

		boolean isValidateMember = false;
		
		try {
			
			getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE BOARD_ID = ? AND PASSWORD = ?");
			pstmt.setLong(1, boardDTO.getBoardId());
			pstmt.setString(2, boardDTO.getPassword());
			rs = pstmt.executeQuery();

			if (rs.next()) 	isValidateMember = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}

		return isValidateMember;
		
	}
	
	
	public boolean updateBoard(BoardDTO boardDTO) {

		boolean isUpdate = false;
		
		try {
			
			if (checkValidateMember(boardDTO)) {
				getConnection();
				pstmt = conn.prepareStatement("UPDATE BOARD SET SUBJECT = ? , CONTENT = ? WHERE BOARD_ID = ?");
				pstmt.setString(1, boardDTO.getSubject());
				pstmt.setString(2, boardDTO.getContent());
				pstmt.setLong(3, boardDTO.getBoardId());
				pstmt.executeUpdate();
				isUpdate = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		return isUpdate;
		
	}
	
	public boolean deleteBoard(BoardDTO boardDTO) {

		boolean isDelete = false;
		
		try {
			
			if (checkValidateMember(boardDTO)) {
				getConnection();
				pstmt = conn.prepareStatement("DELETE FROM BOARD WHERE BOARD_ID = ?");
				pstmt.setLong(1, boardDTO.getBoardId());
				pstmt.executeUpdate();
				isDelete = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		return isDelete;
		
	}
	
}
