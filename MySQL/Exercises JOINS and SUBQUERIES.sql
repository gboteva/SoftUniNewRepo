# 1. Employee Address
SELECT e.`employee_id`,
e.`job_title`,
e.`address_id`,
a.`address_text`
FROM `employees` AS e
JOIN `addresses` AS a
ON e.`address_id` = a.`address_id`
ORDER BY e.`address_id`
LIMIT 5;

#2. Addresses with Towns
SELECT e.`first_name`, e.`last_name`,
t.`name` as 'town', a.`address_text`
FROM `employees` AS e
JOIN `addresses` AS a
ON e.`address_id` = a.`address_id`
JOIN `towns` AS t
ON a.`town_id` = t.`town_id`
ORDER BY e.`first_name`, e.`last_name`
LIMIT 5;

#3. Sales Employee
SELECT e.`employee_id`,
e.`first_name`, e.`last_name`,
d.`name` as 'department_name'
FROM `employees` AS e
JOIN `departments` AS d
ON e.`department_id` = d.`department_id`
WHERE d.`name` = 'Sales'
ORDER BY e.`employee_id` DESC;

# 4. Employee Departments
SELECT e.`employee_id`,
e.`first_name` , e.`salary`,
d.`name` as 'department_name' 
FROM `employees` AS e
JOIN `departments` AS d
ON e.`department_id` = d.`department_id`
WHERE e.`salary` > 15000
ORDER BY d.`department_id` DESC
LIMIT 5;

#5. Employees Without Project
SELECT e.`employee_id`, e.`first_name`
FROM `employees` AS e
LEFT JOIN `employees_projects` as ep
ON e.`employee_id` = ep.`employee_id`
WHERE ep.`project_id` IS NULL
ORDER BY e.`employee_id` DESC
LIMIT 3;

#6. Employees Hired After
SELECT e.`first_name`, e.`last_name`,
e.`hire_date`, d.`name` AS 'dept_name'
FROM `employees` AS e
JOIN `departments` AS d
ON e.`department_id` = d.`department_id`
WHERE e.`hire_date` > '1999-01-01 00:00:00'
AND d.`department_id` IN (3, 10)
ORDER BY `hire_date`;

#7. Employees with Project
SELECT e.`employee_id`, e.`first_name`, p.`name`
AS 'project_name'
FROM `employees` AS e
JOIN `employees_projects` AS ep
ON e.`employee_id` = ep.`employee_id`
JOIN `projects` AS p
ON ep.`project_id` = p.`project_id`
WHERE p.`start_date` > '2002-08-13 23:59:59'
AND p.`end_date` IS NULL
ORDER BY e.`first_name`, p.`name`
LIMIT 5;

# 8. Employee 24
SELECT e.`employee_id`, e.`first_name`,
IF(YEAR(p.`start_date`) >= 2005, NULL, p.`name`) AS 'project_name'
FROM `employees` AS e
JOIN `employees_projects` AS ep
ON e.`employee_id` = ep.`employee_id`
JOIN `projects` AS p
ON ep.`project_id` = p.`project_id`
WHERE e.`employee_id` = 24
ORDER BY p.`name`;

#09. Employee Manager
SELECT e.`employee_id`, e.`first_name`,
e.`manager_id`, e2.`first_name`
FROM `employees` AS e
JOIN `employees` AS e2
ON e.`manager_id` = e2.`employee_id`
WHERE e.`manager_id` IN (3, 7)
ORDER BY e.`first_name`;

# 10. Employee Summary
SELECT e.`employee_id`,
CONCAT_WS(' ', e.`first_name`, e.`last_name`) AS 'employee_name',
CONCAT_WS(' ', e2.`first_name`, e2.`last_name`) AS 'manager_name',
d.`name` AS 'department_name'
FROM `employees` AS e
JOIN `employees` AS e2
ON e.`manager_id` = e2.`employee_id`
JOIN `departments` AS d
ON e.`department_id` = d.`department_id`
WHERE e.`manager_id` IS NOT NULL
ORDER BY e.`employee_id`
LIMIT 5;

# 11. Min Average Salary
SELECT AVG(`salary`) AS 'min_average_salary'
FROM `employees`
GROUP BY `department_id`
ORDER BY `min_average_salary`
LIMIT 1;

SELECT mc.`country_code`, m.`mountain_range`,
p.`peak_name`, p.`elevation`
FROM `peaks` as p
JOIN `mountains_countries` as mc
ON p.`mountain_id` = mc.`mountain_id`
JOIN `mountains` as m
ON mc.`mountain_id` = m.`id` 
WHERE mc.`country_code` = 'BG'
AND p.`elevation` > 2835
ORDER BY p.`elevation` DESC;

# 13.	Count Mountain Ranges
SELECT mc.`country_code`, 
	   COUNT(m.`mountain_range`) as 'mountain_range'
FROM `mountains` AS m
JOIN  `mountains_countries` as mc
ON m.`id` = mc.`mountain_id`
WHERE mc.`country_code` IN ('BG', 'US', 'RU')
GROUP BY mc.`country_code`
ORDER BY `mountain_range` DESC;

#14. Countries with Rivers
SELECT c.`country_name`, r.`river_name`
FROM `countries` AS c
LEFT JOIN `countries_rivers` AS cr
ON c.`country_code` = cr.`country_code`
LEFT JOIN `rivers` AS r
ON cr.`river_id` = r.`id`
WHERE c.`continent_code` = 'AF'
ORDER BY c.`country_name`
LIMIT 5;

#15. *Continents and Currencies
SELECT d1.continent_code, d1.currency_code, d1.currency_usage 
FROM 
	(SELECT `c`.`continent_code`, `c`.`currency_code`,
		COUNT(`c`.`currency_code`) AS `currency_usage` 
		FROM countries as c
		GROUP BY c.currency_code, c.continent_code 
		HAVING currency_usage > 1) AS d1
LEFT JOIN 
	(SELECT `c`.`continent_code`,`c`.`currency_code`,
    COUNT(`c`.`currency_code`) AS `currency_usage` 
    FROM countries AS c
	GROUP BY c.currency_code, c.continent_code 
    HAVING currency_usage > 1) as d2
    
ON d1.continent_code = d2.continent_code 
AND d2.currency_usage > d1.currency_usage

WHERE d2.currency_usage IS NULL
ORDER BY d1.continent_code, d1.currency_code;

#16.  Countries Without Any Mountains
SELECT COUNT(`country_name`) AS 'country_count'
FROM `countries` AS c
LEFT JOIN `mountains_countries` AS mc
ON c.`country_code` = mc.`country_code`
LEFT JOIN `mountains` AS m
ON mc.`mountain_id` = m.`id`
WHERE m.`id` IS NULL;

#17.  Highest Peak and Longest River by Country
SELECT c.`country_name`, 
MAX(p.`elevation`) AS 'highest_peak_elevation', 
MAX(r.`length`) AS 'longest_river_length'
FROM `countries` AS c
LEFT JOIN `mountains_countries` AS mc
ON c.`country_code` = mc.`country_code`
LEFT JOIN `peaks` AS p
ON mc.`mountain_id` = p.`mountain_id`
LEFT JOIN `countries_rivers` AS cr
ON c.`country_code` = cr.`country_code`
LEFT JOIN `rivers` AS r
ON cr.`river_id` =  r.`id`
GROUP BY c.`country_name`
ORDER BY `highest_peak_elevation` DESC, `longest_river_length` DESC, c.`country_name`
LIMIT 5;
