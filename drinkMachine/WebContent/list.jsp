<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.*,java.util.*,java.sql.*,javax.naming.*,drinkMachine.*,drinkMachine.Dao.*"%>
<%List<ItemBean> itemList = (List<ItemBean>)session.getAttribute("itemList");%>
<%String errorMsg = (String)request.getAttribute("kekka");%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <title>商品検索</title>
        <link rel="stylesheet" href="plugins/AdminLTE/css/bootstrap.min.css" type="text/css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="plugins/AdminLTE/css/AdminLTE.min.css" type="text/css">
        <link rel="stylesheet" href="plugins/AdminLTE/css/skins/skin-blue.css" type="text/css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
        <link rel="stylesheet" type="text/css" href="./css/DrinkMachine.css" >
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">
    <header class="main-header">
        <a href="#" class="logo">
            <span class="logo-lg"><b>商品管理</b>画面</span>
            <span class="logo-mini"><b>S</b>KG</span>
        </a>
        <nav class="navbar navbar-static-top">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <li class="dropdown user user-menu">
                        <!--
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <span class="hidden-xs">さん</span>
                        </a>
                        -->
                        <ul class="dropdown-menu">
                            <!-- User image -->
                            <li class="user-header">
                                <p>
                                </p>
                            </li>
                            <!-- Menu Body -->
                            <li class="user-body">
                                <div class="row">
                                    <div class="col-xs-4 text-center">
                                        <a href="#">Followers</a>
                                    </div>
                                    <div class="col-xs-4 text-center">
                                        <a href="#">Sales</a>
                                    </div>
                                    <div class="col-xs-4 text-center">
                                        <a href="#">Friends</a>
                                    </div>
                                </div>
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat">Profile</a>
                                </div>
                                <div class="pull-right">
                                    <a href="#" class="btn btn-default btn-flat">Sign out</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
    </header>

    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <div class="sidebar">
            <!-- Sidebar Menu -->
            <ul class="sidebar-menu tree" data-widget="tree">
                <li class="header text-font">MAIN&nbsp;NAVIGATION</li>
                <!-- Optionally, you can add icons to the links -->
                <li class="treeview">
                    <li><a href="./add.jsp"><i class="fa fa-circle-o"></i>商品追加</a></a></li>
                    <li><a href="./list.jsp"><i class="fa fa-circle-o"></i>商品検索</a></a></li>
                    <li><a href="<%=request.getContextPath().toString()%>/CartController"><i class="fa fa-circle-o"></i>商品販売画面</a></a></li>
                </li>
            </ul>
        <!-- /.sidebar-menu -->
        </div>
    <!-- /.sidebar -->
    </aside>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        商品管理
                        <small class="text-font">Product management</small>
                    </h1>
                </section>
                <!-- Main content -->
                <section class="content container-fluid">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">商品検索</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <section class="container">
<% if (errorMsg != null) { %>
								<div class=" alert  alert-dismissible" id="warningMsg"">
		                            <ul>
		                                <%=errorMsg %>
		                            </ul>
                        		</div>
<% } %>
                                <form action = "ListController"  method = "post" class="form-horizontal">
                                    <div class="form-group col-xs-7">
                                        <label for="ID">
                                            商品ID
                                        </label>
                                        <input type="text" name="code" class="form-control col-ms-3" placeholder="商品ID">
                                    </div>
                                    <div class="form-group col-xs-7">
                                        <label for="name">
                                            商品名
                                        </label>
                                        <input type="text" class="form-control" name="name" placeholder="商品名">
                                    </div>
                                    <div class="check form-group col-xs-7">
                                        <label>
                                                商品価格
                                        </label>
                                        <div>
                                            <input type="radio" name="type" value="1" checked>全ての商品
                                            <input type="radio" name="type" value="2">おすすめ商品のみ
                                            <input type="radio" name="type" value="3">通常商品のみ
                                            <input type="checkbox" name="isSoldout" value="1">売り切れ商品
                                            <input type="hidden" name="isSoldout" value="0">
                                        </div>
                                    </div>
                                    <div class="col-xs-10">
                                        <button type="submit" class="btn btn-primary">検索</button>
                                    </div>
                                </form>
                                <br></br>
                                <!--
                                <form action = "CSVController"  method = "post" style="margin-top:15px;" class="col-xs-12"  enctype="multipart/form-data">
                                    <input onclick = "addCsv()" class="btn btn-info" type="submit" value="csv追加">
                                </form>
                                -->
                                <form action = "CSVGetController" style="margin-top:15px;" class="col-xs-12"  method = "post">
                                    <input onclick = "getCsv()" class="btn btn-info" type="submit" value="csv出力">
                                </form>
                            </section>
                        </div>
                        <div class="box-footer">
<%

if (itemList != null) {
    // 商品検索ループ
    int itemListSize = itemList.size();
    for (int i = 0; i < itemListSize; i++) {
        ItemBean selectedItm = itemList.get(i);
 %>
                            <div class="box-body table-responsive no-padding">
                                <table class="table" style="table-layout:fixed;">
                                    <thead>
                                        <tr>
                                            <th style="width: 10px;">商品ID</th>
                                            <th style="width: 30px;">商品名</th>
                                            <th style="width: 15px;">商品価格</th>
                                            <th style="width: 15px;">在庫数</th>
                                            <th style="width: 20px;"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td><%= selectedItm.getCode() %></td>
                                            <td><%= selectedItm.getName() %></td>
                                            <td><%= selectedItm.getPrice() %></td>
                                            <td><%= selectedItm.getCount() %></td>
                                            <td>
                                                <a href="DelController?id=<%= selectedItm.getCode() %>" onclick="return confirm('本当に削除しますか？');"><button class="btn btn-danger">削除</button></a>
                                                <a href="EditController?id=<%= selectedItm.getCode() %>" ><button class="btn btn-primary">変更</button></a>
                                                <a href="ViewController?id=<%= selectedItm.getCode() %>"><button class="btn btn-info">詳細</button></a>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
<%
    }
}
%>
                        </div>
                    </div>
                </section>
                <!-- end Main content -->
            <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->
            <!-- Main Footer -->
            <jsp:include page="temp/footer.jsp"/>
            <div class="control-sidebar-bg"></div>
        </div>
        <!-- jQuery 2.1.4 -->
        <script src="plugins/AdminLTE/js/jquery.min.js"></script>
        <!-- Bootstrap -->
        <script src="plugins/AdminLTE/js/bootstrap.min.js"></script>
        <!-- AdminLTE App -->
        <script src="plugins/AdminLTE/js/adminlte.min.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="plugins/AdminLTE/js/demo.js"></script>
        <SCRIPT type="text/javascript">
            /*
            function addCsv(){
                if (confirm("csvファイルを登録しますか？")) {
                    var cs = document.createElement('input');
                    cs.type = 'file';
                    cs.name = 'csv';
                    document.forms[2].appendChild(cs);
                    cs.click();
                    document.forms[2].submit();
                }
            }
            }*/

            function getCsv(list){
                if (confirm("csvファイルを出力しますか")) {
                    var hh = document.createElement('input');
                    hh.type = 'hidden';
                    hh.name = 'csv2';
                    document.forms[3].appendChild(hh);
                    document.forms[3].submit();
                }
            }
        </SCRIPT>
    </body>
</html>
