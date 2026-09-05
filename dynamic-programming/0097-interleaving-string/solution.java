// 97. Interleaving String
// https://leetcode.com/problems/interleaving-string/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {
    String one;
    String two;
    String thr;
    int[][] dp;
    public boolean isInterleave(String s1, String s2, String s3) {
        one = s1;
        two = s2;
        thr = s3;
        dp = new int[s1.length()+1][s2.length()+1];
        if(s1.length() + s2.length() != s3.length())
        {
            return false;
        }
        for(int i = 0; i<dp.length; i++)
        {
            for(int j = 0; j<dp[0].length; j++)
            {
                dp[i][j] = -1;
            }
        }
        return recurse(0, 0, 0);
    }

    public boolean recurse(int ind1, int ind2, int ind3)
    {
        if(ind3==thr.length())
        {
            return true;
        }
        if(dp[ind1][ind2]!=-1)
        {
            return dp[ind1][ind2] == 1;
        }
        boolean res = false;
        if(ind1<one.length() && one.charAt(ind1) == thr.charAt(ind3))
        {
            res = recurse(ind1+1, ind2, ind3+1);
        }
        if(ind2<two.length() && two.charAt(ind2) == thr.charAt(ind3) && !res)
        {
            res =  recurse(ind1, ind2+1, ind3+1);
        }
        dp[ind1][ind2] = res ? 1 : 0;
        return res;
    }
}
