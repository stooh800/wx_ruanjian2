<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 加入JSTL的标签库 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>借阅历史</title>
<link href="/kemao_2/library/css/main.css" rel="stylesheet"/>
</head>
<body>
	<table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>编号</th>
								<th>书名</th>
								<th>图片</th>
								<th>借书时间</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${borrowList}" var="borrow">
								<tr>
									<td>${borrow.id}</td>
								    <td>${borrow.name}</td>	
								    <td><img src="/kemao_2/library/images/${borrow.image}"></td>
								    <td>${borrow.borrowtime}</td> 
								</tr>
							</c:forEach>
							</tbody>
</body>
</html>