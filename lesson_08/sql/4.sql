SELECT `member_id`, `area`.`country_id`, `area`.`area_id`, `area`.`area_name`, `member`.`prefecture_id`, `prefecture_name`, `member_name`, `gender`, `created_at`, `updated_at`
FROM (`member` INNER JOIN `prefecture` ON `member`.`prefecture_id` = `prefecture`.`prefecture_id`)
INNER JOIN `area` ON `prefecture`.`area_id` = `area`.`area_id`
WHERE `area`.`area_id` = 6;