<%@ page language="java" contentType="text/html; charset=Shift_JIS"
    pageEncoding="Shift_JIS" import="java.sql.*,javax.naming.*,javax.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Shift_JIS">
<title>Insert title here</title>
</head>
<%
Context context = new InitialContext();
DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/dbtest");
Connection db = ds.getConnection();
db.close();
%>
success
<body>

</body>
</html>