<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>所有雇员</title>
</head>
<body>
	<div id="wrapper">
		<table>
			<tr><th>姓名</th><th>薪水</th><th>雇佣日期</th><th>操作</th></tr>
			<c:forEach items="${requestScope.emps}" var="e">
			<tr>
				<td>${e.name}</td>
				<td>${e.salary}</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${e.hireDate}"/></td>
				<td><a href="show.do?id=${e.id}">编辑</a></td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<div id="addlink">
		<a href="endsession.do">结束会话</a>
	</div>
</body>
</html>