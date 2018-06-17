<?php
require_once 'Zend/Validate.php';

/*
* バリデーションをする関数
* @param  {String, String, String} {$region, $number, $goods} {ユーザが入力した情報の入った変数達}
* @return {String} {バリデーションを行った結果を返す}
*/
function validate($region, $number, $goods)
{
    $errormsg = '';

    if (!Zend_validate::is($region, 'NotEmpty')) {
        $errormsg .= '・都道府県名を入力してください' . "\n";
    }
    if (!Zend_validate::is($number, 'NotEmpty')) {
        $errormsg .= '・人口を入力してください' . "\n";
    } elseif (!Zend_validate::is($number, 'Digits')) {
        $errormsg .= '・半角数字で入力してください' . "\n";
    }
    if (!Zend_validate::is($goods, 'NotEmpty')) {
        $errormsg .= '・特産品を入力してください' . "\n";
    }

    return $errormsg;
}

/*
* 検索の結果を正確にするために配列の1番目と最後を()でかこう
* もっとスマートな方法があるかもしれない
* @param  {StringArray} {$array} {検索対象の配列}
* @return {StringArray} {()で囲った検索対象の配列を返す}
*/
function arrayconversion($array)
{
    $arrayCount = count($array);
    $iterator   = 0;
    if ($arrayCount > 2) {
        foreach ($array as $value) {
            if ($iterator === 0) {
                $array[$iterator] = '(' . $array[$iterator];
            } elseif ($iterator === $arrayCount - 1) {
                $array[$iterator] = $array[$iterator] . ')';
            }
            $iterator++;
        }
    }
    return $array;
}
