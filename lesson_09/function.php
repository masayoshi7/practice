<?php

/*
* 曜日変換メソッド
* @param {string} {$day} {日付の文字列型}
* @return {string} {引数の曜日の数字を感じに置き換えて返す}
*/
function toDay($date)
{
    $timeStamp = strtotime($date);
    $dayNumber = date('w', $timeStamp);

    // 数値に対応した曜日の連想配列を作成
    $days = array(
        '0' => '日',
        '1' => '月',
        '2' => '火',
        '3' => '水',
        '4' => '木',
        '5' => '金',
        '6' => '土',
    );
    $day          = $days[$dayNumber];
    $formatDate   = date('Y年n月j日?H:i', $timeStamp);
    $explodeDates = explode('?', $formatDate);

    return $explodeDates[0] . '（' . $day . '）' . $explodeDates[1];
}

/*
* 渡された引数が空白だけ含んでいないかチェックする関数
* @param {string} {$str} {確認する文字列}
* @return {boolean} {空白だけの場合trueを返す}
*/
function blancCheck($str)
{
    $result;
    if (preg_match('/[\s　]+/u', $str)) {
        if (preg_match('/[\S]+/u', $str) && preg_match('/[^　]+/u', $str)) {
            $result = false;
        } else {
            $result = true;
        }
    } else {
        $result = false;
    }
    return $result;
}

/*
* 渡された引数の改行文字をエスケープする関数
* @param {string} {$str} {確認する文字列}
* @return {string} {改行をエスケープして返す}
*/
function devertedCheck($str)
{
    return str_replace("\r\n", '####', $str);
}
