<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*,javax.naming.*,drinkMachine.*,drinkMachine.Dao.*"%>
<%ItemBean itembean = (ItemBean)request.getAttribute("editDate");%>
<html xml:lang="ja" lang="ja">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>詳細画面</title>
        <link rel="stylesheet" type="text/css" href="./css/DrinkMachine.css" >
    </head>
    <body>
        <h1>商品照会</h1>
        <a href="./list.jsp">一覧</a>
        <br>
        <table class = "table1">
            <tbody>
                <tr>
                    <th class="tr1">商品コード<sup><font color="#ff0000">*</font></sup></th>
                    <td class="td1"><%=itembean.getCode()%></td>
                </tr>
                <tr>
                    <th class="tr1">商品名<sup><font color="#ff0000">*</font></sup></th>
                    <td class="td1"><%=itembean.getName()%></td>
                </tr>
                <tr>
                    <th class="tr1">金額<sup><font color="#ff0000">*</font></sup></th>
                    <td class="td1"><%=itembean.getPrice()%></td>
                </tr>
                <tr>
                    <th class="tr1">数量<sup><font color="#ff0000">*</font></sup></th>
                    <td class="td1"><%=itembean.getCount()%></td>
                </tr>
                <tr>
                    <th class="tr1">商品画像</th>
                    <td class="td1"><img width="80" height="80" alt="" src="<%=itembean.getImage()%>"></td>
                </tr>
                <tr>
                    <th class="tr1">商品区分</th>
                    <td class="td1"><input type="checkbox" name="isPR" disabled="disabled" value="True"<%if(a.equals("1")){ %> checked <%} %>{% endif %}>おすすめ商品</input></td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
