<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>新規登録画面</title>
<link rel="stylesheet" type="text/css" href="./css/DrinkMachine.css" >
</head>
<body>
<div class = "aaaaa">
<h1>新規アカウント作成</h1>
<br>
<a href="./login.jsp">ログイン</a><br>

<%if(request.getAttribute("errorm") != null)
{
	%><%=request.getAttribute("errorm") %><%
}
%>
</div>
<form action = "NewAcountController" method = "post" class = "aaaa">
<p>アカウント名を入力してください</p>
<br>
<br>
<input type = "text" name = "name" id = "name" value = "">
<p>パスワードを入力してください</p>
<br>
<br>
<input type = "password" name = "pas" id = "pas" value = "">
<br>
<br><input type = "submit" value = "新規登録">
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
name: "chkrequired chknocaps  chkmax16",
pas: "chkrequired chknocaps  chkmax16",

},
stepValidation: true
});
</script>
</html>