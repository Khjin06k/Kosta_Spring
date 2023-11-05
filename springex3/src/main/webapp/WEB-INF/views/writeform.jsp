<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판글등록</title>
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
	<script>
		window.onload = function(){
			const fileDom = document.querySelector("#file");
			const imageBox = document.querySelector("#image-box");

			fileDom.addEventListener('change', ()=>{
				const imageSrc = URL.createObjectURL(fileDom.files[0]);
				console.log(imageSrc);
				imageBox.src = imageSrc;
			})
		}
	</script>
</head>
<body>
	<section id="./writeForm">
		<h2>게시판글등록</h2>
		<form action="boardwrite" method="post" enctype="multipart/form-data" name="boardform">
			<input type="hidden" name="writer" value="${user.id}"/>
			<table>
				<tr>
					<td class="td_left"><label>글쓴이</label></td>
					<td class="td_right"><input type="text" readonly="readonly" value="${user.name }"/></td>
				</tr>
				<tr>
					<td class="td_left"><label for="title">제목</label></td>
					<td class="td_right"><input name="title" type="text"
						id="title" required="required" /></td>
				</tr>
				<tr>
					<td class="td_left"><label for="content">내용</label></td>
					<td><textarea id="content" name="content"
							cols="40" rows="15" required="required"></textarea></td>
				</tr>
				<tr>
					<td class="td_left"><label for="file">이미지 파일 첨부</label></td>

					<td class="td_right"><input name="file" type="file" id="file" accept="image/*"/>
						<img id="image-box" width="100px" height="100px" src="img/noImage.png"/><br/>
					</td>
				</tr>
			</table>
			<section id="commandCell">
				<input type="submit" value="등록">&nbsp;&nbsp; 
				<input type="reset" value="다시쓰기" />
			</section>
		</form>
	</section>
</body>
</html>