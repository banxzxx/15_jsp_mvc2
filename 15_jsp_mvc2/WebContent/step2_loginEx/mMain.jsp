<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main</title>
</head>
<body>

	<%--
	
		1) 데이터 베이스
	
			<Resource 
				auth="Container" 
				driverClassName="com.mysql.cj.jdbc.Driver"
				loginTimeout="10" 
				maxWait="5000" 
				name="jdbc/pool2" 
				username="root"
				password="1234" 
				type="javax.sql.DataSource"
				url="jdbc:mysql://localhost:3306/JSP_MVC2_EX?serverTimezone=Asia/Seoul&amp;useSSL=false"
			/> 		
	
		2) 파일 경로 수정
			
			(doPost)
			RegisterMember , UpdateMember , DeleteMember 
			
			PS. ctrl + shift + r 하면 드래그한 거의 이름의 파일을 찾아준다
			해당 파일로 가서 경로는 내 컴퓨터에 있는 membering의 경로로 바꿔주기 !
	
	 --%>

	<div align="center">
		<img src="img/member.PNG" width="200" height="200"><br>
		<a href="registerMember">회원가입</a>&emsp;
		<a href="loginMember">로그인</a>
	</div>
	
</body>
</html>


