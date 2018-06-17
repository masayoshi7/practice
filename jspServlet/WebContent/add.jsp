<html xml:lang="ja" lang="ja">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>商品登録</title>
        <link rel="stylesheet" type="text/css" href="./css/DrinkMachine.css" >
    </head>
    <body>
        <h1>商品登録</h1>
        <% String errorMsg =(String)request.getAttribute("result"); %>
        <% if(errorMsg != null) { %>
               <p><%=errorMsg %></p>
        <% } %>
        <a href="./list.jsp">一覧</a><br><br>
        <form action = "AddController" method = "post" enctype = "multipart/form-data">
            <table class = "table1">
              <tbody>
                  <tr>
                    <th class="tr1">商品コード</th>
                    <td class="td1"><input type="text" id="code" name="code" readonly = "readonly" value="自動で割り振られます"> </td>
                  </tr>
                  <tr>
                    <th class = "tr1">商品名<sup><font class = "common">*</font></sup></th>
                    <td class="td1"><input type="text" id="name" name="name" value=""> </td>
                  </tr>
                  <tr>
                    <th class = "tr1">金額<sup><font class = "common">*</font></sup></th>
                    <td class="td1"><input type="text" id="unitPrice" name="unitPrice" value=""> </td>
                  </tr>
                  <tr>
                    <th class = "tr1">数量<sup><font class = "common">*</font></sup></th>
                    <td class="td1"><input type="text" id="count" name="count" value=""> </td>
                  </tr>
                  <tr>
                    <th class = "tr1">商品画像</th>
                    <td class="td1"><input type="file" id="image" name="image"> </td>
                  </tr>
                  <tr>
                    <th class = "tr1">商品区分</th>
                    <td class="td1">  <input type="hidden" id ="isPR" name="isPR" value="0"><input type="checkbox" id="isPR" name="isPR" value="1"{% if item.isPR %} checked{% endif %}>あったかい(チェックなし:つめたい)</td>
                  </tr>
              </tbody>
          </table><br>
          <input type = "submit" onclick = "if(confirm('商品情報を登録しますか?')) {
                                                return true;
                                              } else {
                                                return false;
                                              }" value ="登録する">
        </form>
        <br>
        <font color="#ff0000">*</font>は必須項目
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
    unitPrice: "chkrequired chknumonly chkmin1 chkmax4 chk0 chknum1000",
    count: "chkrequired chknumonly chkmin1 chkmax3 chknum100",
    filename: "chkfile"
    },
    stepValidation: true
    });
    </script>
</html>
