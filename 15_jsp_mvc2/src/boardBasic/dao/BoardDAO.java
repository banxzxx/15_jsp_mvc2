package boardBasic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

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
	
	
	
}
