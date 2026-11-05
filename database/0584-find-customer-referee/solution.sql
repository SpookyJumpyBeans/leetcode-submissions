-- 584. Find Customer Referee
-- https://leetcode.com/problems/find-customer-referee/
-- Easy | MySQL | Accepted 2026-11-04
-- Runtime 611 ms | Memory 0B

# Write your MySQL query statement below
SELECT name
FROM Customer
WHERE referee_id != 2 OR referee_id IS NULL
