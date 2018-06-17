<?php
require('function.php');
?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>アンケートフォーム</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>
    <body>
        <main>
            <section>
                <h1>アンケートにご協力ください</h1>
                <form method="post" action="confirm.php">
                    <table id="form-table">
                        <tbody>
                            <tr>
                                <td class="item">1．ニックネーム</td>
                                <td class="content">
                                    <input type="text" name="name" size="40" id="nickName" value="<?php isset($_POST['name']) ? print $_POST['name'] : ''; ?>">
                                </td>
                            </tr>
                            <tr>
                                <td>2．年齢</td>
                                <td>
                                    <!--確認画面で変更するを押下した際に値を保持するための処理-->
                                    <select name="age">
                                        <option value="no" <?php isset($_POST['age']) ? value($_POST['age'], 'no', 'selected') : ''; ?> >選択してください</option>
                                        <option value="20歳未満" <?php isset($_POST['age']) ? value($_POST['age'], '20歳未満', 'selected') : ''; ?> >20歳未満</option>
                                        <option value="20代" <?php isset($_POST['age']) ? value($_POST['age'], '20代', 'selected') : ''; ?> >20代</option>
                                        <option value="30代" <?php isset($_POST['age']) ? value($_POST['age'], '30代', 'selected') : ''; ?> >30代</option>
                                        <option value="40代" <?php isset($_POST['age']) ? value($_POST['age'], '40代', 'selected') : ''; ?> >40代</option>
                                        <option value="50代" <?php isset($_POST['age']) ? value($_POST['age'], '50代', 'selected') : ''; ?> >50代</option>
                                        <option value="60代" <?php isset($_POST['age']) ? value($_POST['age'], '60代', 'selected') : ''; ?> >60代</option>
                                        <option value="70代以上" <?php isset($_POST['age']) ? value($_POST['age'], '70代以上', 'selected') : ''; ?> >70代以上</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>3．利用頻度</td>
                                <td>
                                    八王子市のホームページを見る頻度を教えてください。<br>
                                    <input type="radio" name="frequency" value="ほぼ毎日" <?php isset($_POST['frequency']) ? value($_POST['frequency'], 'ほぼ毎日', 'checked') : ''; ?> >ほぼ毎日
                                    <input type="radio" name="frequency" value="週に1から3回" <?php isset($_POST['frequency']) ? value($_POST['frequency'], '週に1から3回', 'checked') : ''; ?> >週に1から3回
                                    <input type="radio" name="frequency" value="月に1回から3回" <?php isset($_POST['frequency']) ? value($_POST['frequency'], '月に1回から3回', 'checked') : ''; ?> >月に1回から3回
                                    <input type="radio" name="frequency" value="あまり使わない" <?php isset($_POST['frequency']) ? value($_POST['frequency'], 'あまり使わない', 'checked') : ''; ?> >あまり使わない
                                </td>
                            </tr>
                            <tr>
                                <td>4．印象</td>
                                <td>
                                    八王子市のホームページ全体の印象はどうですか？<br>
                                    <input type="radio" name="impression" value="分かりやすい" <?php isset($_POST['impression']) ? value($_POST['impression'], '分かりやすい', 'checked') : ''; ?>>分かりやすい
                                    <input type="radio" name="impression" value="やや分かりやすい"<?php isset($_POST['impression']) ? value($_POST['impression'], 'やや分かりやすい', 'checked') : ''; ?>>やや分かりやすい
                                    <input type="radio" name="impression" value="どちらでもない" <?php isset($_POST['impression']) ? value($_POST['impression'], 'どちらでもない', 'checked') : ''; ?>>どちらでもない<br>
                                    <input type="radio" name="impression" value="やや分かりづらい" <?php isset($_POST['impression']) ? value($_POST['impression'], 'やや分かりづらい', 'checked') : ''; ?>>やや分かりづらい
                                    <input type="radio" name="impression" value="分かりづらい" <?php isset($_POST['impression']) ? value($_POST['impression'], '分かりづらい', 'checked') : ''; ?>>分かりづらい
                                </td>
                            </tr>
                            <tr>
                                <td>5．さがしやすさ</td>
                                <td>
                                    必要な情報がすぐに見つかりましたか？<br>
                                    <input type="radio" name="facilitate" value="すぐに見つかった" <?php isset($_POST['facilitate']) ? value($_POST['facilitate'], 'すぐに見つかった', 'checked') : ''; ?>>すぐに見つかった
                                    <input type="radio" name="facilitate" value="見つけるまでに時間がかかった" <?php isset($_POST['facilitate']) ? value($_POST['facilitate'], '見つけるまでに時間がかかった', 'checked') : ''; ?>>見つけるまでに時間がかかった
                                    <input type="radio" name="facilitate" value="見つからなかった" <?php isset($_POST['facilitate']) ? value($_POST['facilitate'], '見つからなかった', 'checked') : ''; ?>>見つからなかった
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>
                                    上記5にて「見つけるまでに時間がかかった」、「見つからなかった」<br>
                                    と回答された方にお聞きします。<br>
                                    見つけにくかった理由、見つからなかった情報は何ですか？<br>
                                    <textarea name="reason" cols="40" rows="5"><?php isset($_POST['reason']) ? print $_POST['reason'] : ''; ?></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td>6．掲載情報</td>
                                <td>
                                    もっと充実させたほうがよいと思われる情報はなんですか？<br>
                                    <textarea name="advance" cols="40" rows="5" id="advance"><?php isset($_POST['advance']) ? print $_POST['advance'] : ''; ?></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td>7．関心のある分野</td>
                                <td>
                                    八王子の市政についてどのような分野に関心がありますか？（複数回答可）<br>
                                    <input type="checkbox" name="interest[]" value="市の政策・計画"  <?php isset($_POST['interest']) ? interest($_POST['interest'], '市の政策・計画', 'checked') : ''; ?>>市の政策・計画
                                    <input type="checkbox" name="interest[]" value="議会" <?php isset($_POST['interest']) ? interest($_POST['interest'], '議会', 'checked') : ''; ?>>議会
                                    <input type="checkbox" name="interest[]" value="都市基盤整備" <?php isset($_POST['interest']) ? interest($_POST['interest'], '都市基盤整備', 'checked') : ''; ?>>都市基盤整備
                                    <input type="checkbox" name="interest[]" value="観光" <?php isset($_POST['interest']) ? interest($_POST['interest'], '観光', 'checked') : ''; ?>>観光
                                    <input type="checkbox" name="interest[]" value="産業" <?php isset($_POST['interest']) ? interest($_POST['interest'], '産業', 'checked') : ''; ?>>産業
                                    <input type="checkbox" name="interest[]" value="環境" <?php isset($_POST['interest']) ? interest($_POST['interest'], '環境', 'checked') : ''; ?>>環境<br>
                                    <input type="checkbox" name="interest[]" value="防犯・防災" <?php isset($_POST['interest']) ? interest($_POST['interest'], '防犯・防災', 'checked') : ''; ?>>防犯・防災
                                    <input type="checkbox" name="interest[]" value="市民参加" <?php isset($_POST['interest']) ? interest($_POST['interest'], '市民参加', 'checked') : ''; ?>>市民参加
                                    <input type="checkbox" name="interest[]" value="税金・保健・年金" <?php isset($_POST['interest']) ? interest($_POST['interest'], '税金・保健・年金', 'checked') : ''; ?>>税金・保健・年金
                                    <input type="checkbox" name="interest[]" value="健康・福祉" <?php isset($_POST['interest']) ? interest($_POST['interest'], '健康・福祉', 'checked') : ''; ?>>健康・福祉
                                    <input type="checkbox" name="interest[]" value="子育て" <?php isset($_POST['interest']) ? interest($_POST['interest'], '子育て', 'checked') : ''; ?>>子育て<br>
                                    <input type="checkbox" name="interest[]" value="学校教育" <?php isset($_POST['interest']) ? interest($_POST['interest'], '学校教育', 'checked') : ''; ?>>学校教育
                                    <input type="checkbox" name="interest[]" value="生涯学習" <?php isset($_POST['interest']) ? interest($_POST['interest'], '生涯学習', 'checked') : ''; ?>>生涯学習
                                    <input type="checkbox" name="interest[]" value="国際交流" <?php isset($_POST['interest']) ? interest($_POST['interest'], '国際交流', 'checked') : ''; ?>>国際交流
                                    <input type="checkbox" name="interest[]" value="イベント・募集" <?php isset($_POST['interest']) ? interest($_POST['interest'], 'イベント・募集', 'checked') : ''; ?>>イベント・募集
                                    <input type="checkbox" name="interest[]" value="市内地図情報" <?php isset($_POST['interest']) ? interest($_POST['interest'], '市内地図情報', 'checked') : ''; ?>>市内地図情報
                                </td>
                            </tr>
                            <tr>
                                <td>8．その他</td>
                                <td>
                                    市ホームページに関してご意見・ご感想をお聞かせください。<br>
                                    <textarea name="other" cols="40" rows="5"><?php isset($_POST['other']) ? print $_POST['other'] : ''; ?></textarea>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div id="buttons">
                        <input type="submit" value="送信する" id="submit">
                        &nbsp;&nbsp;
                        <input type="button" value="リセット"  onclick="clearFormAll()">
                    </div>
                </form>
            </section>
        </main>
        <script src="js/jquery.min.js"></script>
        <script src="js/jquery.balloon.min.js"></script>
        <script src="js/form.js"></script>
    </body>
</html>
