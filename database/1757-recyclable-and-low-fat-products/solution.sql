-- 1757. Recyclable and Low Fat Products
-- https://leetcode.com/problems/recyclable-and-low-fat-products/
-- Easy | MySQL | Accepted 2026-11-04
-- Runtime 605 ms | Memory 0B

# Write your MySQL query statement below
SELECT product_id  
FROM products 
WHERE low_fats = 'Y' AND recyclable = 'Y'
