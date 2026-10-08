// 2140. Solving Questions With Brainpower
// https://leetcode.com/problems/solving-questions-with-brainpower/
// Medium | Java | Accepted 2026-10-07
// Runtime 9 ms | Memory 155.3 MB

class Solution {
    long[] dp;
    public long mostPoints(int[][] questions) {
        dp = new long[questions.length];
        Arrays.fill(dp, -1);
        //Only need 1D since for the current index since the past choices don't influence future decisions (we either skipped to get to this index or we took and then got to this index after skipping the next brainpower questions)
        return recurse(0, questions); //Start at index 0
    } 

    public long recurse(int ind, int[][] arr)
    {
        if(ind>=arr.length)
        {
            return 0;
        }
        if(dp[ind]!=-1)
        {
            return dp[ind];
        }
        long take = arr[ind][0] + recurse(ind+arr[ind][1]+1, arr); //Take the current element and skip ahead brainpower elements
        long skip = recurse(ind+1, arr); //Skip this question and increment the index
        return dp[ind] = Math.max(take, skip); //Store the maximum of the two choices
    }
}
