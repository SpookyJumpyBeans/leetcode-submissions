-- 1741. Find Total Time Spent by Each Employee
-- https://leetcode.com/problems/find-total-time-spent-by-each-employee/
-- Easy | MySQL | Accepted 2026-11-19
-- Runtime 435 ms | Memory 0B

# Write your MySQL query statement below
SELECT e.event_day as day, e.emp_id, SUM(e.out_time - e.in_time) as total_time #Sums the difference between every out time and in time for every id and day pairing
FROM Employees as e
GROUP BY event_day, emp_id #Groups by the unique column pairings of day and emp_id
