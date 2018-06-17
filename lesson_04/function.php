<?php
header('charset=UTF-8');

// 画像表示メソッド、引数としてアクセスカウンターの数値を受け取る
function imagePrint($result)
{

    // 文字形式整形
    $result = str_pad($result, 7, 0, STR_PAD_LEFT);

    // 左の画像
    $image = '<img src=img/l.gif>';
    // 文字数の長さを格納
    $length = strlen($result);
    // whileループ制御変数
    $i = 0;
    // 対応する数字の画像を出力
    while ($i < $length) {
        // 画像パスを動的に分ける
        $image .= '<img src=img/' . mb_substr($result, $i, 1) . '.gif>';

        // $image .= '<img src=img/' . $result[$i] . '.gif>';
        $i++;
    }
    // 右の画像
    $image .= '<img src=img/r.gif>';
    return $image;
}
