-- 1350. Students With Invalid Departments
-- https://leetcode.com/problems/students-with-invalid-departments/
-- Easy | MySQL | Accepted 2026-11-19
-- Runtime 991 ms | Memory 0B

# Write your MySQL query statement below
SELECT s.id, s.name
FROM Students as s
LEFT JOIN Departments as d
ON s.department_id = d.id
WHERE d.id IS NULL
