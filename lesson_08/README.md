# 課題08

**lesson_08** SQL基礎

## 内容

SQLの基礎を学ぼう。

## 詳細

SQL (Structured Query Language) とは、リレーショナルデータベース (RDB) の管理や操作を行う為のドメイン固有言語です。  
今回は、RDB のデータを取得、挿入、更新、削除する方法を学習しましょう。

データの取得には `SELECT` 文を利用します。  
取得条件を指定するには `WHERE` 句を利用します。  
**参考**:  
https://www.w3schools.com/sql/sql_select.asp  
https://www.w3schools.com/sql/sql_where.asp  
https://www.w3schools.com/sql/sql_and_or.asp  
https://www.w3schools.com/sql/sql_orderby.asp

データの挿入には `INSERT` 文を利用します。  
**参考**:  
https://www.w3schools.com/sql/sql_insert.asp

データの更新には `UPDATE` 文を利用します。  
**参考**:  
https://www.w3schools.com/sql/sql_update.asp

データの削除には `DELETE` 文を利用します。  
**参考**:  
https://www.w3schools.com/sql/sql_delete.asp

以上を踏まえ、下記「[SQL作成](#SQL作成)」の  
条件に基づくデータの操作を SQL で行ってみましょう。

また、 [RDBMS](https://ja.wikipedia.org/wiki/関係データベース管理システム) は [MySQL](https://www.mysql.com/jp/) を利用します。  
データベース及び、テーブルは既に準備されたものを使用して下さい。  
(データベースの接続先や接続方法などは後述する「[接続先データベース情報](#接続先データベース情報)」「[MySQLクライアント](#MySQLクライアント)」を参照下さい)

## 接続先データベース情報

後述する [MySQLクライアント](#MySQLクライアント) で、以下のデータベースに接続します。

- SSH ホスト: `13.231.121.140`
- SSH 鍵: `<yourname>.pem`
- MySQL ホスト: `127.0.0.1`
- MySQL ポート: `3306`
- MySQL ユーザ: `<yourname>`
- MySQL パスワード: `<yourname>`
- MySQL DB 名: `<yourname>`

## テーブル構成

今回の課題で使用するテーブル構成は以下の通りです。

**地域情報**:  
```
SHOW FULL COLUMNS FROM area;
+------------+-------------+-----------------+------+-----+---------+-------+---------------------------------+-----------+
| Field      | Type        | Collation       | Null | Key | Default | Extra | Privileges                      | Comment   |
+------------+-------------+-----------------+------+-----+---------+-------+---------------------------------+-----------+
| country_id | tinyint(4)  | NULL            | NO   | PRI | NULL    |       | select,insert,update,references | 国 ID     |
| area_id    | tinyint(4)  | NULL            | NO   | PRI | NULL    |       | select,insert,update,references | 地域 ID   |
| area_name  | varchar(20) | utf8_general_ci | YES  |     | NULL    |       | select,insert,update,references | 地域名    |
+------------+-------------+-----------------+------+-----+---------+-------+---------------------------------+-----------+
```

**都道府県情報**:  
```
SHOW FULL COLUMNS FROM prefecture;
+-----------------+-------------+-----------------+------+-----+---------+-------+---------------------------------+-----------------+
| Field           | Type        | Collation       | Null | Key | Default | Extra | Privileges                      | Comment         |
+-----------------+-------------+-----------------+------+-----+---------+-------+---------------------------------+-----------------+
| country_id      | tinyint(4)  | NULL            | NO   | PRI | NULL    |       | select,insert,update,references | 国 ID           |
| area_id         | tinyint(4)  | NULL            | NO   | PRI | NULL    |       | select,insert,update,references | 地域 ID         |
| prefecture_id   | tinyint(4)  | NULL            | NO   | PRI | NULL    |       | select,insert,update,references | 都道府県 ID     |
| prefecture_name | varchar(20) | utf8_general_ci | YES  |     | NULL    |       | select,insert,update,references | 都道府県名      |
+-----------------+-------------+-----------------+------+-----+---------+-------+---------------------------------+-----------------+
```

**会員情報**:  
```
SHOW FULL COLUMNS FROM member;
+---------------+------------------+-----------------+------+-----+---------+----------------+---------------------------------+-------------------------------+
| Field         | Type             | Collation       | Null | Key | Default | Extra          | Privileges                      | Comment                       |
+---------------+------------------+-----------------+------+-----+---------+----------------+---------------------------------+-------------------------------+
| member_id     | int(10) unsigned | NULL            | NO   | PRI | NULL    | auto_increment | select,insert,update,references | 会員 ID                       |
| prefecture_id | tinyint(4)       | NULL            | NO   |     | NULL    |                | select,insert,update,references | 都道府県 ID                   |
| member_name   | varchar(30)      | utf8_general_ci | YES  |     | NULL    |                | select,insert,update,references | 会員名                        |
| gender        | char(1)          | utf8_general_ci | NO   |     | NULL    |                | select,insert,update,references | 性別 (0: 女性, 1: 男性)       |
| created_at    | datetime         | NULL            | NO   |     | NULL    |                | select,insert,update,references | 登録日時                      |
| updated_at    | datetime         | NULL            | YES  |     | NULL    |                | select,insert,update,references | 更新日時                      |
+---------------+------------------+-----------------+------+-----+---------+----------------+---------------------------------+-------------------------------+
```

## MySQLクライアント

データベース接続、 SQL 発行を行うために [MySQL Workbanch](https://dev.mysql.com/downloads/workbench/) を利用します。  
導入・設定手順は以下を参照下さい。  
[MySQL Workbench 導入・設定手順 - Wiki](http://52.197.1.134:8080/gitbucket/Intern/documents/wiki/MySQL%20Workbench%20%E5%B0%8E%E5%85%A5%E3%83%BB%E8%A8%AD%E5%AE%9A%E6%89%8B%E9%A0%86)

## SQL作成

以下、本題です。  
次の条件に基づくデータ操作を行う SQL を作成しましょう。

1. 全ての会員情報を取得して下さい。
2. 性別が男性の会員情報のみを取得して下さい。
3. 都道府県が「京都府」の会員情報のみを取得して下さい。  
その会員の都道府県情報も一緒に取得して表示しましょう。
4. 地域が「近畿地方」の会員情報のみを取得して下さい。  
その会員の地域情報と都道府県情報も一緒に取得して表示しましょう。
5. 2018年以降に登録された会員の会員情報を取得して下さい。
6. 2017年に登録された会員の会員情報を取得して下さい。
7. 会員名に「田」を含む会員の会員情報のみ取得して下さい。
8. 1番最初に登録された会員の会員情報を取得して下さい。
9. 1番最後に登録された会員の会員情報を取得して下さい。
10. 会員情報の男性、女性それぞれの件数を取得して下さい。
11. 会員情報に自分の情報を登録して下さい。
12. 会員名が「森田義明」の会員情報が間違っているので、会員名を「森田昭義」に変更して下さい。
13. 会員情報から自分の情報を削除して下さい。
14. **[発展課題]** 地域ごとに会員が何人いるかを取得して下さい。  
ただし、会員がいない地域は 0 と表示するようにして下さい。

## SQL提出

作成した SQL は、ファイル名を `<番号>.sql` として、  
リポジトリの `lesson_08/sql` 配下に追加して下さい。

```
lesson_08/
 |
 + README.md
 |
 + sql/
    |
    + 1.sql   ← 作成した SQL ファイル
    + 2.sql
    + ...
```
