-- 1148. Article Views I
-- https://leetcode.com/problems/article-views-i/
-- Easy | MySQL | Accepted 2026-11-04
-- Runtime 667 ms | Memory 0B

# Write your MySQL query statement below
SELECT DISTINCT author_id AS id  -- DISTINCT allows for unique ids (ids aren't repeated)
-- AS allows for renaming a column using an alias 
FROM Views
WHERE author_id = viewer_id
ORDER BY author_id ASC -- ORDER BY allows for sorting and ASC means sort by author_id ascending
