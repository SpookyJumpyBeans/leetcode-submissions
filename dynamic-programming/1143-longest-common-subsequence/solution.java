// 1143. Longest Common Subsequence
// https://leetcode.com/problems/longest-common-subsequence/
// Medium | Java | Accepted 2026-11-16
// Runtime 34 ms | Memory 53.9 MB

class Solution {
    int[][] dp;
    String text1;
    String text2;
    public int longestCommonSubsequence(String text1, String text2) {
        dp = new int[text1.length()][text2.length()];
        this.text1 = text1;
        this.text2 = text2;
        for(int[] d : dp)
        {
            Arrays.fill(d, -1);
        }
        return recurse(0, 0);
    }

    public int recurse(int t1, int t2)
    {
        if(t1>=text1.length() || t2>=text2.length())
        {
            return 0;
        }
        if(dp[t1][t2]!=-1)
        {
            return dp[t1][t2];
        }
        int max = 0;
        if(text1.charAt(t1)==text2.charAt(t2))
        {
            max = Math.max(max, 1+recurse(t1+1, t2+1));
        }
        else
        {
            max = Math.max(max, Math.max(recurse(t1, t2+1), recurse(t1+1, t2)));
        }
        return dp[t1][t2] = max;
    }
}
