<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<s:form action="/Register" method="post">
	<table border="1">
		<tr>
			<th>ユーザー名</th>
			<td><input type="text" name="uid" size="20" /></td>
		</tr>
		<tr>
			<th>パスワード</th>
			<td><input type="password" name="passwd" size="20" /></td>
		</tr>
		<tr>	
			<th>氏名</th>
			<td><input type="text" name="uname" size="20" /></td>
		</tr>
	</table>
<input type="submit" value="登録する" />
</s:form>
</body>
</html>