<?php
header('Content-Type: text/html; charset=UTF-8');
mb_regex_encoding ('UTF-8');
require('function.php');

$outputText = '';

if ($_POST['check_type_number'] == '1') {
    if (preg_match('/[0-9]+/', $_POST['user_input_text'])) {
        $outputText = '「半角数字が含まれています。」';
    } else {
        $outputText = '「半角数字が含まれていません。」';
    }

} elseif ($_POST['check_type_number'] == '2') {
    if (preg_match_all('/[0-9]/', $_POST['user_input_text'], $matchArray, PREG_OFFSET_CAPTURE)) {
        foreach ($matchArray[0] as $matchNumber) {
            $outputText .= '`' . $matchNumber[0] . '` ';
        }
    } else {
        $outputText = '「半角数字が含まれていません。」';
    }

} elseif ($_POST['check_type_number'] == '3') {
    if (preg_match('/[ぁ-ん]+/u', $_POST['user_input_text'])) {
        $outputText = '「平仮名が含まれています。」';
    } else {
        $outputText = '「平仮名が含まれていません。」';
    }

} elseif ($_POST['check_type_number'] == '4') {
    if (preg_match_all('/[ぁ-ん]/u', $_POST['user_input_text'], $matchArray, PREG_OFFSET_CAPTURE)) {
        foreach ($matchArray[0] as $matchNumber) {
            $outputText .= '`' . $matchNumber[0] . '`  ';
        }
    } else {
        $outputText = '「平仮名が含まれていません。」';
    }

} elseif ($_POST['check_type_number'] == '5') {
    preg_match_all('/(<.+?>)(\n|\r\n)*.+?(\n|\r\n)*<\/.+?>/u', $_POST['user_input_text'], $matchArray, PREG_OFFSET_CAPTURE);
    foreach ($matchArray[0] as $matchNumber) {
        $outputText .= '`' . getInnerHtmlFormatFunc($matchNumber[0]) . '`  ';
    }
    $outputText = $outputText;

} elseif ($_POST['check_type_number'] == '6') {
    $outputText = preg_replace('/年|月/', '-', $_POST['user_input_text']);
    $outputText = preg_replace('/日/', '', $outputText);

} elseif ($_POST['check_type_number'] == '7') {
    if (preg_match('/\.png$|\.jpg$|\.jpeg$|\.gif$/', $_POST['user_input_text'])) {
        $outputText = preg_replace('/\.png$|\.jpg$|\.jpeg$|\.gif$/', '', $_POST['user_input_text']);
    } else {
        $outputText = '「画像ファイル名として不正です。」';
    }

}  elseif ($_POST['check_type_number'] == '8') {
    if (preg_match('/[^0-9a-zA-Z-_]/', $_POST['user_input_text'])) {
        $outputText = '「半角英数字と記号(`-_`)で入力してください。」';
    } else {
        $outputText = '「入力値は問題ありません。」';
    }

} elseif ($_POST['check_type_number'] == '9') {
    if (preg_match('/^([0-9]-?){10,11}$/', $_POST['user_input_text'])) {
        $outputText = '「入力値は問題ありません。」';
    } else {
        $outputText = '「電話番号の形式が不正です。」';
    }

} elseif ($_POST['check_type_number'] == '10') {

    if (preg_match('/^(?!.*(\.com\/?|\.jp\/?)).*$|\.{2,}|^https?:\/\/.*\/{2,}.*$|^(?!https?:)/', $_POST['user_input_text'])) {
        $outputText = '「URLの形式が不正です。」';
    } else {
        $outputText = '「入力値は問題ありません。」';
    }

} elseif ($_POST['check_type_number'] == '11') {
    $outputText = preg_replace('/\n/', "\n\n", $_POST['user_input_text']);

} elseif ($_POST['check_type_number'] == '12') {
    preg_match_all('/href=".*?" /', $_POST['user_input_text'], $htmlAtagArray, PREG_OFFSET_CAPTURE);
    foreach ($htmlAtagArray[0] as $htmlAtag) {
        $outputText .= getUrlFormatFunc($htmlAtag[0]) . "\n";
    }

} elseif ($_POST['check_type_number'] == '13') {
    if (preg_match('/^[0-9A-Fa-f]{1,}$/', $_POST['user_input_text'])) {
        $outputText = $_POST['user_input_text'];
    } else {
        $outputText = '「16進数表記の数値を入力してください。」';
    }
}
?>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
        <title>正規表現チェッカー</title>
    </head>
    <body>
        <p>
            <?php echo nl2br($outputText); ?>
        </p>
    </body>
</html>
