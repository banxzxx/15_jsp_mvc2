<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>register</title>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="jquery/jquery-3.6.1.min.js"></script>
<script>
	function execDaumPostcode() {
	    new daum.Postcode({
	        oncomplete: function(data) {
	
	            var fullRoadAddr = data.roadAddress; 
	            var extraRoadAddr = ''; 
	
	            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                extraRoadAddr += data.bname;
	            }
	            if (data.buildingName !== '' && data.apartment === 'Y'){
	               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	            }
	            if (extraRoadAddr !== ''){
	                extraRoadAddr = ' (' + extraRoadAddr + ')';
	            }
	            if (fullRoadAddr !== ''){
	                fullRoadAddr += extraRoadAddr;
	            }
	
	            document.getElementById('zipcode').value = data.zonecode; //5자리 새우편번호 사용
	            document.getElementById('roadAddress').value = fullRoadAddr;
	            document.getElementById('jibunAddress').value = data.jibunAddress;
	          
	        }
	    }).open();
	}
</script>
<script>
	
	
	var isValidId = false;
	var isValidConfirmPasswd = false;
	
	//가입을 누른 경우 ( onsubmit : 폼의 입력값이 서버로 제출될 때 발생하는 이벤트 )
	function checkFormData() {
		
		// 생일을 한 문장으로 
		var birthDt = $("#birthY").val() + "-" + $("#birthM").val() + "-" + $("#birthD").val();
		$("[name='birthDt']").val(birthDt);
		
		// false인 경우 조건문 실행
		if (!isValidConfirmPasswd) { 
			alert("패스워드를 확인하세요.");
			$("#confirmPasswd").focus(); // 비밀번호 확인 포커스
			return false;
		}
		
		if (!isValidId) {
			alert("아이디 중복체크를 확인하세요.");
			$("#memberId").focus(); // 아이디 포커스
			return false;
		}
		
	}	
	
	$().ready(function(){
		
		//비밀번호 확 text란에서 벗어나면 실행되는 함수 ( 얜 ajax로 안해도 되긴 함 서버를 왔다갔다할 필요가 없으니 )
		// 입력한 비밀번호 일치 불일치 구분
		$("#confirmPasswd").blur(function(){
			
			if ($("#passwd").val() == $("#confirmPasswd").val()){
				$("#msg").html("<span style='color:green;'>패스워드 일치</span>");
				isValidConfirmPasswd = true;
			}
			else {
				$("#msg").html("<span style='color:red;'>패스워드 불일치</span>");
				isValidConfirmPasswd = false;
			}
			
			
		});
		
		
		// '중복확인' 클릭 시 AJAX을 통해 서버를 거쳐 중복 아이디가 있는지 없는지 확인하기
		//CheckDuplicateId.java 서블렛으로 이동 -> DAO의 메서드를 통해 데베에 중복 확
		$("#btnOverlapped").click(function(){
			
			$.ajax({
				
				url : "checkDuplicateId",
				type : "get",
				data : {"memberId" : $("#memberId").val()},
				success : function(result) {
					// 중복시 isDuple 중복 아닐시 isNotDuple
					if (result == "isDuple") {
						alert("사용할 수 없는 아이디 입니다.");
						isValidId = false;
					}
					else {
						alert("사용할 수 있는 아이디 입니다.");
						isValidId = true;
					}
					
				}
				
			});
			
		});
		
		
	});

</script>
</head>
<body>

	<div align="center">
		<%-- 파일을 받기 때문에 인코드 타입을 multipart로 설정한다 --%>
		<form action="registerMember" method="post" enctype="multipart/form-data" onsubmit="return checkFormData();">
			<h3>회원 가입</h3>
			<table border="1">
				<tr>
					<%-- 중복아닌 아이디를 입력해도 '확인안하고 바로 가입' 안됨 
						중복확인을 안해도 isValidId = false
						중복이 아닌데 가입을 하는 경우, ajax에서 isNotduple이 뜨지만 isValidId는 false 이므로 넘어가지 않음
						
						중복 확인 버튼을 통해서만 isValidId가 true 변경 가능하다
					--%>
					<td>아이디</td>
					<td>
						<input type="text" id="memberId" name="memberId" placeholder="아이디를 입력하세요." required>
						<input type="button" id="btnOverlapped" value="중복확인" >
					</td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" id="passwd" name="passwd" placeholder="비밀번호를 입력하세요." required></td>
				</tr>
				<tr>
					<td>비밀번호 확인</td>
					<td><input type="password" id="confirmPasswd" placeholder="비밀번호를 확인하세요."><span id="msg"></span></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="memberNm" placeholder="이름을 입력하세요." required></td>
				</tr>
				<tr>
					<td>프로필이미지</td>
					<td><input type="file" name="imgNm" required></td>
				</tr>
				<tr>
					<td>성별</td>
					<td>
						남 &nbsp;<input type="radio" name="sex" value="m" checked> &emsp;
						여 &nbsp;<input type="radio" name="sex" value="f">
					</td>
				</tr>
				<tr>
					<td>생년월일</td>
					<td>
						 <select id="birthY">
				        	<c:forEach var="i" begin="0" end="2023" >
				        		<option>${2023 - i}</option>
				        	</c:forEach>
				        </select>년 
				        
			          	<select id="birthM">
				          	<c:forEach var="i" begin="1" end="12" >
				          		<c:choose>
				           		<c:when test="${i < 10 }">
				            		<option>0${i}</option>
				           		</c:when>
				           		<c:otherwise>
				            		<option>${i}</option>
				           		</c:otherwise>
				          		</c:choose>
				          	</c:forEach>
				         </select>월
				         
				         <select id="birthD">
				          	<c:forEach var="i" begin="1" end="31" >
				          		<c:choose>
				           		<c:when test="${i < 10 }">
				            		<option>0${i}</option>
				           		</c:when>
				           		<c:otherwise>
				            		<option>${i}</option>
				           		</c:otherwise>
				          		</c:choose>
				          	</c:forEach>
				         </select>일
				         <input type="hidden" name="birthDt"/>
					</td>
				</tr>
				<tr>
					<td>연락처</td>
					<td>
						<input type="text" name="hp" placeholder="-를 포함해서 입력하세요." required><br>
						SMS 소식을 수신합니다. <input type="checkbox" id="smsstsYn" name="smsstsYn" value="Y" checked>
					</td>
				</tr>
				<tr>
					<td>이메일</td>
					<td>
						<input type="text" name="email" placeholder="이메일을 입력하세요."><br>
						E-mail을 수신합니다.  <input type="checkbox" id="emailstsYn" name="emailstsYn" value="Y" checked>
					</td>
				</tr>
				<tr>
					<td>주소</td>
					<td>
						우편번호 : <input type="text" id="zipcode" name="zipcode" required>
						<input type="button" value="검색" onclick="execDaumPostcode();"> <br><br>
						도로명 주소 : <input type="text" id="roadAddress" name="roadAddress" placeholder="도로명주소를 입력하세요."><br>
						지번 주소 : <input type="text" id="jibunAddress" name="jibunAddress" placeholder="지번주소를 입력하세요."><br>
						나머지 주소 : <input type="text" id="namujiAddress" name="namujiAddress" placeholder="나머지주소를 입력하세요.">
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<button type="submit">가입</button>
					</td>
				</tr>
			</table>
		</form>		
	</div>
	
</body>
</html>