<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="test.TBook" %>
<%@ page import="test.BookDB" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<center>
	<h2>購入商品</h2>
</center>

<br><br>

以下が購入する商品と合計です。
<br>
<table border="1">

<%
List<String> listCheckedBook = (List<String>)session.getAttribute("CART");

BookDB bd = new BookDB();

bd.getConnection();

if(listCheckedBook != null){
	for(String iterBookISBN :listCheckedBook){
		TBook book = (TBook)bd.findBookByISBN(iterBookISBN);
%>

<tr>
	<td>
	<%= book.getTitle() %>
	</td>
	<td>
	<%= book.getAuthor() %>
	</td>
</tr>
<tr>
	<td>
	<%= book.getPublisher() %>
	</td>
	<td>
	<%= book.getPrice() %>
	</td>
</tr>
<%
	}
}
%>
</table>
<br><br>
合計: <%= request.getAttribute("TOTAL") %>円
</body>
</html>