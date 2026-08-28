// 1563. Stone Game V
// https://leetcode.com/problems/stone-game-v/
// Hard | Java | Accepted 2026-08-17
// Runtime 227 ms | Memory 47.9 MB

class Solution {
    int[][] dp;
    int[] stones;
    int[] prefix;
    public int stoneGameV(int[] stoneValue) {
        dp = new int[stoneValue.length][stoneValue.length]; //Use a 2D DP array to keep track of the start and ending subarrays. i and j represent the maximum score Alice can achieve on a subarray startin at stoneValue[i] and ending with stoneValue[j]
        stones = new int[stoneValue.length]; //Global stones array
        prefix = new int[stoneValue.length+1]; //Prefix sums to quickly calculate the total sum of a subarray
        for(int i = 0; i<stoneValue.length; i++)
        {
            stones[i] = stoneValue[i];
            prefix[i+1] = stones[i] + prefix[i]; //Make the prefix sum by adding a 0 buffer
        }
        for(int i = 0; i<stoneValue.length; i++)
        {
            for(int j = 0; j<stoneValue.length; j++)
            {
                dp[i][j] = -1; //Default all indices to -1 
            }
        }
        return recurse(0, stoneValue.length-1); //Recurse with the full array indices
    }

    public int recurse(int start, int end)
    {
        if(start==end)
        {
            return 0; //If out of 
        }
        if(dp[start][end]!=-1)
        {
            return dp[start][end];
        }
        int max = 0;
        for(int i = start; i<end; i++)
        {
            int leftSum = prefix[i+1]-prefix[start];
            int rightSum = prefix[end+1]-prefix[i+1];
            int currScore = 0;
            if(leftSum<rightSum)
            {
                currScore = leftSum + recurse(start, i);
            }
            else if(rightSum<leftSum)
            {
                currScore = rightSum + recurse(i+1, end);
            }
            else
            {
                currScore = leftSum + Math.max(recurse(start, i), recurse(i+1, end));
            }
            max = Math.max(max, currScore);
        }
        return dp[start][end] = max;
    }
}
