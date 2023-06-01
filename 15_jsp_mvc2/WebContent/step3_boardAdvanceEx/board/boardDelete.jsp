<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardDelete</title>
</head>
<body>

	<div align="center" style="padding-top: 100px">
		<form action="boardDelete" method="post">
			<h3>게시글 삭제</h3>
			<br>
			<table style="width: 700px" border="1">
				<tr>
					<td>작성자</td>
					<td>${mainBoardDTO.writer }</td>
				</tr>
				<tr>
					<td>작성일</td>
					<td>${mainBoardDTO.enrollDt }</td>
				</tr>
				<tr>
					<td>제목</td>
					<td>${mainBoardDTO.subject }</td>
				</tr>
				<tr>
					<td>패스워드</td>
					<td><input type="password" name="passwd" size="60"></td>
				</tr>
				<tr align="right">
					<td colspan="2">
						<input type="hidden" name="boardId" value="${mainBoardDTO.boardId }">
						<input type="submit" value="글삭제">
						<input type="button" onclick="location.href='boardList'" value="목록보기">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
</body>
</html>