<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="POST" action="quest5_process.jsp">
${requestScope['err']}
<table border="0">
	<tr>
		<th align="right">ISBNコード:</th>
		<td><input type="text" name="isbn" size="25" maxlength="30" value="${param.isbn}" /></td>
	</tr>
	
	<tr>
		<th align="right">書名:</th>
		<td><input type="text" name="title" size="35" maxlength="170" value="${param.title}" /></td>
	</tr>
	
	<tr>
		<th align="right">価格:</th>
		<td><input type="text" name="price" size="5" maxlength="5" value="${param.price}" />円</td>
	</tr>
	
	<tr>
		<th align="right">出版社:</th>
		<td><input type="text" name="publish" size="15" maxlength="20" value="${param.publish}" /></td>
	</tr>
	
	<tr>
		<th align="right">配本日:</th>
		<td><input type="text" name="published" size="15" maxlength="15" value="${param.published}" /></td>
	</tr>
</table>
<input type="submit" value="登録する" />
</form>
</body>
</html>