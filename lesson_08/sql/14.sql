SELECT `area_name`, COUNT(`member_name`) AS `count`
FROM(`member` INNER JOIN `prefecture` ON `member`.`prefecture_id` = `prefecture`.`prefecture_id`)
RIGHT JOIN `area` ON `prefecture`.`area_id` = `area`.`area_id` 
GROUP BY `area_name`
ORDER BY `count` DESC;