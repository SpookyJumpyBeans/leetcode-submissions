// 518. Coin Change II
// https://leetcode.com/problems/coin-change-ii/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {
    int[] coin;
    Integer[][] memo;
    public int change(int amount, int[] coins) {
        coin = coins;
        memo = new Integer[amount+1][coins.length];
        return recurse(amount, 0);
    }

    public int recurse(int amts, int index)
    {
        if(amts == 0)
        {
            return 1;
        }
        if(amts<0)
        {
            return 0;
        }
        if(memo[amts][index]!=null)
        {
            return memo[amts][index];
        }
        int res = 0;
       for(int i = index; i<coin.length; i++)
       {
          res  += recurse(amts-coin[i], i);
       }
       memo[amts][index] = res;
        return memo[amts][index];
    }
}
