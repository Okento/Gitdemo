<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>

<%
String prev_name;
if(session != null){
	prev_name = (String)session.getAttribute("prev_name");
	if(prev_name != null){
%>
	<font color="red">
	以前、あなたはアクセスしたことがあります<br>
	</font>
	お久しぶりです　<%= prev_name %>　さん<br>
<%
	}
}
%>
<br>

名前、性別、年齢を入力してください<br>
<br>
<s:form action="/Check" method="post">
	<s:textfield key="name" label="氏名" /><br>
	<s:radio list="#{'male':'男性','female':'女性' }" name="sex" label="性別" /><br>
	<s:textfield key="age" label="年齢" /><br>
	<s:submit value="submit" />
</s:form>
</body>
</html>
