<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%
response.setContentType("text/html; charset=UTF-8");
%>
<%@ page import="java.io.*,java.util.*,java.sql.*,javax.naming.*,drinkMachine.*"%>
<%AcountBean acount = (AcountBean)session.getAttribute("acount");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>入金画面</title>
</head>
<body>
<%
//リクエストパラメータの文字コードを指定
request.setCharacterEncoding("UTF-8");
%>
<h1>入金画面</h1>

<a href="./myPage.jsp">マイページへ</a><br>
<p>あなたの現在の所持金は<%=acount.getMoney() %>円です</p>
<form action = "MoneyController" method = "post">
<p>入金する金額を入力してください</p>
<br>
<br>
<input type = "text" name = "money" id = "name" value = "">
<br>
<br><input type = "submit" value = "入金する">
</form>
</body>
<script type="text/javascript" src="exValidation/jquery-validation-1.9.0"></script>
<Script type="text/javascript" src="exValidation/jquery-validation-1.9.0/jquery.min.js"></script>
<script type="text/javascript" src="exValidation/jquery-validation-1.9.0/jquery.easing.js"></script>
<script type="text/javascript" src="exValidation/jquery-validation-1.9.0/jQselectable.js"></script>
<script type="text/javascript" src="exValidation/jquery-validation-1.9.0/exvalidation.js"></script>
<script type="text/javascript" src="exValidation/jquery-validation-1.9.0/exchecker-ja.js"></script>
<script type="text/javascript" >
var validation = $("form")
.exValidation({
rules: {
name: "chkrequired chknumonly chkmax16",
pas: "chkrequired chknocaps  chkmax16",

},
stepValidation: true
});
</script>
</html>