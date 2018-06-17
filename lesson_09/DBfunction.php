<?php

/*
*   ログテーブル関連のDB処理
*/


/*
* logテーブルにinsertする関数
* @param {resource, stringArray} {$pdo, $stringArray} {pdoリソースと挿入したいデータ}
* @return {int} {sqlの実行結果を返す}
*/
function logInsert($pdo, $userId)
{
    $date      = date('Y-m-d H:i:s');
    $insertSql = 'INSERT INTO `log` (`id`, `bbs_id`, `created_at`)
                  VALUES (null, ?, ?)';
    $stmt      = $pdo->prepare($insertSql);

    $stmt->bindParam(1, $userId, PDO::PARAM_INT);
    $stmt->bindParam(2, $date, PDO::PARAM_STR);

    return $stmt->execute();
}


/*
*   bbsテーブル関連のDB処理
*/


/*
* bbsテーブルの対象idのpasswordを取得する関数
* @param {resource, string} {$pdo, $userId} {pdoリソースとユーザのid}
* @return {string} {パスワードを返す}
*/
function bbsPasswordCheck($pdo, $userId)
{
    $passwordSerchSql = 'SELECT `password` FROM `bbs`
                         WHERE `id` = ?';

    $stmt             = $pdo->prepare($passwordSerchSql);
    $stmt->bindParam(1, $userId, PDO::PARAM_INT);
    $stmt->execute();

    return $stmt->fetch(PDO::FETCH_NUM);
}

/*
* bbsテーブルのカラムを削除する関数
* @param {resource, string} {$pdo, $userId} {pdoリソースとユーザのid}
* @return {int} {結果を返す}
*/
function bbsDelete($pdo, $userId)
{
    $deleteSql = 'DELETE FROM `bbs`
                  WHERE `id` = ?';
    $stmt      = $pdo->prepare($deleteSql);

    $stmt->bindParam(1, $userId, PDO::PARAM_INT);

    return $stmt->execute();
}

/*
* bbsテーブルのカラムを更新する関数
* @param {resource, stringArray, int, boolean} {$pdo, $updateArray, $userId, $passwordExist}
*                                              {pdoリソースと更新情報配列とユーザーIDとpasswordの存在真偽}
* @return {int} {結果を返す}
*/
function bbsUpdate($pdo, $updateArray, $userId, $passwordExist)
{
    $updateSql = 'UPDATE `bbs`
                  SET
                    `name`    = ?,
                    `title`   = ?,
                    `email`   = ?,
                    `comment` = ?';
    if ($passwordExist) {
        $updateSql .= ', `password` = ? WHERE `id` = ?';
    } else {
        $updateSql .= 'WHERE `id` = ?';
    }

    $stmt = $pdo->prepare($updateSql);
    $stmt->bindParam(1, $updateArray[0], PDO::PARAM_STR);
    $stmt->bindParam(2, $updateArray[1], PDO::PARAM_STR);
    $stmt->bindParam(3, $updateArray[2], PDO::PARAM_STR);
    $stmt->bindParam(4, $updateArray[3], PDO::PARAM_STR);

    if ($passwordExist) {
        $stmt->bindParam(5, $updateArray[4], PDO::PARAM_STR);
        $stmt->bindParam(6, $userId, PDO::PARAM_INT);
    } else {
        $stmt->bindParam(5, $userId, PDO::PARAM_INT);
    }
    return $stmt->execute();
}

/*
* bbsテーブルにinsertする関数
* @param {resource, stringArray, boolean} {$pdo, $insertArray, $passwordExist} {pdoリソースと挿入したいデータとパスワードの存在真偽}
* @return {int} {sqlの実行結果を返す}
*/
function bbsInsert($pdo, $insertArray, $passwordExist)
{
    $sql = 'INSERT INTO `bbs` (`name`, `email`, `title`, `comment`, `created_at`';
    if ($passwordExist) {
        $sql .= ', `password`) VALUES (?, ?, ?, ?, ?, ?)';
    } else {
        $sql .= ') VALUES (?, ?, ?, ?, ?)';
    }
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(1, $insertArray[0], PDO::PARAM_STR);
    $stmt->bindParam(2, $insertArray[1], PDO::PARAM_STR);
    $stmt->bindParam(3, $insertArray[2], PDO::PARAM_STR);
    $stmt->bindParam(4, $insertArray[3], PDO::PARAM_STR);
    $stmt->bindParam(5, $insertArray[4], PDO::PARAM_STR);
    if ($passwordExist) {
        $stmt->bindParam(6, $insertArray[5], PDO::PARAM_STR);
    }
    $stmt->execute();
}

/*
* bbsテーブルから表示するデータを取得する関数
* @param {resource, int} {$pdo, $dataStart} {pdoリソースと取得するデータの始まり変数}
* @return {resource} {実行したpdostatementを返す}
*/
function bbsSelect($pdo, $dataStart)
{
    // 表示するデータを求めるsql
    $sql       = 'SELECT `id`, `name`, `password`, `title`, `email`, `comment`, `created_at` FROM `bbs`
                  ORDER BY `id` DESC
                  LIMIT ?, 10';
    $stmt      = $pdo->prepare($sql);
    $stmt->bindParam(1, $dataStart, PDO::PARAM_INT);
    $stmt->execute();

    return $stmt;
}


/*
* bbsテーブルからカラム件数を取得する関数
* @param {resource} {$pdo} {pdoリソース}
* @return {int} {データの件数を返す}
*/
function bbsDataCount($pdo)
{
    $countSql  = 'SELECT COUNT(`id`) FROM `bbs`'; // データの件数を求めるsql
    return $pdo->query($countSql)->fetch(PDO::FETCH_NUM);
}
