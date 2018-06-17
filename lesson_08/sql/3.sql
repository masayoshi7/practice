SELECT `member_id`, `country_id`, `area_id`, `member`.`prefecture_id`, `prefecture_name`, `member_name`, `gender`, `created_at`, `updated_at` 
FROM `member` INNER JOIN `prefecture` ON `member`.`prefecture_id` = `prefecture`.`prefecture_id`
WHERE `member`.`prefecture_id` = 26;
