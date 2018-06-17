<?php

// タイムゾーンを日本の東京に設定
date_default_timezone_set('Asia/Tokyo');

/*
* 画像表示メソッド
* @param {array} {$array} {来訪者のカウント情報と、最後に来訪者が来た日付データをもつ配列}
* @return {string} {引数で与えられたカウント情報を、画像に変換したパスを持つ変数}
*/
function imagePrint($array)
{

    // 例外が投げられた際はarray配列は0で渡されるのでデータの整形を行う
    if ($array == 0) {
        $array = [
            0,
            0,
            0,
            date('Ymd')
        ];
    }

    // 文字形式整形
    $allData        = str_pad($array[0], 7, 0, STR_PAD_LEFT);
    $todayData      = format($array[1]);
    $yesterdayData  = format($array[2]);
    $length         = strlen($allData);                                   // 文字数の長さを格納
    $i              = 0;                                                  // whileループ制御変数
    $image          = '<img src=img/l.gif id=\'leftBea\' class=\'bea\'>'; // 左の熊の画像

    // 対応する数字の画像を出力
    while ($i < $length) {

        // 画像パスを動的に分ける
        $image .= '<img src=img/' . mb_substr($allData, $i, 1) . '.gif class=\'number\'>';

        // $image .= '<img src=img/' . $result[$i] . '.gif>';
        $i++;
    }

    // 右の熊の画像と今日の来訪者と機能の来訪者を末尾に追加
    $image .= '<img src=img/r.gif id=\'rightBea\' class=\'bea\'><br />'
            . 'Today: ' . $todayData . '<br />yesterday: ' . $yesterdayData;
    return $image;
}

/*
* 本日分の来訪者と昨日の分の来訪者を判定する関数
* @param {array} {$array} {来訪者のカウント情報と、最後に来訪者が来た日付データをもつ配列}
* @return {array} {引数で与えられたカウント情報を、本日の来訪者と昨日の来訪者を振り分けて返す}
*/
function dateCheck($array)
{
    $today      = date('Ymd');                               // 本日の日付
    $yesterday  = date('Ymd', strtotime($today . '-1 day')); // 昨日の日付

    /*
    * ファイルの日付が昨日の日付だった場合、本日分のアクセスカウンタを昨日のアクセスカウンタにうつす
    * そして本日分のカウンタを0に戻し、本日の日付を日付データに格納する
    */
    if (trim($yesterday) == trim($array[3])) {
        $array[2] = $array[1];
        $array[1] = 0;
        $array[3] = $today;
    } elseif (!(trim($yesterday) == trim($array[3])) && !(trim($today) == trim($array[3]))) {
        $array[2] = 0;
        $array[1] = 0;
        $array[3] = $today;
    }
    return $array;
}

/*
* 4桁以上の場合、カンマ区切りにする関数
* @param {int} {$num} {来訪者数を格納した変数}
* @return {int} {引数の数字を４桁以上の場合、カンマ区切りにしたものを返す}
*/
function format($num)
{
    if (mb_strlen($num) <= 4) {
        $num = number_format($num);
    } else {
        $num = str_pad($num, 3, 0, STR_PAD_LEFT);
    }
    return $num;
}

/*
* クッキー判定メソッド
* @param {string} {$fileTime} {最後に来訪者が来た日付の情報変数}
* @return {void}
*/
function timeCheckCookie($fileTime)
{

    /*
    * visit変数が存在する場合に、クッキーの寿命が超えてないかを判定する
    * visit変数に書き込まれている日付と本日の日付が同一な場合、同日内にアクセスしているとみなす
    */
    if (isset($_COOKIE['visit'])) {
        if (trim($fileTime) != date('Ymd')) {
            unset($_COOKIE['visit']);
        }
    }
}
