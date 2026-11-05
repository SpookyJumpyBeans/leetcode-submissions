-- 1683. Invalid Tweets
-- https://leetcode.com/problems/invalid-tweets/
-- Easy | MySQL | Accepted 2026-11-04
-- Runtime 679 ms | Memory 0B

# Write your MySQL query statement below
SELECT tweet_id 
FROM Tweets
WHERE CHAR_LENGTH(content) > 15
