<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 加入JSTL的标签库 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>确定借阅</title>
<link href="/kemao_2/library/css/main.css" rel="stylesheet"/>
</head>
<body>
	<c:forEach items="${borrowList.borrow }" var="borrow">
		<div class="item">
			${borrow.name }
			
			
		</div>
	</c:forEach>
</body>
</html>