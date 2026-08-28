// 877. Stone Game
// https://leetcode.com/problems/stone-game/
// Medium | Java | Accepted 2026-08-23
// Runtime 13 ms | Memory 46.9 MB

class Solution {
    int[][] dp;
    int[] p;
    public boolean stoneGame(int[] piles) {
        dp = new int[piles.length][piles.length];
        p = piles;
        for(int i = 0; i<piles.length; i++)
        {
            for(int j = 0; j<piles.length; j++)
            {
                dp[i][j] = -1;
            }
        }
        return recurse(0, piles.length-1) > 0 ? true : false;
    }

    public int recurse(int start, int end)
    {
        if(start==end)
        {
            return p[start];
        }
        if(dp[start][end]!=-1)
        {
            return dp[start][end];
        }
        int p1 = p[start] - recurse(start+1, end);
        int p2 = p[end] - recurse(start, end-1);
        return dp[start][end] = Math.max(p1, p2);
    }
}
