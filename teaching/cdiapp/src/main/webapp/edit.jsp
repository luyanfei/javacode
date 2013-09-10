<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑雇员信息</title>
</head>
<body>
<div id="wrapper">
	<form action="edit.do" method="post">
		<label for="name">名字：</label>
		<input type="text" id="name" name="name" value="${emp.name}"/><br/>
		<label for="salary">薪水：</label>
		<input type="text" id="salary" name="salary" value="${emp.salary}"/><br/>
		<label for="hiredate">雇佣日期：</label>
		<input type="text" id="hiredate" name="hiredate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${emp.hireDate}"/>"/><br/>
		<input type="submit" value="提交"/>
	</form>
</div>
</body>
</html>