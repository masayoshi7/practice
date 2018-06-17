-- idが22の行があれば更新、なければ登録する
INSERT INTO `member`(`member_id`, `prefecture_id`, `member_name`, `gender`, `created_at`, `updated_at`) 
VALUES(22, 26, 'テスト四郎', '1', NOW(),null)
ON DUPLICATE KEY UPDATE
`prefecture_id` = VALUES(`prefecture_id`),
`member_name`= VALUES(`member_name`),
`gender`= VALUES(`gender`),
`created_at`= VALUES(`created_at`),
`updated_at`= VALUES(`updated_at`);