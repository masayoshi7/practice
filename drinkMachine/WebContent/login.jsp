<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xml:lang="ja" lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>ログインフォーム</title>
<link rel="stylesheet" type="text/css" href="./css/DrinkMachine2.css" >
</head>
<body background="image\sea109.jpg" style="background-repeat: no-repeat;background-size: cover;background-position: center center;background-attachment: fixed;">

<br>
<br>

<%
//リクエストパラメータの文字コードを指定
request.setCharacterEncoding("UTF-8");
%>
<%if(request.getAttribute("error") != null)
{%>
	<script type="text/javascript">
<!--
		alert("ユーザ名または、パスワードが正しくありません");

//-->
</script>
<%
}
%>


<form action = "LogInController" method = "post" class = "a">
<p>アカウント名を入力してください</p>
<br>
<br>
<input class = "a" type = "text" name = "name" id = "name" placeholder="Username" value = "">
<p>パスワードを入力してください</p>
<br>
<br>
<input class = "a" type = "password" name = "pas" id = "pas" placeholder="Password" value = "">
<br>
<br><input class = "a" type = "submit" value = "ログイン">
<a href="./NewAcount.jsp">新規登録</a>
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