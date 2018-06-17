<?php
header('Content-Type: text/html; charset=UTF-8');
mb_internal_encoding('utf-8');
mb_language('ja');

require('function.php');

// 以下メインプログラム
$content    = '';
$mailResult = '';
$error      = checkContent(); // 送信内容のnullチェック、および字数制限を行う
if (mb_strlen($error) != 0) {
    $errorFlag = true;
} else {

    // flagクッキーが存在している場合は二回目の処理とする
    if (isset($_COOKIE['flag'])) {
        $errorFlag = true;
        $error     = '再読み込みによるメール送信は行われません';
    } else {

        // 以下メール送信処理
        $content = '・名前'     . "\n" . $_POST['name'] . "\n"
                 . '・年齢'     . "\n" . $_POST['age'] . "\n"
                 . '・利用頻度'  . "\n" . $_POST['frequency'] . "\n"
                 . '・印象'      . "\n" . $_POST['impression'] . "\n"
                 . '・探しやすさ' . "\n" . $_POST['facilitate'] . "\n";
        if (isset($_POST['reason'])) {
            $content .= '・見つからなかった理由' . "\n" . $_POST['reason'] . "\n";
        }
        $content .= '・掲載情報について' . "\n" . $_POST['advance'] . "\n"
                  . '・関心のある分野' . "\n";
        foreach ($_POST['interest'] as $value) {
            $content .= $value . "\n";
        }
        $content   .= '・その他' . "\n" . $_POST['other'] . "\n";

        $to         = mb_encode_mimeheader('西口正祥', 'ISO-2022-JP-MS') . '<nishiguchi@adinte.co.jp>';
        $title      = 'ここでは十分に長い日本語の題名（subject）をつけたタイトルにしています、ｱｲｳｴｵ';
        $title      = mb_encode_mimeheader($title, 'ISO-2022-JP-MS');
        $from       = 'From:' . mb_encode_mimeheader('西田', 'ISO-2022-JP-MS') . '<nishida@adinte.co.jp>';

        // sendMail関数で文字コードの設定と送信を行う
        if (mail($to, $title, $content, $from)) {
            $mailResult = 'ご協力ありがとうございました！';
        } else {
            $mailResult = 'メールの送信に失敗しました。';
        }

        // 初回読み込みか再読み込みか判断する
        setcookie('flag', 'yes');
    }
}
?>
<html>
    <head>
        <title>アンケート入力ページ</title>
    </head>
    <body>
        <?php
        if (isset($errorFlag)) {
            echo $error;
        } else {
            echo $mailResult;
        }
        ?>
    </body>
</html>
