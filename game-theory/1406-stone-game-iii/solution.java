// 1406. Stone Game III
// https://leetcode.com/problems/stone-game-iii/
// Hard | Java | Accepted 2026-09-04
// Runtime 31 ms | Memory 92.1 MB

class Solution {
    int[] dp;
    int[] stones;
    public String stoneGameIII(int[] stoneValue) {
        //I used a memoization technique
        //Make a dp array to store the maximum score difference achieved at each index
        //Since Alice goes first, a positive score difference means Alice has a higher score than Bob, while a negative score difference means the opposite
        //A score difference of 0 means they tie
        dp = new int[stoneValue.length];
        Arrays.fill(dp, -1);
        stones = stoneValue;
        int diff = recurse(0); //Do the recursion starting at index 0
        if(diff==0)
        {
            return "Tie";
        }
        return diff<0 ? "Bob" : "Alice";
    }

    public int recurse(int ind)
    {
        if(ind==stones.length) //If the index is out of bounds, then return 0 since no stones can be picked up
        {
            return 0;
        }
        if(dp[ind]!=-1) //If we've already explored this index before, return the maximum difference we found already
        {
            return dp[ind];
        }
        int take1 = stones[ind] - recurse(ind+1); //Then to calculate the score difference for each scenario (take 1, take 2, take 3) just add the stones taken and subtract the result of the recursion at the index after the last taken stone
        //This works because the recursion is going to return the maximum score that the next player can achieve given the number of stones the current player takes, so subtracting the result of the recursion will tell us if Bob or Alice is going to win or tie at any index
        int take2 = Integer.MIN_VALUE;
        int take3 = Integer.MIN_VALUE;
        if(ind+1<stones.length)
        {
            take2 = stones[ind] + stones[ind+1] - recurse(ind+2);
        }
        if(ind+1<stones.length && ind+2<stones.length)
        {
            take3 = stones[ind] + stones[ind+1] + stones[ind+2] - recurse(ind+3);
        }
        int max = Math.max(take1, Math.max(take2, take3)); //Find the maximum score difference
        //Since we want Alice to win, if taking all 3 stones results in negative numbers, we store the result where she loses the least (the smallest negative)
        //Otherwise, store the maximum score difference that Alice can achieve
        return dp[ind] = max;
    }
}
