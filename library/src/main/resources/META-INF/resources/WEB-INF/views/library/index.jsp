<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 引入JSTL里面的core标签库，它里面包含了forEach、if、choose等标签，代替JSP的脚本和表达式。 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>图书馆首页</title>
<link href="/kemao_2/library/css/main.css" rel="stylesheet" />
</head>
<body>
	<form action="" method="get">
    <button class="fanghui"></button>
		<input name="keyword" value="${param.keyword }" placeholder="关键字" />
    
		<button class="search">搜索</button>
	</form>
    
	<c:forEach items="${page.content }" var="book">
		<div class="book">
			<div class="col-1">
				<img class="image" src="/kemao_2/library/images/${book.image }" /> 
			</div>
			<span class="col-10 name">${book.name }</span>
			<span class="col-1" style="text-align: right;">
				<a href="/kemao_2/library/debit?id=${book.id}" class="button">+</a>
			</span>      
		</div>    
        
	</c:forEach>
	<div class="fenye">
		<c:if test="${page.number > 0 }">
			<a href="">上一页</a>
		</c:if>
		<c:if test="${page.number <= 0 }">
			<a>上一页</a>
		</c:if>
		<c:choose>
			<c:when test="${page.number >= page.totalPages - 1 }">
				<a>下一页</a>
			</c:when>
			<c:otherwise>
				<a href="">下一页</a>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>