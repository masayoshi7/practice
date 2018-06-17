<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*,javax.naming.*,drinkMachine.*"%>
<%AcountBean acount = (AcountBean)session.getAttribute("acount");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xml:lang="ja" lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>マイページ</title>
<link href="bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
<!--
.btn-circle {
  width: 30px;
  height: 30px;
  text-align: center;
  padding: 6px 0;
  font-size: 12px;
  line-height: 1.428571429;
  border-radius: 50%;
}
.btn-circle.btn-lg {
  width: 50px;
  height: 50px;
  padding: 10px 16px;
  font-size: 18px;
  line-height: 1.33;
}
.btn-circle.btn-xl {
  width: 70px;
  height: 70px;
  padding: 10px 16px;
  font-size: 24px;
  line-height: 1.33;

}
.aaa{
position:relative;
top:-100px;
left:365px;
}
.aaa:hover{
content:"画像です。";

}
.aaa1{
position:relative;
top:180px;
left:30px;
}
.aaa2{
position:relative;
top:109px;
left:110px;
}
.aaa3{
position:relative;
top:38px;
left:195px;
}
.aaa4{
position:relative;
top:-31px;
left:280px;
}
.font{
color :white;
font-size:60px;
font-style:italic;
 font-family :cursive;

}
.fonta{
color :white;
position:relative;
top:290px;
left:40px;
font-style:italic;
 font-family :cursive;
}
//-->
</style>
</head>
<%
int a = (int)(Math.random()*3);
%>
<body
<% if(a == 0){ %>
background="image\fireworks12.jpg"
<%} %>
<% if(a == 1){ %>
background="image\twilightwall_wuxga.jpg"
<%} %>
<% if(a == 2){ %>
background="image\other14.jpg"
<%} %>
style="background-repeat: no-repeat;background-size: cover;background-position: center center;background-attachment: fixed;">
<%
//リクエストパラメータの文字コードを指定
request.setCharacterEncoding("UTF-8");
%>
<p class = "font">WELCOM!</p>
<p class = "font"><%=acount.getName()%></p>

<p class = "font">Hold Gold [<%=acount.getMoney() %>￥]</p>
<%if(acount.getName().equals("7777"))
{%>
<p class = "fonta">※管理者用のマイページです</p>

<%} %>
<div class = "aaa1">
<form action = "CartController"  method = "post">
<a href="javascript:q_code();" title="商品購入へ"><button type="button" class="btn btn-warning btn-circle  btn-xl"><i class="glyphicon glyphicon-shopping-cart"></i></button></a>
</form>
</div>
<div  class = "aaa2">
<form action = "AcountDelContoroller"  method = "post">
<a href="javascript:d_code(<%=acount.getNo()%>);" title="アカウントを削除する"><button type="button" class="btn btn-danger btn-circle  btn-xl"><i class="glyphicon glyphicon glyphicon-remove-circle"></i></button></a>
</form>
</div>
<div class = "aaa3">
<form action = "LogOutController"  method = "post">
<a href="javascript:abc();" title="ログアウトする"><button type="button" class="btn btn-success btn-circle  btn-xl"><i class="glyphicon glyphicon glyphicon-lock"></i></button></a>
</form>
</div>
<div class = "aaa4">
<a href="money.jsp" title="お金をチャージする"><button type="button" class="btn btn-info btn-circle  btn-xl"><i class="glyphicon glyphicon-usd"></i></button></a>
</div>
<%if(acount.getName().equals("7777"))
{%>
<div>
<a href="./list.jsp" title="商品管理へ"><button type="button" class="btn btn-primary btn-circle  btn-xl aaa"><i class="glyphicon glyphicon-list-alt"></i></button></a>
</div>
<%} %>
<SCRIPT type = "text/javascript" language = JavaScript>
<!--
function q_code()
{

		var f = document.createElement('input');
		f.type = 'hidden';
		f.name = 'code';
		document.forms[0].appendChild(f);
		document.forms[0].submit();

}
// -->
</SCRIPT>
<SCRIPT type = "text/javascript" language = JavaScript>
<!--
function d_code(no)
{
	if(confirm("■アカウントを削除しますか？")== false)
		{
		return false;
		}
	else
		{
		var v = document.createElement('input');
		v.type = 'hidden';
		v.name = 'Nom';
		v.value = no;

		document.forms[1].appendChild(v);
		document.forms[1].submit();
		}
}
// -->
</SCRIPT>
<script type="text/javascript">
<!--
function abc()
{
	if(confirm("■ログアウトしますか？")==false)
	{
		return false;
	}
	else
	{
	var a = document.createElement('input');
	a.type = 'hidden';
	a.name = 'Nomalcode';
	document.forms[2].appendChild(a);
	document.forms[2].submit();
	}
}
// -->
</script>
</body>
</html>