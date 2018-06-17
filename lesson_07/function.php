<?php

/*
* 曜日変換メソッド
* @param {string} {$day} {日付の文字列型}
* @return {string} {引数の曜日の数字を感じに置き換えて返す}
*/
function toDay($day)
{
    $place   = mb_strpos($day, '(');
    $target  = mb_substr($day, $place + 1 ,1);
    $replace = '(' . day($target) . ')';

    return preg_replace('/\([0-6]\)/', $replace, $day);
}

/*
* 曜日数字に対応する曜日を返す関数
* @param {int} {$num} {日付の数字}
* @return {string} {引数の曜日の数字を感じに置き換えて返す}
*/
function day($num)
{
    switch ($num) {
        case 0:
            return '日';
            break;
        case 1:
            return '月';
            break;
        case 2:
            return '火';
            break;
        case 3:
            return '水';
            break;
        case 4:
            return '木';
            break;
        case 5:
            return '金';
            break;
        default:
            return '土';
            break;
    }
}

/*
* 引数で与えられた文字列に対し?と｜が含まれていないか確認し、あれば変更する
* @param {string} {$sentence} {投稿する情報}
* @return {string} {投稿する情報を変換して返す}
*/
function encryption($sentence)
{
    $target   = '/\?/';
    $replace  = '###';
    $sentence = preg_replace($target, $replace, $sentence);

    $target2  = '/\|/';
    $replace2 = '%%%';
    $sentence = preg_replace($target2, $replace2, $sentence);

    return $sentence;
}

/*
* Encryption関数で変換した文字列をもとに戻す
* @param {string} {$sentence} {変換された投稿内容}
* @return {string} {変換された投稿内容をもとに戻して返す}
*/
function decipher($sentence)
{
    $target   = '/###/';
    $replace  = '?';
    $sentence = preg_replace($target, $replace, $sentence);

    $target2  = '/%%%/';
    $replace2 = '|';
    $sentence = preg_replace($target2, $replace2, $sentence);

    return $sentence;
}
