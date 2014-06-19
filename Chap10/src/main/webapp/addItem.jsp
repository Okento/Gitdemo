<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<s:form action="/IRegister" method="post">
	<table border="1">
		<tr>
			<th>ISBN</th>
			<td><input type="text" name="isbn" size="20" /></td>
		</tr>
		<tr>
			<th>作品名</th>
			<td><input type="text" name="title" size="20" /></td>
		</tr>
		<tr>
			<th>著者名</th>
			<td><input type="text" name="author" size="20" /></td>
		</tr>
		<tr>
			<th>出版社</th>
			<td><input type="text" name="publisher" size="20" /></td>
		</tr>
		<tr>
			<th>価格</th>
			<td><input type="text" name="price" size="20" /></td>
		</tr>
	</table>
<input type="submit" value="登録する" />
</s:form>
</body>
</html>