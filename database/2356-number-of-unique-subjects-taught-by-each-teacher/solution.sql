-- 2356. Number of Unique Subjects Taught by Each Teacher
-- https://leetcode.com/problems/number-of-unique-subjects-taught-by-each-teacher/
-- Easy | MySQL | Accepted 2026-11-19
-- Runtime 465 ms | Memory 0B

# Write your MySQL query statement below
SELECT t.teacher_id, COUNT(DISTINCT t.subject_id) as cnt # COUNT(DISTINCT) only counts the subject_ids that aren't duplicated
# After Gouping by teacher_id, the ids are already unique and the subject_ids are grouped with each subject_id (even if the subjects are duplicated)
FROM Teacher as t 
GROUP BY teacher_id 
