<?php
header('Content-Type: text/html; charset=UTF-8');
require 'function.php';
class OpenException extends Exception
{
}
class WriteException extends Exception
{
}

// ファイルへのパス
$pas = 'count.txt';
try {

    // 指定したファイルが無ければ作成
    if (!(file_exists($pas))) {
        file_put_contents($pas, 0);
    }

    // ファイルを読み書き権限で取得
    $fp = fopen($pas, 'r+');
    if ($fp == false) {
        throw new \OpenException('ファイルの読み込みに失敗しました。');
    }

    // ファイルを占有ロック
    flock($fp, LOCK_EX);

    // ファイルからデータを読み込む
    $count = fgets($fp);

    // visited変数がない場合のみアクセスカウンターを上昇させる
    if (isset($_COOKIE['visit'])) {
        echo '30秒以内の二回目以降の訪問はカウントされません';
    } else {

        // 訪問フラグを立てる
        setcookie('visit', 'yes', time() + 30);

        // カウンダを1増加させる
        $count++;

        // ポインタを先頭に戻す
        rewind($fp);

        // ファイルにデータ分だけを書き込み
        $error = fwrite($fp, $count, strlen($count));
        if ($error == false) {
            throw new \WriteException('ファイルへの書き込みに失敗しました。');
        }
    }
} catch (\OpenException $e) {
    echo '例外が投げられました: ' . $e -> getMessage() . '</br>';

    // 対応する数字の画像を出力
    echo imagePrint(0);
} catch (\WriteException $e) {
    echo '例外が投げられました: ' . $e -> getMessage();
} finally {
    if ($fp != false) {

        // 占有ロックを解除
        flock($fp, LOCK_UN);

        // ファイルを閉じる
        fclose($fp);
    }
}
?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>counter</title>
    </head>
    <body>
        <p id="counter">
            <?php

            // 画像表示メソッド呼び出し
            echo imagePrint($count);
            ?>
        </p>
        <input type="button" value="リセットボタン" id="resetBotton"/>
        <script src="js/jquery.min.js"></script>
        <script src="js/counter.js"></script>
    </body>
</html>
