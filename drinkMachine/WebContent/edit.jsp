<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*,java.sql.*,javax.naming.*,drinkMachine.*,drinkMachine.Dao.*"%>
<%ItemBean itembean = (ItemBean)request.getAttribute("itembean");%>
<%String msg = (String)request.getAttribute("msg");%>
<html xml:lang="ja" lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!--
<link type="text/css" rel="stylesheet" href="exValidation/css/style.css" />
-->
<link type="text/css" rel="stylesheet" href="exValidation/css/exvalidation.css" />
<title>編集画面</title>
<link rel="stylesheet" type="text/css" href="./css/DrinkMachine.css" >
</head>
<body>
<h1>商品変更</h1>
 <a href="./list.jsp">一覧</a><br><br>
<%if(msg!=null){%>
<p><%=msg %></p>
<%} %>
<form action="UpdateController" method="post" enctype = "multipart/form-data">

<table class = "table1">
  <tbody><tr>
    <th class="tr1">商品コード</th>
    <td class="td1"><input type="text" id="code" name="code" readonly="readonly" value="<%=itembean.getCode()%>"></td>
  </tr>
  <tr>
    <th class="tr1">商品名<sup><font color="#ff0000">*</font></sup></th>

    <td class="td1"><input type="text" id="name" name="name" value="<%=itembean.getName()%>"></td>
  </tr>
  <tr>
    <th class="tr1">金額<sup><font color="#ff0000">*</font></sup></th>
    <td class="td1"><input type="text" id="unitPrice" name="unitPrice" value="<%=itembean.getPrice()%>"></td>
  </tr>
  <tr>
    <th class="tr1">数量<sup><font color="#ff0000">*</font></sup></th>

    <td class="td1"><input type="text" id="count" name="count" value="<%=itembean.getCount()%>"></td>
  </tr>
  <tr>
    <th class="tr1">商品画像</th>
    <td class="td1"><input type="file" id="image" name="image"src ="" value = "<%=itembean.getImage()%>"></td>
  </tr>
  <tr>
    <th class="tr1">商品区分</th>
<%String a = itembean.getIsPR();%>
    <td class="td1"> <input type="hidden" name="isPR" value="0"><input type="checkbox" id="isPR" name="isPR" value="1" <%if(a.equals("1")){%> checked <%}%>{% endif %}>あったかい(チェックなし:つめたい)</input>
    					 </td>
  </tr>
</tbody></table><br>
<input type="submit" value="変更する">
</form>

<br>
<font color="#ff0000">*</font>

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
name: "chkrequired chkmax200",
unitPrice: "chkrequired chknumonly chkmin1 chkmax4 chknum1000",
count: "chkrequired chknumonly chkmin1 chkmax3 chknum100",
filename: "chkfile"
},
stepValidation: true
});
</script>


</html>
