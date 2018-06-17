<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*,javax.naming.*,drinkMachine.*,drinkMachine.Dao.*"%>
 <%List<ItemBean> PRList = (List<ItemBean>)session.getAttribute("PRItem");%>
<%List<ItemBean> NomalList = (List<ItemBean>)session.getAttribute("NormalItem");%>
<%AcountBean acount = (AcountBean)session.getAttribute("acount");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xml:lang="ja" lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" href="exValidation/css/exvalidation.css" />
<title>購入画面</title>
<link rel="stylesheet" type="text/css" href="./css/DrinkMachine.css" >
<!--[if IE]>
<script type="text/javascript">
    document.createElement('header');
    document.createElement('section');
    document.createElement('nav');
    document.createElement('footer');
</script>
<![endif]--> 
</head>
<body>
<div id = "a-box">
<h1 style="position:absolute; top:40px; left:350px;">自動販売機：購入画面</h1>
<a  style="position:absolute; top:80px; left:1100px;" href="./login.jsp"><font size="3">ログイン</font></a><br>
<a  style="position:absolute; top:120px; left:1100px;" href="./NewAcount.jsp"><font size="3">新規登録</font></a>
</div>

<%if(acount != null)
{%>
<img align="right" style = "position: fixed; top:160px; left:1010px;" src ="image\\illust4461.png" width="200" height="230">
<div style = "position: fixed; top:200px; left:1040px;" >
<p><%=acount.getName()%>さん</p>
<p>所持金は<%=acount.getMoney()%>円です</p>
<a href="./myPage.jsp">マイページへ</a><br>
<form action = "LogOutController"  method = "post"><a href="javascript:abc();">ログアウト</a></form><br>
</div>
<%} %>

<form action = "CartController2"  method = "post" >
<table  border="1" cellspacing="1" cellpadding="8">
  <tbody><tr>
    <th colspan="5" bgcolor="white" >≪あったか～い≫</th>
  </tr>
  <tr bgcolor="#f0aa68">

<%String errorMsg =(String)request.getAttribute("errormoney");%>

<%if(errorMsg != null){%>
<script type="text/javascript">
<!--
		alert("お金が足りません、マイページからお金を入金してください");

//-->
</script>
<%} %>
<%
if(PRList != null)
{
int outputItemCount = 0;
for(int i = 0; i < PRList.size(); i++)
{
	ItemBean PRItem = PRList.get(i);

	if(outputItemCount%5==0)
	{
		%><tr><%
	}
%>
    <td  bgcolor="#ffe4b5">
     <table border = "1" width="180" height="180">
       <tbody><tr><td align="center" rowspan="1"><img width="160" height="160"  alt="" src="<%=PRItem.getImage()%>"
       <%  if(PRItem.getCount().equals("0"))
			{
    	 		%>class="example1"><%
    	  		%><p class = "soldout_style"></p><%
			}   %></td></tr>
       <tr><td align="center"><%=PRItem.getName()%></td></tr>

       <tr><td align="center"><input style="color:#fff;background-color:#FFA500;font-size:15;width:150px;height:30px;border-radius:25px;box-shadow:2px 2px #555;padding-bottom:0px;"
       type="submit"

      <%  if(PRItem.getCount().equals("0"))
			{

    	  		%>disabled
    	  		  value = "売り切れです"
    	  		<%
			}   %>
       value="<%=PRItem.getPrice()%>円"

       onclick = "kansuu(<%=PRItem.getCode()%>,<%=PRItem.getCount()%>)"></td></tr>

     </tbody></table>
    </td><%outputItemCount++; %>
<%
	if(outputItemCount%5==0)
	{
		%></tr><%
	}
}
}
%>
  <tr><th colspan="5" bgcolor="white">≪つめた～い≫</th></tr>
  <%
if(NomalList != null)
{
	int outputItemCount = 0;
for(int i = 0; i < NomalList.size(); i++)
{
	ItemBean NomalItem = NomalList.get(i);
	if(outputItemCount%5==0)
	{
		%><tr><%
	}
%>
  	<td align="center" bgcolor="#EDF7FF">
     <table border = "1" width="180" height="180">
       <tbody><tr><td align="center"><img width="160" height="160" alt="" src="<%=NomalItem.getImage()%>"<%  if(NomalItem.getCount().equals("0"))
			{
    	 	  %>class="example1"><%
    	  		%><p class = "soldout_style"></p><%
			}   %></td></tr>
       <tr><td align="center"><%=NomalItem.getName()%></td></tr>
       <tr><td align="center"><input style="color:#fff;background-color:#49a9d4;font-size:15;width:150px;height:30px;border-radius:25px;box-shadow:2px 2px #555;"
       type="submit"
        <%  if(NomalItem.getCount().equals("0"))
			{
    	  		%>disabled
    	  		value = "売り切れです"
    	  		<%
			}   %>
       value="<%=NomalItem.getPrice()%>円"  onclick = "kansuu2(<%=NomalItem.getCode()%>,<%=NomalItem.getCount()%>);"></td></tr>
       </tbody></table>
    </td><%outputItemCount++; %>
<%
	if(outputItemCount%5==0)
	{
		%></tr><%
	}
}
}
%>
</tbody></table>
</form>
<div align="right">
<a href="#a-box"><font size="3" color="#0000ff">ページ上に戻る</font></a>
</div>
<script type="text/javascript">
<!--
function kansuu(PRItemCode,PRItemCount)
{
	if(PRItemCount == 0)
	{
		return false;
	}
	if(confirm("■商品を購入しますか？")==false)
	{
		return false;
	}
	else
	{
	var u = document.createElement('input');
	u.type = 'hidden';
	u.name = 'PRcode';
	u.value = PRItemCode;
	var c = document.createElement('input');
	c.type = 'hidden';
	c.name = 'PRcount';
	c.value = PRItemCount;
	document.forms[1].appendChild(u);
	document.forms[1].appendChild(c);
	document.forms[1].submit();
	}
}
// -->
</script>
<script type="text/javascript">
<!--
function kansuu2(NomalItemCode,NomalItemCount)
{
	if(confirm("■商品を購入しますか？")==false)
	{
		return false;
	}
	else
	{
	var a = document.createElement('input');
	a.type = 'hidden';
	a.name = 'Nomalcode';
	a.value = NomalItemCode;
	var x = document.createElement('input');
	x.type = 'hidden';
	x.name = 'Nomalcount';
	x.value = NomalItemCount;
	document.forms[1].appendChild(a);
	document.forms[1].appendChild(x);
	document.forms[1].submit();
	}
}
// -->
</script>
<script type="text/javascript">
<!--
function setClass(count,i)
{
	if(count == 0)
	{
		var souldoutid = document.getElementById('soldoutlabel'+i);
		soulddoutid.className="soldout_style";
	}
	else
	{
		var souldoutid = document.getElementById('soldoutlabel'+i);
		souldoutid.className = "";
	};
};
// -->
</script>
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
	document.forms[0].appendChild(a);
	document.forms[0].submit();
	}
}
// -->
</script>

</body>
</html>
