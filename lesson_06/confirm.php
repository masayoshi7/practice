<?php
header('Content-Type: text/html; charset=UTF-8');
require('function.php');

$errorFlag = false;
$error     = checkContent(); // 送信内容のnullチェック、および字数制限を行う

if (mb_strlen($error) != 0) {
    $errorFlag = true;
}
?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>アンケートフォーム</title>
    </head>
    <link rel="stylesheet" href="css/confirm.css" type="text/css">
    <body>
        <main>
            <section>
                <?php
                if ($errorFlag) {
                    echo $error;
                } else {
                ?>
                    <h3>入力内容確認画面</h3>
                    <table id="confirmTable">
                        <tr>
                            <td>名前:</td>
                            <td>
                                <?php echo $_POST['name']; ?>
                            </td>
                        </tr>
                        <tr>
                            <td>年齢:</td>
                            <td>
                                <?php echo $_POST['age']; ?>
                            </td>
                        </tr>
                        <tr>
                            <td>利用頻度:</td>
                            <td>
                                <?php echo $_POST['frequency']; ?>
                            </td>
                        </tr>
                        <tr>
                            <td>印象:</td>
                            <td>
                                <?php echo $_POST['impression']; ?>
                            </td>
                        </tr>
                        <tr>
                            <td>探しやすさ:</td>
                            <td>
                                <?php echo $_POST['facilitate']; ?>
                            </td>
                        </tr>
                        <?php
                        if (isset($reason)) {
                        ?>
                            <tr>
                                <td>探しづらい理由:</td>
                                <td>
                                    <?php echo nl2br($reason); ?>
                                </td>
                            </tr>
                        <?php
                        }
                        ?>
                        <tr>
                            <td>掲載情報:</td>
                            <td>
                                <?php echo nl2br($_POST['advance']); ?>
                            </td>
                        </tr>
                        <tr>
                            <td>関心のある分野:</td>
                            <td>
                                <?php
                                foreach ($_POST['interest'] as $value) {
                                    echo $value;
                                ?>
                                <br />
                                <?php
                                }
                                ?>
                            </td>
                        </tr>
                        <tr>
                            <td>その他:</td>
                            <td>
                                <?php echo nl2br($_POST['other']); ?>
                            </td>
                        </tr>
                    </table>
                    <div class="buttons">
                        <form method="post" action="send_mail.php">
                            <input type="hidden" name="name" value="<?php echo $_POST['name']; ?>">
                            <input type="hidden" name="age" value="<?php echo $_POST['age']; ?>">
                            <input type="hidden" name="frequency" value="<?php echo $_POST['frequency']; ?>">
                            <input type="hidden" name="impression" value="<?php echo $_POST['impression']; ?>">
                            <input type="hidden" name="facilitate" value="<?php echo $_POST['facilitate']; ?>">
                            <?php
                            if (isset($_POST['reason'])) {
                            ?>
                                <input type="hidden" name="reason" value="<?php echo $_POST['reason']; ?>">
                            <?php
                            }
                            ?>
                            <input type="hidden" name="facilitate" value="<?php echo $_POST['facilitate']; ?>">
                            <?php
                            foreach ($_POST['interest'] as $value) {
                            ?>
                                <input type="hidden" name="interest[]" value="<?php echo $value; ?>">
                            <?php
                            }
                            ?>
                            <input type="hidden" name="advance" value="<?php echo $_POST['advance']; ?>">
                            <input type="hidden" name="other" value="<?php echo $_POST['other']; ?>">
                            <input type="submit" value="確定" />
                        </form>
                <?php
                }
                ?>
                        <!--変更ボタン押下時にform.phpに遷移する-->
                        <form method="post" action="form.php">
                            <input type="hidden" name="name" value="<?php echo $_POST['name']; ?>">
                            <input type="hidden" name="age" value="<?php echo $_POST['age']; ?>">
                            <input type="hidden" name="frequency" value="<?php echo $_POST['frequency']; ?>">
                            <input type="hidden" name="impression" value="<?php echo $_POST['impression']; ?>">
                            <input type="hidden" name="facilitate" value="<?php echo $_POST['facilitate']; ?>">
                            <?php
                            if (isset($_POST['reason'])) {
                            ?>
                                <input type="hidden" name="reason" value="<?php echo $_POST['reason']; ?>">
                            <?php
                            }
                            ?>
                            <input type="hidden" name="facilitate" value="<?php echo $_POST['facilitate']; ?>">
                            <?php
                            if (isset($_POST['interest'])) {
                                foreach ($_POST['interest'] as $value) {
                            ?>
                                    <input type="hidden" name="interest[]" value="<?php echo $value; ?>">
                            <?php
                                }
                            }
                            ?>
                            <input type="hidden" name="advance" value="<?php echo $_POST['advance']; ?>">
                            <input type="hidden" name="other" value="<?php echo $_POST['other']; ?>">
                            <input type="submit" value="変更" />
                        </form>
                    </div>
            </section>
        </main>
    </body>
</html>
