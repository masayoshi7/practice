<?php
header('Content-Type: text/html; charset=UTF-8');
date_default_timezone_set('Asia/Tokyo');
require('function.php');

$error = ''; // エラーチェックに使用する変数
$pas   = 'data/log.txt';

if (!(file_exists('data'))) {
    mkdir('data', 0777);
    chmod('data', '0777');
}

// ファイルがあるか確認
if (!(file_exists($pas))) {
    touch($pas);
}

// 名前のエラーチェック
if (isset($_POST['name'])) {
    if (mb_strlen($_POST['name']) == 0) {
        $error .= '「お名前を入力してください。」';
    } elseif (mb_strlen($_POST['name']) > 30) {
        $error .= '「お名前は30文字以内で入力してください。」';
    } else {
        $name = encryption($_POST['name']);
    }
}

// e-mailのエラーチェック
if (isset($_POST['e-mail'])) {
    if (!(preg_match('/^[a-zA-Z0-9@_.-]*$/', $_POST['e-mail'])) && strlen($_POST['e-mail']) != 0) {
        $error .= '「e-mailは半角英数と記号（@-_.）で入力してください。」';
    } elseif (strlen($_POST['e-mail']) > 100) {
        $error .= '「e-mailは100文字以内で入力してください。」';
    } elseif (strlen($_POST['e-mail']) == 0) {
        $e_mail = '';
    } else {
        $e_mail = $_POST['e-mail'];
    }
}

// タイトルのエラーチェック
if (isset($_POST['title'])) {
    if (mb_strlen($_POST['title']) == 0) {
        $error .= '「タイトルを入力してください。」';
    } elseif (mb_strlen($_POST['title']) > 50) {
        $error .= '「タイトルは50文字以内で入力してください。」';
    } else {
        $title = encryption($_POST['title']);
    }
}

// コメントのエラーチェック
if (isset($_POST['comemnts'])) {
    if (mb_strlen($_POST['comemnts']) == 0) {
        $error .= '「コメントを入力してください。」';
    } elseif (mb_strlen($_POST['comemnts']) > 200) {
        $error .= '「コメントは200文字以内で入力してください。」';
    } else {
        $comemnts = encryption($_POST['comemnts']);
    }
}

// エラーチェックで問題なし、かつデータが送信されていた場合、及びpostメソッドで送信された場合に書き込み処理を行う
if (mb_strlen($error) == 0 && isset($_POST['name']) && isset($_POST['e-mail']) &&
    isset($_POST['title']) && isset($_POST['comemnts']) && $_SERVER['REQUEST_METHOD'] === 'POST') {

    $putData  = $title . '|' . $e_mail . '|' . $name . '|'
              . date('Y年m月d日(w)H:i') . '|' . $comemnts . '?' . "\n"; // 書き込むデータの整形
    $fileData = file_get_contents($pas); // ファイルデータを取得
    $_POST    = array();                 // ポストメソッドを初期化

    // データを書き込み、追記で今までのデータを書き込むことによって新しいデータを前に登録する
    file_put_contents($pas, $putData . $fileData, LOCK_EX);

    // headerメソッドで遷移するとgetメソッドで値が渡されるので再読み込みによる書き込みは行われない
    header('Location:bbs.php');
}

$data = file_get_contents($pas); // ファイルを取得しデータを読み込む
$unit = explode('?', $data);     // データから一回分の書き込みに分割し配列に格納
?>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>ゲストブック</title>
        <link rel="stylesheet" href="./css/bbs.css" type="text/css">
    </head>
    <body>
        <div class="body">
            <h1>ゲストブック</h1>
            <hr>
            <div class="error">
                <?php

                // エラー内容がある場合は出力
                if (mb_strlen($error) != 0) {
                    echo $error;
                }
                ?>
            </div>
            <form method="post" action="bbs.php" name="bbsForm">
                <table class="form">
                    <colgroup>
                        <col span="1">
                        <col span="1">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>お名前/</th>
                            <td><input type="text" name="name" class="small" value="<?php isset($_POST['name']) ? print htmlspecialchars($_POST['name'],  ENT_QUOTES, 'UTF-8') : ''; ?>"></td>
                        </tr>
                        <tr>
                            <th>e-mail/</th>
                            <td><input type="text" name="e-mail" class="medium" value="<?php isset($_POST['e-mail']) ? print htmlspecialchars($_POST['e-mail'],  ENT_QUOTES, 'UTF-8') : ''; ?>"></td>
                        </tr>
                        <tr>
                            <th>タイトル/</th>
                            <td>
                                <input type="text" name="title" class="medium" value="<?php isset($_POST['title']) ? print htmlspecialchars($_POST['title'],  ENT_QUOTES, 'UTF-8') : ''; ?>">
                                <input type="submit" value="投稿する">
                                <input type="button" value="リセット" onclick="clearFormAll()">
                            </td>
                        </tr>
                        <tr>
                            <th class="top">コメント/</th>
                            <td><textarea name="comemnts" cols="60" rows="10"><?php isset($_POST['comemnts']) ? print htmlspecialchars($_POST['comemnts'],  ENT_QUOTES, 'UTF-8') : ''; ?></textarea></td>
                        </tr>
                    </tbody>
                </table>
            </form>
            <hr>
            <?php
            if (isset($unit)) {
                $unitLength = count($unit) - 1;       // 書き込みデータの量を変数に格納
                $count      = $unitLength;
                $pageMath   = ceil($unitLength / 10); // ページ数を求める
                $now        = 1;                      // 現在ページのデフォルトは１とする

                // getmethodのnow変数があれば値を設定
                if (isset($_GET['now'])) {

                    // getmethodで渡された値が実際のページ数より多いときエラーを出し、１ページを表示する。
                    if ($_GET['now'] > $pageMath || $_GET['now'] <= 0) {
                        echo '「不正な値が入力されました。1ページ目を表示します。」';
                        $now = 1;
                    } else {
                        $now = $_GET['now'];
                    }
                } else {
                    $now = 1;
                }
                $dataMath = 10 * ($now - 1); // 現在のページから表示するデータの配列番号を求める
                $no       = $unitLength;

                // 最後のページでは表示するデータを実際のデータ数と同等にする
                if($now == $pageMath) {
                    $no    = $no - $dataMath;
                    $count = $unitLength;
                } elseif ($pageMath <= 1) {
                    $dataMath = 0;
                    $count    = $unitLength;
                } else {
                    $no    = $no - $dataMath;
                    $count = $dataMath + 10;
                }

                // 書き込みを表示するメインループ
                for ($i = $dataMath; $i < $count; $i++) {
                    $write = explode('|', $unit[$i]);
            ?>
                        <table class="article">
                            <tbody>
                                <tr>
                                    <td>
                                        <p class="title">no.<?php echo $no; ?>&nbsp;<span><?php echo htmlspecialchars(decipher($write[0]), ENT_QUOTES, 'UTF-8'); ?></span></p>
                                        投稿者/
                                        <?php

                                        // メールアドレスの有無によってリンクの有無を分岐
                                        if ($write[1] == '') {
                                            echo htmlspecialchars(decipher($write[2]), ENT_QUOTES, 'UTF-8');
                                        } else {
                                        ?>
                                            <a href="mailto:<?php echo htmlspecialchars(decipher($write[1]), ENT_QUOTES, 'UTF-8'); ?>"><?php echo htmlspecialchars(decipher($write[2]), ENT_QUOTES, 'UTF-8'); ?></a>
                                        <?php
                                        }
                                        ?>
                                        <br>
                                        投稿日/ <?php echo toDay($write[3]) ?><br>
                                        <blockquote>
                                            <?php echo nl2br(htmlspecialchars(decipher($write[4]), ENT_QUOTES, 'UTF-8')); ?>
                                        </blockquote>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
            <?php
                    $no--;
                }

                // ページの最初では表示しない
                if ($now != 1) {
                    $backPage = $now - 1;
                    echo '<a href="bbs.php?now=" . $backPage . ""><input type="button" value="前へ" /></a>';
                }

                // ページの最後まで来たら表示しない
                if($now != $pageMath && $pageMath > 1) {
                    $nextPage = $now + 1;
                    echo '<a href="bbs.php?now=" . $nextPage . ""><input type="button" value="次へ" /></a>';
                }
            }
            ?>
            <br>
            <br>
        </div>
        <script src="js/bbs.js"></script>
    </body>
</html>
