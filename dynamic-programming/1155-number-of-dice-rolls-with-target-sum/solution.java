// 1155. Number of Dice Rolls With Target Sum
// https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/
// Medium | Java | Accepted 2026-09-21
// Runtime 9 ms | Memory 43.7 MB

class Solution {
    int[][] dp;
    int targ;
    int kk;
    int nn;
    public int numRollsToTarget(int n, int k, int target) {
        dp = new int[n+1][target+1]; //Represents our current state, how many dice we've used thus far and what our current sum is at
        targ = target;
        kk = k;
        nn = n;
        for(int[] d : dp)
        {
            Arrays.fill(d, -1);
        }
        return recurse(0, 0); //Start at 0 dice used and a sum of 0
    }

    public int recurse(int diceUsed, int sum)
    {
        if(diceUsed==nn && sum==targ) //If we've used the total amount of dice available and our sum equals the target, return 1 for a successful combination
        {
            return 1;
        }
        if(diceUsed==nn) //Else, the sum doesn't equal the target, return 0 since we've used the number of dice available but it's not a valid combination
        { 
            return 0;
        }
        if(dp[diceUsed][sum]!=-1) //Return cached state
        {
            return dp[diceUsed][sum];
        }
        int count = 0; //This counts how many of the combinations starting at our current state result in the target sum
        for(int i = 1; i<=kk; i++) //Go through all the possible faces of our die
        {
            if(sum+i<=targ) //If adding this to the current sum is valid (it doesn't go over the target)
            {
                count = (count+recurse(diceUsed+1, sum+i))%1000000007; //Add the result of the recursion of using this die and adding the die's face to our current sum modded 
            }
        }
        return dp[diceUsed][sum] = count; //Return the stored count
    }
}
