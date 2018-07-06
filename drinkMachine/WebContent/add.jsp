<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <title>商品追加</title>
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
                            <h3 class="box-title">商品追加</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <section class="container">
                                <% String errorMsg =(String)request.getAttribute("result"); %>
                                <% if(errorMsg != null) { %>
                                       <p><%=errorMsg %></p>
                                <% } %>
                                <form action = "AddController" method = "post" enctype = "multipart/form-data" class="form-horizontal">
                                    <div class="form-group">
                                        <div class="col-xs-offset-1 col-xs-10">
                                                                                                    「 <font class="text-danger">*</font>」は必須となります
                                        </div>
                                    </div>
                                    <div class="form-group">
                                    <div class="form-group">
                                        <label class="control-label col-xs-2">商品名<sup><font class="text-danger">*</font></sup></label>
                                        <div class="col-xs-5">
                                            <input type="text" id="name" name="name" value="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-xs-2">価格<sup><font class="text-danger">*</font></sup></label>
                                        <div class="col-xs-5">
                                            <input type="text" id="unitPrice" name="unitPrice" value="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-xs-2">数量<sup><font class="text-danger">*</font></sup></label>
                                        <div class="col-xs-5">
                                            <input type="text" id="count" name="count" value="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-xs-2">商品画像<sup><font class="text-danger">*</font></sup></label>
                                        <div class="col-xs-5">
                                            <input type="file" id="image" name="image">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-xs-2">商品区分</label>
                                        <div class="col-xs-5">
                                            <input type="hidden" id ="isPR" name="isPR" value="0">
                                            <input type="checkbox" id="isPR" name="isPR" value="1"{% if item.isPR %} checked{% endif %}>おすすめ商品
                                        </div>
                                    </div>
                                        <div class="col-xs-offset-2 col-xs-10">
                                            <button type = "submit" class="btn btn-success" onclick = "if(confirm('商品を登録しますか？')) {
                                                                                    return true;
                                                                              } else {
                                                                                return false;
                                                                            }"> 登録</button>
                                        </div>
                                    </div>
                                </form>
                                <br>
                            </section>
                        </div>
                        <div class="box-footer">
                        </div>
                    </div>
                </section>
                <!-- end Main content -->
            <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->
            <!-- Main Footer -->
            <footer class="main-footer">
                <div class="pull-right hidden-xs">
                    <b>Version</b> 1.0.0
                </div>
            </footer>
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
    </body>
</html>
