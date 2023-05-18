<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bDetail</title>
</head>
<body>

	<h1>게시글 상세조회</h1>
	<table border="1">
		<tr>
			<td>조회수</td>
			<td>${boardDTO.readCnt }</td>
		</tr>
		<tr>
			<td>작성자</td>
			<td>${boardDTO.writer }</td>
		</tr>
		<tr>
			<td>작성일</td>
			<td>${boardDTO.enrollDt }</td>
		</tr>
		<tr>
			<td>이메일</td>
			<td>${boardDTO.email }</td>
		</tr>
		<tr>
			<td>제목</td>
			<td>${boardDTO.subject }</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>${boardDTO.content }</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" value="수정하기" onclick="location.href='bUpdate?boardId=${boardDTO.boardId}'">
				<input type="button" value="삭제하기" onclick="location.href='bDelete?boardId=${boardDTO.boardId}'">
				<input type="button" value="목록보기" onclick="location.href='bList'">
			</td>
		</tr>
	</table>
	
</body>
</html>