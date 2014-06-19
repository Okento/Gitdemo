<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
あなたは、<br>
氏名:<%= request.getParameter("name") %><br>
<%
	String sex_string;
	if("male".equals(request.getParameter("sex"))){
		sex_string = "男性";
	}else{
		sex_string = "女性";
	}
	
	int age = Integer.parseInt(request.getParameter("age"));
	String drink;
	if(age >= 20){
		drink = "あなたはお酒が飲めますね。";
	}else{
		drink = "あなたはお酒を飲んではいけません";
	}
	
%>

性別:<%= sex_string %><br>
年齢:<%= request.getParameter("age") %>歳<br>

<br>
こんにちは　<%= (String)request.getAttribute("new_name") %>さん<br>

<br>

<%= drink %><br>

<a href="/Chap10/index.jsp">トップ</a>へ戻る
</body>
</html>