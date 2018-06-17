<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*,javax.naming.*,drinkMachine.*,drinkMachine.Dao.*"%>
<%List<ItemBean> list = (List<ItemBean>)session.getAttribute("sltm");%>
<%String errorMsg = (String)request.getAttribute("kekka");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/DtD/xhtml1-transitional.dtd">
<link type="text/css" rel="stylesheet" href="C:\eclipse\workspece2\ jspServlet" />
<html xml:lang="ja" lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>検索画面</title>
<link rel="stylesheet" type="text/css" href="./css/DrinkMachine.css" >
</head>
<body>
<h1>商品一覧</h1>
<%if(errorMsg != null){%>
<p><%=errorMsg %></p>
<%} %>

<a href="./add.jsp">追加</a><br>
<form action = "CartController"  method = "post"><a href="javascript:q_code();">購入者画面をチェックする</a></form><br>
<form action = "ListController"  method = "post">
<br>
<div>
<table class = "table1">
  <tbody><tr>
    <th class="tr1">商品コード</th>
    <td class="td1"><input type="text" name="code" value=""> </td>
  </tr>
  <tr>
    <th class="tr1">商品名<sup><font color="#ff0000">*</font></sup></th>

    <td class="td1"><input type="text" name="name" value=""> </td>
  </tr>
  <tr>
    <th class="tr1">他、検索条件</th>
    <td class="td1">
      <input type="checkbox" name="hot" value="1"{% if item.isPR %} checked{% endif %}>あったかい</input>
      <input type="hidden" name="hot" value="0">
      <input type="checkbox" name="cool" value="1"{% if item.isPR %} checked{% endif %}>つめたい</input>
      <input type="hidden" name="cool" value="0">
      <input type="checkbox" name="isSoldout" value="1"{% if item.isSoldout %} checked{% endif %}>売切れ商品</input>
      <input type="hidden" name="isSoldout" value="0">
    </td>

  </tr>
</tbody></table>
<br>
</div>
<div bgcolor="#999999" border="0">

<input type="submit" onclick="kensaku()" value="検索">
</div>
</form>


<br>

<form action = "CSVController"  method = "post"  enctype="multipart/form-data"><!--<input onclick = "addCsv()" type="submit" value="一括登録">--></form>

<form action = "CSVAddController"  method = "post"><!--<input onclick = "addCsv2()" type="submit" value="一覧の情報をCSV出力">--></form>

<br>
<br>

<%if(list != null)
{

for(int i= 0;i<list.size();i++)			//検索の値を出力するfor文
  {
  	ItemBean selectedItm = list.get(i);
  	%>

<table class = "table1">
  <tbody><tr class="tr1">
    <th class="tr1">&nbsp;</th>
    <th class="tr1">&nbsp;</th>
    <!--<th class="tr1">&nbsp;</th>-->
    <th class="tr1" align="center">商品コード</th>
    <th class="tr1" align="center">商品名</th>
    <th class="tr1" align="center">金額</th>
    <th class="tr1" align="center">数量</th>
</tr>

<tr class="tr1">
    <!--<td class="td1"><form action = "ViewController"  method = "post"><a href="javascript:b_code(<%=selectedItm.getCode()%>);">詳細</a></form></td>-->
    <td class="td1"><form action = "EditController"  method = "post"><a href="javascript:a_code(<%=selectedItm.getCode()%>);">編集</a></form></td>
    <td class="td1"><form action = "DelController"  method = "post"><a href="javascript:sakuzyo_code(<%=selectedItm.getCode()%>);">削除</a></form></td>

    <td class="td1"> <%= selectedItm.getCode() %></td>
    <td class="td1"> <%= selectedItm.getName() %></td>
	<td class="td1"> <%= selectedItm.getPrice() %></td>
	<td class="td1"> <%= selectedItm.getCount() %></td>
</tr>


</table>
<% }}%>
</div>
<SCRIPT type = "text/javascript" language = JavaScript>
<!--
function a_code(code)
{
	if(confirm("■商品データを変更しますか？")== false)
		{
		location.href = "./list.jsp";
		return false;
		}
	else
		{
		var u = document.createElement('input');
		u.type = 'hidden';
		u.name = 'code';
		u.value = code;
		document.forms[4].appendChild(u);
		document.forms[4].submit();
		}
}
//-->
</SCRIPT>
<SCRIPT type = "text/javascript" language = JavaScript>
<!--
function sakuzyo_code(code)
{
	if(confirm("■商品情報を削除しますか？")== false)
		{
		location.href = "./list.jsp";
		return false;
		}
	else
		{
		var a = document.createElement('input');

		a.type = 'hidden';
		a.name = 'code';
		a.value = code;
		document.forms[5].appendChild(a);
		document.forms[5].submit();

		}
}
//-->
</SCRIPT>
<SCRIPT type = "text/javascript" language = JavaScript>
<!--
function b_code(code)
{
	if(confirm("■商品情報を詳細閲覧しますか？")== false)
		{
		location.href = "./list.jsp";
		return false;
		}
	else
		{
		var h = document.createElement('input');
		h.type = 'hidden';
		h.name = 'code';
		h.value = code;
		document.forms[4].appendChild(h);
		document.forms[4].submit();
		}
}
// -->
</SCRIPT>
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
function addCsv(){
if(confirm("■一括登録を行いますか？") == false)
{
	location.href = "./list.jsp";
	return false;
}
else
{
var cs = document.createElement('input');

cs.type = 'file';
cs.name = 'csv';

document.forms[2].appendChild(cs);
cs.click();
document.forms[2].submit();


}
}
//-->
</SCRIPT>
<SCRIPT type = "text/javascript" language = JavaScript>
<!--
function addCsv2(list){
if(confirm("■商品情報をｃｓｖファイルに出力しますか？") == false)
{

	return;
	location.href = "./list.jsp";
}
else
{
var hh = document.createElement('input');

hh.type = 'hidden';
hh.name = 'csv2';

document.forms[3].appendChild(hh);
document.forms[3].submit();


}
}
//-->
</SCRIPT>
<SCRIPT type = "text/javascript" language = JavaScript>
<!--
function addAll()
{
	if(confirm("■全ての商品を検索しますか？")== false)
		{
		location.href = "./list.jsp";
		return false;
		}
	else
		{
		var a = document.createElement('input');

		a.type = 'hidden';
		a.name = 'code';
		a.value = code;
		document.forms[2].appendChild(a);
		document.forms[2].submit();

		}
}
//-->
</SCRIPT>



</tbody>

</body>
</html>
