// 1672. Richest Customer Wealth
// https://leetcode.com/problems/richest-customer-wealth/
// Easy | Java | Accepted 2026-10-27
// Runtime 0 ms | Memory 44.2 MB

class Solution {
    public int maximumWealth(int[][] accounts) {
        int max = 0;
        for(int[] i : accounts)
        {
            int curr = 0;
            for(int j = 0; j<i.length; j++)
            {
                curr+=i[j];
            }
            max = Math.max(curr, max);
        }
        return max;
    }
}
