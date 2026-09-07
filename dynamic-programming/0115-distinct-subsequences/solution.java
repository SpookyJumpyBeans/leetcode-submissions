// 115. Distinct Subsequences
// https://leetcode.com/problems/distinct-subsequences/
// Hard | Java | Accepted 2026-09-06
// Runtime 22 ms | Memory 54.3 MB

class Solution {
    int[][] dp;
    String ss;
    String tt;
    public int numDistinct(String s, String t) {
        //Use a dp solution (backtracking + cache)
        ss = s;
        tt = t;
        dp = new int[s.length()+1][t.length()+1]; //We need a 2D DP array since the indexes of both the s and t strings are changing
        for(int[] d : dp)
        {
            Arrays.fill(d, -1);
        }
        return recurse(0, 0); //Start at index 0 for both strings
    }

    public int recurse(int indS, int indT)   
    {   
        if(tt.length()-indT>ss.length()-indS) //This is pruning early, by returning immediately the second it's not possible to find all the characters in t because the amount of characters needed left in t is more than the characters we have to choose from in s
        {
            return 0;
        }
        if(indT==tt.length()) //The second indT reaches the length of t, we've found a match in s, return 1 (1 successful match)
        {
            return 1;
        }
        if(dp[indS][indT]!=-1) //Check the cache
        {
            return dp[indS][indT];
        }
        int ans = 0;
        for(int i = indS; i<ss.length(); i++)
        {
            if(ss.charAt(i)==tt.charAt(indT)) //Every time we find a valid character match for t in s, we increment both indices
            {
                ans += recurse(i+1, indT+1); //Pass i+1 to the next recursive step since we found the valid char at i and since there is a strict order, we don't want to look at any past characters
            }
        }
        return dp[indS][indT] = ans; //Return the answer in the cache and return
    }
}
