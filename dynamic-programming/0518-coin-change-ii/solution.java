// 518. Coin Change II
// https://leetcode.com/problems/coin-change-ii/
// Medium | Java | Accepted 2026-11-17
// Runtime 32 ms | Memory 58.4 MB

class Solution {
    int[][] dp;
    public int change(int amount, int[] coins) {
        dp = new int[amount+1][coins.length]; //State is the amount of change we have left and the length of the coins array
        for(int[] i : dp)
        {
            Arrays.fill(i, -1);
        }
        return recurse(amount, coins, 0);
    }

    public int recurse(int left, int[] coins, int ind)
    {
        if(ind>=coins.length || left<0) //If we overshoot the change or we reach the end of the coins array, return 0
        {
            return 0;
        }
        if(left==0) //If we succesfully find a combination that covers all the change exactly, return 1
        {
            return 1;
        }
        if(dp[left][ind]!=-1) //Check cache
        {
            return dp[left][ind];
        }
        int takeStay = recurse(left-coins[ind], coins, ind); //Either we deduct the value of coins at ind and stay at the same ind
        int skip = recurse(left, coins, ind+1); //Or we skip to the next index and don't take anything at the current ind
        return dp[left][ind] = takeStay + skip; //Store the summation of the two options
    }
}
