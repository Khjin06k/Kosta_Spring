<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게사판글수정</title>
<style type="text/css">
h2 {
	text-align: center;
}
table {
	margin: auto;
	width: 450px;
}
.td_left {
	width: 150px;
	background: orange;
}
.td_right {
	width: 300px;
	background: skyblue;
}
#commandCell {
	text-align: center;
}
</style>
</head>
<body>
	<section id="./writeForm">
		<h2>게사판글수정</h2>
		<form action="boardmodify" method="post" enctype="multipart/form-data">
		<input type="hidden" name="num" value="${board.num}"/>

			<table border="1">
				<tr>
					<td class="td_left"><label for="writer">글쓴이</label></td>
					<td class="td_right"><input type="text" name="writer"
						id="writer" readonly="readonly" value="${board.writer}"/></td>
				</tr>
				<tr>
					<td class="td_left"><label for="title">제목</label></td>
					<td class="td_right"><input name="title" type="text"
						id="title" value="${board.title}"/></td>
				</tr>
				<tr>
					<td class="td_left"><label for="content">내용</label></td>
					<td><textarea id="content" name="content"
							cols="40" rows="15" >${board.content}</textarea></td>
				</tr>
				<tr>
					<td class="td_left"><label for="file">이미지 파일 첨부</label></td>
					<td class="td_right"><input name="file" type="file" id="file" accept="image/*"/></td>
				</tr>
			
			</table>
			<section id="commandCell">
				<input type="submit" value="수정">&nbsp;&nbsp;
				<a href="boardlist">목록</a>
			</section>
		</form>
	</section>
</body>
</html>