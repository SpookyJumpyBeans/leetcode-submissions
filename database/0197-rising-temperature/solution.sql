-- 197. Rising Temperature
-- https://leetcode.com/problems/rising-temperature/
-- Easy | MySQL | Accepted 2026-11-15
-- Runtime 425 ms | Memory 0B

# Write your MySQL query statement below
# To compare 2 different rows in the same table, do a self join on the table
SELECT w1.id #Only keep the id column from w1
FROM Weather w1
INNER JOIN Weather w2 
ON DATEDIFF(w1.recordDate, w2.recordDate) = 1 
# Subtracts the date from w2 from w1
# Joins rows where w1 is a day after w2
WHERE w1.temperature > w2.temperature #Check if the temperature associated with w1 for each row is greater than the temperature associated with w2
