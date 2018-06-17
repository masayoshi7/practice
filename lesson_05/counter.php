<?php
require 'date_override.php';
require 'function.php';

// 文字形式をutf-8にし日本語に対応
header('Content-Type: text/html; charset=UTF-8');

// タイムゾーンを日本の東京に設定
date_default_timezone_set('Asia/Tokyo');
class OpenException extends Exception
{
}
class WriteException extends Exception
{
}

// ファイルへのパス
$pas = 'count.txt';

// jsの多重アクセス判定に使用する変数
$flag = 'no';

// 一か月間のエポック秒
$lifeTime = 2592000;
try {
    if (file_exists($pas)) {

        // ファイルを取得しデータを読み込む
        $data = file_get_contents($pas);
    } else {
        $result = file_put_contents($pas, '0|0|0|00000000');
        $data   = file_get_contents($pas);
        if (!($result)) {
            throw new \OpenException('ファイルの読み込みに失敗しました。');
        }
    }

    // [0]がすべてのカウント,[1]が今日のカウント、[2]が昨日のカウント、[3]が日付のデータとなるarray配列を受け取る
    $array = explode('|', $data);

    // クッキーの有効期限を判定する
    timeCheckCookie($array[3]);

    // １日以内に訪れていた場合の処理
    if (isset($_COOKIE['visit'])) {

        // 訪問回数カウント変数
        $_COOKIE['count']++;

        // クッキーの値をjavascriptに渡すため格納
        $visitedCount = $_COOKIE['count'];

        // クッキーを格納しなおす
        setcookie('count', $_COOKIE['count'], time() + $lifeTime);

        // このフラグによって吹き出しのjsを起動させる
        $flag = 'yes';
    } else {

        /*
        * クッキーをセット
        * このクッキーは一日以内に訪問したかどうかの判定に使用する
        * ブラウザが閉じてもクッキーが消えないように設定
        */
        setcookie('visit', time(), time() + $lifeTime);

        /*
        * このクッキーは一日以内に何回訪れたかをカウントする
        * ブラウザが閉じてもクッキーが消えないように設定
        */
        setcookie('count', 1, time() + $lifeTime);

        // 日付の判定をfunction.phpで行い変更が行われた配列arrayを受け取る
        $array = dateCheck($array);

        // 本日と全ての訪問者数のカウンダを1増加させる
        $array[0]++;
        $array[1]++;

        // ファイルにデータを書き込む
        $putData = $array[0] . '|' . $array[1] . '|' . $array[2] . '|' . $array[3];
        $error   = file_put_contents($pas, $putData);
        if ($error == false) {
            throw new \WriteException('ファイルへの書き込みに失敗しました。');
        }
    }
} catch (\OpenException $e) {
    echo '例外が投げられました: ' . $e->getMessage() . '</br>';

    // 対応する数字の画像を出力
    echo imagePrint(0);
} catch (\WriteException $e) {
    echo '例外が投げられました: ' . $e->getMessage();
}
?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="css/counter.css" type="text/css">
        <title>Accesscounter</title>
    </head>
    <body>
        <div class="center">
            <h6 class="title">Welcom Accesscounter</h6>
            <p id="counter" class="poo">
                <?php

                // 画像表示メソッド呼び出し
                echo imagePrint($array);
                ?>
            </p>
            <input type="button" value="リセットボタン" id="resetBotton" class="reset"/>
        </div>
        <?php
        if ($flag == 'yes') {

            // この変数を作成することでcounter.jsにて熊の吹き出しを出力する
            echo '<script language=javascript> var flag = "yes"; </script>';

            // javascript変数ににphp変数の値を渡しcounter.jsで値を取得する
            echo "<script language=javascript> var count=$visitedCount</script>";
        }
        ?>
        <script src="js/jquery.min.js"></script>
        <script src="js/jquery.balloon.min.js"></script>
        <script src="js/counter.js"></script>
    </body>
</html>
