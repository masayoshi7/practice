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
        <title>購入画面</title>
        <link rel="stylesheet" type="text/css" href="./css/DrinkMachine.css" >
        <link rel="stylesheet" href="plugins/AdminLTE/css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="plugins/AdminLTE/css/AdminLTE.min.css" type="text/css">
        <link rel="stylesheet" href="plugins/AdminLTE/css/skins/skin-blue.css" type="text/css">
        <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
    </head>
    <body>
	    <header style="background:#a3d1ff;position:fixed;top:0;width:100%;z-index: 999;">
	        <h1 style="color:#FFF;margin-left:15%;display: inline-block;">購入ページ</h1>
<% if (acount != null) { %>
            <div class="pull-right" style="display: inline-block;margin-right:50px;margin-top:20px;">
                <span><%=acount.getName()%>さん</span>
                <button type="button" id="acountDetail" class="btn btn-primary">詳細</button>
                <div id="acountMenu" style="background:#FFF;border:solid 1px #000;display:none;position: fixed;">
	                <p>所持金は<%=acount.getMoney()%>円です</p>
	                <a href="./list.jsp">商品管理へ</a><br>
	                <a href="./money.jsp">入金</a><br>
	                <a href="LogOutController">ログアウト</a><br>
                </div>
            </div>
<% } else { %>
            <a href="./login.jsp"><button type="button" name="registry" id="registry" style="margin-right:50px;margin-top:20px;" class="pull-right btn btn-info">ログイン</button></a>
<% } %>
	    </header>
        <main style="margin-top:100px;margin-left:150px";>
            <form action="CartController2"  method="post">
                <table  border="1" cellspacing="1" cellpadding="8">
                    <tbody>
                        <tr>
                            <th colspan="5" bgcolor="white" style="text-align:center;" >≪おすすめ商品≫</th>
                        </tr>
                        <tr bgcolor="#f0aa68">
<%
String errorMsg =(String)request.getAttribute("errormoney");
if (errorMsg != null) {
%>
                        <script type="text/javascript">
                        	alert("お金が足りません、マイページからお金を入金してください");
                        </script>
<%
}
if (PRList != null) {
    int outputItemCount = 0;
    for (int i = 0; i < PRList.size(); i++) {
        ItemBean PRItem = PRList.get(i);
        if (outputItemCount % 5 == 0) {
%>
                        <tr>
<%      } %>
                        <td  bgcolor="#ffe4b5">
                            <table border = "1" width="180" height="180">
                                <tbody>
                                    <tr>
                                        <td align="center" rowspan="1"><img width="160" height="160"  alt="" src="<%=PRItem.getImage()%>"
                                            <% if (PRItem.getCount().equals("0")) { %>
                                                class="example1">
                                            <p class = "soldout_style"></p>
                                            <% } %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center"><%=PRItem.getName()%></td>
                                    </tr>
                                    <tr>
                                        <td align="center">
                                            <input type="hidden" class="code" value="<%=PRItem.getCode()%>">
                                            <input type="hidden" class="count" value="<%=PRItem.getCount()%>">
                                            <input class="buy" type="button" value="<%=PRItem.getPrice()%>円"
                                            style="color:#fff;background-color:#FFA500;font-size:15;width:150px;height:30px;border-radius:25px;box-shadow:2px 2px #555;padding-bottom:0px;"
                                            <% if (PRItem.getCount().equals("0")) { %>
                                                 disabled value = "売り切れです"
                                            <% } %>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
<%
        outputItemCount++;
        if (outputItemCount % 5 == 0) {
%>
                        </tr>
<%
        }
    }
}
%>
                        <tr>
                            <th colspan="5" bgcolor="white" style="text-align:center;">≪通常商品≫</th>
                        </tr>
<%
if (NomalList != null) {
    int outputItemCount = 0;
    for (int i = 0; i < NomalList.size(); i++) {
        ItemBean NomalItem = NomalList.get(i);
        if (outputItemCount % 5 == 0) {
%>
                        <tr>
<%
}
%>
                        <td align="center" bgcolor="#EDF7FF">
                            <table border = "1" width="180" height="180">
                                <tbody>
                                    <tr>
                                        <td align="center"><img width="160" height="160" alt="" src="<%=NomalItem.getImage()%>"<%  if (NomalItem.getCount().equals("0")) {
                                            %>class="example1"><%
                                            %><p class = "soldout_style"></p><%
                                            }   %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center">
                                            <%=NomalItem.getName()%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center">
                                            <input type="hidden" class="code" value="<%=NomalItem.getCode()%>">
                                            <input type="hidden" class="count" value="<%=NomalItem.getCount()%>">
                                            <input class="buy" style="color:#fff;background-color:#49a9d4;font-size:15;width:150px;height:30px;border-radius:25px;box-shadow:2px 2px #555;" type="button"
<% if (NomalItem.getCount().equals("0")) { %>
                                                disabled value = "売り切れです"
<% } %>
                                                value="<%=NomalItem.getPrice()%>円"  />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
<%
		outputItemCount++;
		if (outputItemCount % 5 == 0) {
%>
                    </tr>
<%
        }
    }
}
%>
                    </tbody>
                </table>
            </form>
        </main>
        <div align="right">
        <a href="#a-box"><font size="3" color="#0000ff">ページ上に戻る</font></a>
        </div>
        <!-- jQuery 2.1.4 -->
        <script src="plugins/AdminLTE/js/jquery.min.js"></script>
        <!-- Bootstrap -->
        <script src="plugins/AdminLTE/js/bootstrap.min.js"></script>
        <!-- AdminLTE App -->
        <script src="plugins/AdminLTE/js/adminlte.min.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="plugins/AdminLTE/js/demo.js"></script>
        <script type="text/javascript">
            $(function() {

                // アカウントメニューの開閉処理
                $("#acountDetail").on("click",function(){
                    $("#acountMenu").toggle();
                });

                // 購入ボタン押下時の購入処理
                $(".buy").on("click",function(){
                    if (confirm("商品を購入しますか？")) {
                        var code   = $(this).prevAll(".code").val();
                        var count  = $(this).prevAll(".count").val();
                        var action = "BuyController?code=" + code + "&count=" + count;
                        window.location.href = action;
                    }
                });
            });

	        function setClass(count,i)
	        {
	            if (count == 0) {
	                var souldoutid = document.getElementById('soldoutlabel'+i);
	                soulddoutid.className="soldout_style";
	            } else {
	                var souldoutid = document.getElementById('soldoutlabel'+i);
	                souldoutid.className = "";
	            };
	        }
        </script>
    </body>
</html>
