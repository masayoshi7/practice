<?php

/*
* ラジオボタンとセレクトウィンドウの値保持を制御する関数
* @param {string|string|string} {$val|$content|$word} {$valは選択された値、$contentは元々の値、$wordは出力する値}
* @return {string} {$wordを出力する}
*/
function value($val, $content, $word)
{
    if ($val == $content) {
        echo $word;
    }
}

/*
* チェックボタンの値を保持する関数
* @param {string|string|string} {$val|$content|$word} {$valは選択された値、$contentは元々の値、$wordは出力する値}
* @return {string} {$wordを出力する}
*/
function interest($val, $target, $word)
{
    foreach ($val as $value) {
        if ($value == $target) {
            print $word;
        }
    }
}

/*
* 入力された値のエラーチェックを行う
* @param void
* @return {string} {エラーチェックを行いエラーがあれば出力する}
*/
function checkContent()
{
    $error     = '';    // エラーメッセージに使用する変数
    $content   = '';
    $errorFlag = false; // エラー文字があるかどうかの判定に使用する変数

    // 名前の情報格納
    if (isset($_POST['name'])) {
        if (mb_strlen($_POST['name']) == 0) {
            $error = '１．名前を入力してください' . "\n";
        } else {
            $name = $_POST['name'];
            if (mb_strlen($name) >= 20) {
                $error .= '１．名前の入力は20字以内でお願いします' . "\n";
            }
        }
    } else {
        $error = '１．名前を入力してください' . "\n";
    }

    // 年齢の選択の確認、及び値にあった年齢を変数に格納
    if (isset($_POST['age'])) {
        if ($_POST['age'] == 'no') {
            $error .= '２．年齢を選択してください' . "\n";
        } else {
            $age = $_POST['age'];
        }
    } else {
        $error .= '２．年齢を選択してください' . "\n";
    }

    // 利用頻度の情報格納
    if (isset($_POST['frequency'])) {
        $frequency = $_POST['frequency'];
    } else {
        $error .= '３．利用頻度を入力してください' . "\n";
    }

    // 印象の情報格納
    if (isset($_POST['impression'])) {
        $impression = $_POST['impression'];
    } else {
        $error .= '４．印象を入力してください' . "\n";
    }

    // 探しやすさの情報格納
    if (isset($_POST['facilitate'])) {
        $facilitate = $_POST['facilitate'];

        // 探しやすさで見つからなかった、または見つけるまで時間がかかったを選んだ場合
        if($facilitate == '見つからなかった' || $facilitate == '見つけるまでに時間がかかった') {
            if(mb_strlen($_POST['reason']) == 0) {
                $error .= '５－２．見つからなかった理由、又は見つけるまでに時間がかかった理由を入力してください' . "\n";
            } elseif (mb_strlen($_POST['reason']) >= 100) {
                $error .= '５－２．見つからなかった理由、又は見つけるまでに時間がかかった理由は100字以内にしてください' . "\n";
            } else {
                $reason = $_POST['reason'];
            }
        }
    } else {
        $error .= '５．探しやすさを選択してください' . "\n";
    }

    // 掲載情報の情報格納
    if (isset($_POST['advance'])) {
        if (mb_strlen($_POST['advance']) == 0) {
            $error .= '６．掲載情報について入力してください' . "\n";
        } elseif (mb_strlen($_POST['advance']) >= 100) {
            $error .= '６．掲載情報については100字以内にしてください' . "\n";
        } else {
            $advance = $_POST['advance'];
        }
    } else {
        $error .= '６．掲載情報について入力してください' . "\n";
    }

    /*
    * 興味のある分野の情報格納
    * 配列として受け取る
    */
    if (isset($_POST['interest'])) {
        $interest = $_POST['interest'];
    } else {
        $error .= '７．関心のある分野について入力してください' . "\n";
    }

    // その他の情報格納
    if (isset($_POST['other'])) {
        if (mb_strlen($_POST['other']) == 0) {
            $error .= '８．その他について入力してください' . "\n";
        } elseif (mb_strlen($_POST['other']) >= 100) {
            $error .= '８．その他は100文字以内で入力してください' . "\n";
        } else {
            $other = $_POST['other'];
        }
    } else {
        $error .= '８．その他について入力してください' . "\n";
    }

    return $error;
}
