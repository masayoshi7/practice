<?php
header('Content-Type: text/html; charset=UTF-8');
date_default_timezone_set('Asia/Tokyo');
require('function.php');
require('DBfunction.php');

// DBに接続するための情報変数
define('IP', 'mysql:host=127.0.0.1;dbname=nishiguchi;charset=utf8');
define('USER', 'nishiguchi');
define('PASS', 'nishiguchi');

$pdo   = new PDO(IP, USER, PASS);
$error = ''; // エラーチェックに使用する変数

// 名前のエラーチェック
if (isset($_POST['name'])) {
    if (blancCheck($_POST['name']) || strlen($_POST['name']) === 0) {
        $error .= '「お名前を入力してください。」';
    } elseif (mb_strlen($_POST['name']) > 30) {
        $error .= '「お名前は30文字以内で入力してください。」';
    } else {
        $name = $_POST['name'];
    }
}

// パスワードのエラーチェック
if (isset($_POST['password'])) {
    if (blancCheck($_POST['password']) || strlen($_POST['password']) === 0) {
        $passwordExist = false;
    } elseif (mb_strlen($_POST['password']) > 8) {
        $error .= '「パスワードは8文字以内で入力してください。」';
    } else {
        $password = $_POST['password'];
        $passwordExist = true;
    }
} else {
    $passwordExist = false;
}

// e-mailのエラーチェック
if (isset($_POST['e-mail'])) {
    if (strlen($_POST['e-mail']) > 100) {
        $error .= '「e-mailは100文字以内で入力してください。」';
    } elseif (!(preg_match('/^[\w.-]+(?!\w.-)*@(?!\w.-)*[\w.-]+$/', $_POST['e-mail'])) && strlen($_POST['e-mail']) !== 0) {
        $error .= '「e-mailは半角英数と記号（@-_.）で入力してください。」';
    } else {
        $eMail = $_POST['e-mail'];
    }
}

// タイトルのエラーチェック
if (isset($_POST['title'])) {
    if (blancCheck($_POST['title']) || strlen($_POST['title']) === 0) {
        $error .= '「タイトルを入力してください。」';
    } elseif (mb_strlen($_POST['title']) > 50) {
        $error .= '「タイトルは50文字以内で入力してください。」';
    } else {
        $title = $_POST['title'];
    }
}

// コメントのエラーチェック
if (isset($_POST['comment'])) {
    if (blancCheck($_POST['comment']) || strlen($_POST['comment']) === 0) {
        $error .= '「コメントを入力してください。」';
    } elseif (mb_strlen($_POST['comment']) > 200) {
        $error .= '「コメントは200文字以内で入力してください。」';
    } else {
        $comment = $_POST['comment'];
    }
}

// パスワードチェック
if (isset($_POST['userInputPassword'])) {

    // DBよりパスワードを取得する
    $answerPassword = bbsPasswordCheck($pdo, $_POST['id']);

    if (mb_strlen($_POST['userInputPassword']) > 8) {
        $error .= '「入力するパスワードは8文字以内で入力してください。」';
    } elseif ($answerPassword[0] != $_POST['userInputPassword']) {
        $error .= '「パスワードが異なっています。」';
    }
}

session_start();

if (isset($_POST['id'])) {

    // 更新or削除処理
    if (isset($_POST['type'])) {
        if ($_POST['type'] === 'delete' && strlen($error) === 0) {
            $pdo->beginTransaction();

            /*
            * 削除した内容をlogテーブルにも書き込む
            * 途中で例外が起きた場合ロールバックする
            */
            try{
                bbsDelete($pdo, $_POST['id']);
                logInsert($pdo, $_POST['id']);
                $pdo->commit();
            } catch(PDOException $e){
                $pdo->rollback();
            }
        }
        if ($_POST['type'] === 'update' && strlen($error) === 0) {
            if ($passwordExist) {
                $updateArray = [
                    $name,
                    $title,
                    $eMail,
                    $comment,
                    $password,
                    $_POST['id']
                ];
            } else {
                $updateArray = [
                    $name,
                    $title,
                    $eMail,
                    $comment,
                    $_POST['id']
                ];
            }
            $pdo->beginTransaction();

            /*
            * 更新した内容をlogテーブルにも書き込む
            * 途中で例外が起きた場合ロールバックする
            */
            try{
                bbsUpdate($pdo, $updateArray, $_POST['id'], $passwordExist);
                logInsert($pdo, $_POST['id']);
                $pdo->commit();
            } catch(PDOException $e){
                $pdo->rollback();
            }
        } elseif ($_POST['type'] === 'update' && strlen($error) !== 0) {
            $updateErrorFlag = true;
        }
    }
} else {

    // エラーチェックで問題なし、かつデータが送信されていた場合、及びpostメソッドで送信された場合に書き込み処理を行う
    if (strlen($error) == 0 && isset($name) && isset($eMail) &&
        isset($title) && isset($comment) && $_SERVER['REQUEST_METHOD'] === 'POST') {

        // 保存したキーとpostで送られてきたキーが同じ場合は、最初の投稿とみなす
        if ($_SESSION['key'] == $_POST['flag']) {
            $insertArray = [
                $name,
                $eMail,
                $title,
                $comment,
                date('Y-m-d H:i:s')
            ];
            if ($passwordExist) {
                array_push($insertArray, $password);
            }
            $pdo->beginTransaction();

            /*
            * 挿入した内容をlogテーブルにも書き込む
            * 途中で例外が起きた場合ロールバックする
            */
            try{
                bbsInsert($pdo, $insertArray, $passwordExist);
                logInsert($pdo, $pdo->lastInsertId('id'));
                $pdo->commit();
            } catch(PDOException $e){
                $pdo->rollback();
            }
        } else {
            $error = '「再読み込みによる投稿は行われません。」';
        }
        $_POST['name']     = '';
        $password          = '';
        $_POST['eMail']    = '';
        $_POST['title']    = '';
        $_POST['comment']  = '';
    }
}

// 削除or変更処理はデータの再現を行わない
if (isset($_POST['type'])) {
    $name     = '';
    $password = '';
    $eMail    = '';
    $title    = '';
    $comment  = '';
}

$key             = md5(time() . 'xaxaxaxaxaxaxa');  // タイムスタンプと推測できない文字列にてキーを発行
$_SESSION['key'] = $key;
$now             = 1;                               // 現在ページのデフォルトは１とする
$dataCount       = bbsDataCount($pdo);              // データ件数を取得
$unitLength      = $dataCount[0];                   // 書き込みデータの量を変数に格納
$pageMath        = ceil($unitLength / 10);          // ページ数を求める

// getmethodのnow変数があれば値を設定
if (isset($_GET['now'])) {

    // getmethodで渡された値が実際のページ数より多いときエラーを出し、１ページを表示する。
    if ($_GET['now'] > $pageMath || $_GET['now'] <= 0) {
        $error = '「不正な値が入力されました。1ページ目を表示します。」';
        $now   = 1;
    } else {
        $now = $_GET['now'];
    }
}

// 現在のページから表示するデータの始まりと終わりのidを求める
$dataStart = ($now - 1) * 10;
$no        = $unitLength - (($now - 1) * 10);

// DBから表示するデータを取得する
$stmt       = bbsSelect($pdo, $dataStart);
$printCount = $stmt->rowCount();           // 取得した行数を格納
$printStart = $unitLength - $dataStart;      // 全データ量と表示するデータの始まりから何番目のデータを表示しているか求める
$printEnd   = $printStart - $printCount + 1; // printstart変数と実際に取得した行数の差から、何番目のデータまで表示しているか求める
?>

<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>ゲストブック</title>
        <link rel="stylesheet" href="./css/bbs.css" type="text/css">
        <link rel="stylesheet" href="./css/original.css" type="text/css">
    </head>
    <body>
        <div class="body">
            <h1>ゲストブック</h1>
            <hr>
            <div class="error" id="errorMsg">
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
                            <th>パスワード/</th>
                            <td><input type="password" name="password" class="small" value="<?php $passwordExist ? print htmlspecialchars($password,  ENT_QUOTES, 'UTF-8') : ''; ?>"></td>
                        </tr>
                        <tr>
                            <th>e-mail/</th>
                            <td><input type="text" name="e-mail" class="medium" value="<?php isset($_POST['e-mail']) ? print htmlspecialchars($_POST['e-mail'],  ENT_QUOTES, 'UTF-8') : ''; ?>"></td>
                        </tr>
                        <tr>
                            <th>タイトル/</th>
                            <td>
                                <input type="text" name="title" class="medium" value="<?php isset($_POST['title']) ? print htmlspecialchars($_POST['title'],  ENT_QUOTES, 'UTF-8') : ''; ?>">
                                <input type="hidden" name="flag" value="<?php echo $key?>">
                                <input type="submit" value="投稿する">
                                <input type="button" value="リセット" onclick="clearFormAll()">
                            </td>
                        </tr>
                        <tr>
                            <th class="top">コメント/</th>
                            <td><textarea name="comment" cols="60" rows="10"><?php isset($_POST['comment']) ? print htmlspecialchars($_POST['comment'],  ENT_QUOTES, 'UTF-8') : ''; ?></textarea></td>
                        </tr>
                    </tbody>
                </table>
            </form>
            <div class="serchInput">
                <form>
                    <p>検索フォーム</p>
                    <input type="text"  placeholder="検索条件を入力してください"/>
                    <input type="submit" value="検索"/>
                </form>
            </div>
            <hr>
            <p><?php echo $printStart . '件目から' . $printEnd . '件目まで表示しています。'; ?></p>
            <?php

            // 書き込みを表示するメインループ
            while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            ?>
            <table class="article">
                <tbody>
                    <tr>
                        <td>
                            <p class="title">no.<?php echo $no ?>&nbsp;<span class="getTitle"><?php echo htmlspecialchars($row['title'], ENT_QUOTES, 'UTF-8') ?></span></p>
                            投稿者/
                            <?php
                            if ($row['email'] == '') {
                                echo '<span class="getName">' . htmlspecialchars($row['name'], ENT_QUOTES, 'UTF-8') . '</span>';
                            }
                            else {
                            ?>
                                <a class="getEMail" href="mailto:<?php echo htmlspecialchars($row['email'], ENT_QUOTES, 'UTF-8'); ?>"><?php echo '<span class="getName">' . htmlspecialchars($row['name'], ENT_QUOTES, 'UTF-8') . '</span>'; ?></a>
                            <?php
                            }
                            ?>
                            <?php
                            if ($row['password'] != null) {
                            ?>
                            <div class="inputComnd">
                                <form method="post" action="bbs.php" name="deleteForm">
                                    <input type="hidden" class="getId" name="id" value="<?php echo $row['id']?>">
                                    <input type="hidden" name="type" value="delete"/>
                                    <input type="password" name="userInputPassword" placeholder="password"/>
                                    <input type="submit" value="削除"/>
                                    <input type="button" class="modal-open" value="更新" onclick="getValue(this)"/>
                                </form>
                            </div>
                            <?php
                            }
                            ?>
                            <br>
                            投稿日/ <?php echo toDay($row['created_at']); ?><br>
                            <blockquote class="getComment"><?php echo nl2br(htmlspecialchars($row['comment'], ENT_QUOTES, 'UTF-8')); ?></blockquote>
                        </td>
                    </tr>
                </tbody>
            </table>
            <br/>
            <?php
                $no--;
            }
            ?>
            <div>
            <?php
            for ($i = 1; $i <= $pageMath; $i++) {
                if ($i == $now) {
                    $paging = '<span style="font-weight:bold;margin-right:5px;font-size:16px;"">' . $i . '</span>';
                } else {
                    $paging = '<a href="bbs.php?now=' . $i . '"><span style="margin-right:5px;font-size:16px;">' . $i  . '</span></a>';
                }
                echo $paging;
            }
            ?>
            </div>
        </div>
        <div class="modal-content">
            <form method="post" action="bbs.php" name="updateForm">
                <table class="form">
                    <colgroup>
                        <col span="1">
                        <col span="1">
                    </colgroup>
                    <h4>書き込み情報変更画面</h4>
                    <p class="error" id="modalErrorMsg">
                    </p>
                    <tbody>
                        <tr>
                            <th>お名前/</th>
                            <td><input type="text" id="uName" name="name" class="small" value="<?php isset($updateErrorFlag) ? print $_POST['name'] : ''; ?>"></td>
                        </tr>
                        <tr>
                            <th>変更前パスワード/</th>
                            <td><input type="password"  name="userInputPassword" class="small"></td>
                        </tr>
                        <tr>
                            <th>変更後パスワード/</th>
                            <td><input type="password"  name="password" class="small" ></td>
                        </tr>
                        <tr>
                            <th>e-mail/</th>
                            <td><input type="text" id="uEmail" name="e-mail" class="medium" value="<?php isset($updateErrorFlag) ? print $_POST['e-mail'] : ''; ?>"></td>
                        </tr>
                        <tr>
                            <th>タイトル/</th>
                            <td>
                                <input type="text" id="uTitle" name="title" class="medium" value="<?php isset($updateErrorFlag) ? print $_POST['title'] : ''; ?>">
                                <input type="submit" value="変更する">
                                <input type="button" value="リセット" onclick="clearFormAll()">
                            </td>
                        </tr>
                        <tr>
                            <th class="top">コメント/</th>
                            <td><textarea name="comment" id="uComment" cols="60" rows="10"><?php isset($updateErrorFlag) ? print $_POST['comment'] : ''; ?></textarea></td>
                        </tr>
                    </tbody>
                </table>
                <input type="hidden" name="type" value="update">
                <input type="hidden" name="id" id="id" value="<?php isset($updateErrorFlag) ? print $_POST['id'] : ''; ?>">
            </form>
            <input type="button" class="modal-close" value="閉じる" />
        </div>
        <?php
        if (isset($updateErrorFlag)) {
        ?>
            <script>
                updateErrorFlag = true
            </script>
        <?php
        }
        ?>
        <script src="js/jquery.min.js"></script>
        <script src="js/bbs.js"></script>
    </body>
</html>
