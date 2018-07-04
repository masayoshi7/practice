<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>ログイン</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.4 -->
        <link href="plugins/AdminLTE/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- Font Awesome Icons -->
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!-- Theme style -->
        <link href="plugins/AdminLTE/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
        <!-- iCheck -->
        <link href="plugins/iCheck/square/blue.css" rel="stylesheet" type="text/css" />
    </head>
    <body class="login-page">
        <div class="login-box">

            <div class="login-logo">
                <a href="">自動販売機ログイン</a>
            </div>

            <div class="login-box-body">
                <p class="login-box-msg">入力してください</p>
<%
// エラーメッセージ出力処理
String errorMsg =(String)request.getAttribute("error");
if(errorMsg != null) {
%>
                <p style="text-align:center;color:red;"><%=errorMsg %></p>
<% } %>
                <form action="LogInController" method="post">

                    <!-- ユーザーネーム -->
                    <div class="form-group has-feedback">
                        <input type="text" class="form-control" name="name" id="name" placeholder="Username"/>
                    </div>

                    <!-- パスワード -->
                    <div class="form-group has-feedback">
                        <input type="password" class="form-control" name="pas" id="pas" placeholder="Password" />
                    </div>
                    <div class="row">
                        <div class="col-xs-8 col-xs-offset-2" style="margin-bottom:10px;">
                            <button type="submit" class="btn btn-primary btn-block btn-flat">ログイン</button>
                        </div>
                        <div class="col-xs-8 col-xs-offset-2">
                            <a href="./NewAcount.jsp">
                                <button type="button" class="btn btn-primary btn-block btn-success">新規登録</button>
                            </a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <!-- jQuery 2.1.4 -->
        <script src="plugins/AdminLTE/js/jquery.min.js" type="text/javascript"></script>
        <!-- Bootstrap 3.3.2 JS -->
        <script src="plugins/AdminLTE/js/bootstrap.min.js" type="text/javascript"></script>
        <!-- iCheck -->
        <script src="plugins/iCheck/icheck.min.js" type="text/javascript"></script>
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
            },stepValidation: true});
        </script>
    </body>
</html>
