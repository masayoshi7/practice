SELECT 
CASE
 WHEN `gender` = 0 THEN 'women'
 ELSE 'men'
END AS 'gender'
, COUNT(`gender`) AS 'count' 
FROM `member`
GROUP BY `gender`;