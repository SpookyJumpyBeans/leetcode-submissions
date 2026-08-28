// 70. Climbing Stairs
// https://leetcode.com/problems/climbing-stairs/
// Easy | Java | Accepted 2026-01-09
// Runtime 0 ms | Memory 42.2 MB

class Solution {
    public int climbStairs(int n) {
        int[] ans = new int[n];
        int count = 0;
        ans[0] = 1;
        for(int i = 1; i<n; i++)
        {
            if(i==1)
            {
                ans[i] = 2;
            }
            else
            {
            ans[i] = ans[i-1]+ans[i-2];
            }
        }
        return ans[n-1];
    }
    

}
