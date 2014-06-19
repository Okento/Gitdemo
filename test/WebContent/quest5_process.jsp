<%@ page language="java" contentType="text/html; charset=UTF-8"
    import=" java.sql.*, javax.naming.*, javax.sql.*, todo.web.CheckUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<% 
request.setCharacterEncoding("UTF-8");
String isbn = request.getParameter("isbn");
String title = request.getParameter("title");
String price = request.getParameter("price");
String publish = request.getParameter("publish");
String published = request.getParameter("published");

CheckUtil c = new CheckUtil();

// それぞれの項目に対して、検証ルールを追加
c.requiredCheck(isbn, "ISBNコード");												// 必須検証
c.regExCheck(isbn, "^978-4-[0-9]{3,6}-[0-9]{3,6}-[0-9X]{1}$", "ISBNコード");		// 正規表現検証
c.duplicateCheck(isbn, "SELECT isbn FROM book WHERE isbn = ?", "ISBNコード");		// 重複検証
c.requiredCheck(title, "書名");													// 必須検証
c.lengthCheck(title, 100, "書名");												//　文字列長検証
c.numberTypeCheck(price, "価格");												// 数値型検証
c.rangeCheck(price, 10000, 1, "価格");											// 数値範囲検証
c.dateTypeCheck(published, "配本日");											// 日付型検証

// エラーを検出した場合だけ入力フォームに戻す
if(c.hasErrors()){
	request.setAttribute("err", c.getErrorList());
	application.getRequestDispatcher("/quest5_form.jsp").forward(request, response);
	return;
}
%>
</body>
</html>