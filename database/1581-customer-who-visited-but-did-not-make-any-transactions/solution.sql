-- 1581. Customer Who Visited but Did Not Make Any Transactions
-- https://leetcode.com/problems/customer-who-visited-but-did-not-make-any-transactions/
-- Easy | MySQL | Accepted 2026-11-15
-- Runtime 1132 ms | Memory 0B

# Write your MySQL query statement below
SELECT v.customer_id, COUNT(*) AS count_no_trans #Creates the final 2 columns: customer_id and count_no_trans
#Use COUNT(*) because we count the number of rows in each bucket that gets created when we GROUP BY customer_id
FROM Visits v 
LEFT JOIN Transactions t  #Left join means that we keep every visit_id even if the visit_id doesn't appear in transactions
ON v.visit_id = t.visit_id
WHERE t.transaction_id IS NULL #We only keep the rows where visit_id in transactions is NULL, which means that the visit ended in no transactions
GROUP BY v.customer_id #Group all the null transaction id's by the customer_id so that we can see how many null transactions each customer made
