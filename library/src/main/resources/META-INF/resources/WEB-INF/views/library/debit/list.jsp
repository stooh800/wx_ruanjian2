<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 加入JSTL的标签库 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>借阅列表</title>
<link href="/kemao_2/library/css/main.css" rel="stylesheet"/>
<style>
a{
background-color:bule;
boder:5px;
width:25px;
height:20px;
}
a:hover{
background-color: rgba(200, 200, 200, 0.5);
cursor: pointer;
    
}
</style>
</head>
<body>
	<c:forEach items="${debitList.books }" var="book">
		<div class="item">
			${book.name }
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="/kemao_2/library/debit/remove/${book.id }" class="remove">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="/kemao_2/library/borrowdebit?id=${book.id}" class="save">确定借阅</a>
			
		</div>
	</c:forEach>
</body>
</html>