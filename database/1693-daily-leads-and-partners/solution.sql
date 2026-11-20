-- 1693. Daily Leads and Partners
-- https://leetcode.com/problems/daily-leads-and-partners/
-- Easy | MySQL | Accepted 2026-11-19
-- Runtime 451 ms | Memory 0B

# Write your MySQL query statement below
SELECT ds.date_id, ds.make_name, COUNT(DISTINCT lead_id) as unique_leads, COUNT(DISTINCT partner_id) as unique_partners #Counts the leads and partner ids that are unique to each date and make name pairing
FROM DailySales as ds
GROUP BY date_id, make_name #Group by 2 separate columns so each date_id and make_name pairing is unique
