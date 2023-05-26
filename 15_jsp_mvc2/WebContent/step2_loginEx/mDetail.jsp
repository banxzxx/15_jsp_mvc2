<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detail</title>
<script>

	function deleteMember() {
		
		if (confirm("정말로 탈퇴하시겠습니까?")) {
			location.href = "deleteMember";
		}
		
	}
	
</script>
</head>
<body>

	<div align="center">
		<h3>개인정보 조회</h3>
		<table border="1">
			<tr>
				<td>프로필 이미지</td>
				<td><img src="step2_loginEx/memberImg/${memberDTO.imgNm}" width="200" height="100"></td>
			</tr>
			<tr>
				<td>아이디</td>
				<td>${memberDTO.memberId }</td>
			</tr>
			<tr>
				<td>이름</td>
				<td>${memberDTO.memberNm }</td>
			</tr>
			<tr>
				<td>성별</td>
				<td>
					<c:choose>
						<c:when test="${memberDTO.sex == 'm'}">
							남
						</c:when>
						<c:otherwise>
							여
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>생년월일</td>
				<td>${memberDTO.birthDt }</td>
			</tr>
			<tr>
				<td>연락처</td>
				<td>${memberDTO.hp } / 수신 동의 : ${memberDTO.smsstsYn }</td>
			</tr>
			<tr>
				<td>이메일</td>
				<td>${memberDTO.email } / 수신 동의 : ${memberDTO.emailstsYn }</td>
			</tr>
			<tr>
				<td>우편번호</td>
				<td>
					${memberDTO.roadAddress } <br>
					${memberDTO.jibunAddress } <br>
					${memberDTO.namujiAddress } <br>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<button onclick="location.href='updateMember'">내 정보 수정</button>
					<button onclick="location.href='logoutMember'">로그아웃</button>
					<button onclick="deleteMember();">회원탈퇴</button>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>