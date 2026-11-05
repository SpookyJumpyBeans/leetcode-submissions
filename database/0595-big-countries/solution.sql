-- 595. Big Countries
-- https://leetcode.com/problems/big-countries/
-- Easy | MySQL | Accepted 2026-11-04
-- Runtime 307 ms | Memory 0B

# Write your MySQL query statement below
SELECT name, population, area 
FROM World 
WHERE area >= 3000000
OR    population >= 25000000
