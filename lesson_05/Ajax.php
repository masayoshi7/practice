<?php
header('charset=UTF-8');
require 'function.php';

/*
* 「処理内容」
* jqueryによりリクエストされたこのphpファイルがデータの初期化処理を行う
* まず、テキストファイルに0を書き込む。その後アクセスカウンターがすべて0の
* パスの画像文字列を生成し呼び出し元に返す。
* その後呼び出しもとで既に表示されている、アクセスカウンターと入れ替える。
*/
$pas = 'count.txt';

// データを初期化し書き込む
file_put_contents($pas, '0|0|0|00000000');

// 画像表示メソッドに0を渡して画像プリント変数を作成する
$image = imagePrint(0);

// 指定されたデータタイプに応じたヘッダーを出力する
header('Content-type: application/json');

// 画像プリント変数を呼び出し元へ帰す
echo json_encode($image);
