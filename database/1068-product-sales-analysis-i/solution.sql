-- 1068. Product Sales Analysis I
-- https://leetcode.com/problems/product-sales-analysis-i/
-- Easy | MySQL | Accepted 2026-11-15
-- Runtime 1092 ms | Memory 0B

# Write your MySQL query statement below
SELECT p.product_name, s.year, s.price
FROM Sales s
LEFT JOIN Product p 
ON s.product_id = p.product_id;
