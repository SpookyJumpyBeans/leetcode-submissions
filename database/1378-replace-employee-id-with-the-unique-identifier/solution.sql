-- 1378. Replace Employee ID With The Unique Identifier
-- https://leetcode.com/problems/replace-employee-id-with-the-unique-identifier/
-- Easy | MySQL | Accepted 2026-11-15
-- Runtime 941 ms | Memory 0B

# Write your MySQL query statement below
SELECT EmployeeUNI.unique_id, Employees.name
FROM Employees
LEFT JOIN EmployeeUNI 
ON Employees.id = EmployeeUNI.id;
