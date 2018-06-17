<?php

/*
* aタグ内のurlを整形し取得する関数
* @param  {string} {$userInputText} {ユーザが入力したテキスト}
* @return {string} {aタグ内のhref内urfを返す}
*/
function getUrlFormatFunc($userInputText)
{
    $targetTextStart = strpos($userInputText, '"');
    $targetTextEnd   = strpos($userInputText, '"', -2);
    $returnText      = substr($userInputText, $targetTextStart + 1, $targetTextEnd - 6);

    return $returnText;
}

/*
* インナーテキストを整形して返す
* @param  {string} {$userInpuText} {タグ間の文字}
* @return {string} {インナーテキストを返す}
*/
function getInnerHtmlFormatFunc($userInputText)
{
    $farstlessThanSign = strpos($userInputText, '>') + 1;
    $lastBiggerThanSighn = strrpos($userInputText, '<') - 1;
    $returnText = substr($userInputText, $farstlessThanSign, $lastBiggerThanSighn);

    return $returnText;
}
