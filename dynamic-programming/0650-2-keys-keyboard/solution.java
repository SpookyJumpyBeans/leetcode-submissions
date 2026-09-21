// 650. 2 Keys Keyboard
// https://leetcode.com/problems/2-keys-keyboard/
// Medium | Java | Accepted 2026-09-20
// Runtime 22 ms | Memory 70.2 MB

class Solution {
    int[][] dp;
    int nn;
    public int minSteps(int n) {
        //Can also solve using prime factorization
        /*

 * Intuition (Prime Factorization):
 * Every sequence of 1 "Copy All" followed by several "Paste" operations acts as a multiplier. 
 * For example, Copy + Paste + Paste (3 steps) multiplies the current number of 'A's by 3.
 * To reach exactly 'n' characters, we need a sequence of multipliers whose product is 'n'. 
 * The total number of steps taken will be the sum of those multipliers.
 * 
 * To minimize the total steps, we want the sum of our multipliers to be as small as possible. 
 * Mathematically, breaking a large multiplier into smaller factors always reduces the sum 
 * (because a * b >= a + b for integers > 1). 
 * For example, multiplying by 6 takes 6 steps. But breaking it into factors of 2 and 3 takes 
 * only 2 + 3 = 5 steps.
 * 
 * Therefore, we just keep breaking 'n' down into its smallest possible factors (prime numbers). 
 * The minimum number of operations is simply the sum of the prime factors of 'n'.

 class Solution {
    public int minSteps(int n) {
        int steps = 0;
        int factor = 2;
        
        while (n > 1) {
            // While 'n' is divisible by the current factor, keep dividing and adding to steps
            while (n % factor == 0) {
                steps += factor;
                n /= factor;
            }
            factor++;
        }
        
        return steps;
    }
}
 
    */

        //2D DP Approach
        //There are 2 states, the current number of characters present on the screen and the total number of characters currently in our copy 
        dp = new int[n+1][n+1];
        for(int[] d : dp) //Initialize everything to -1
        {
            Arrays.fill(d, -1);
        }
        nn = n;
        return recurse(1, 0); //Recurse starting with a length of 1 for the starting 'A' on the screen and a length of 0 for the length of the string currently copied
    }

    public int recurse(int len, int numCopy)
    {
        if(len==nn) //If the length of the string is equal to the desired target length, return 0
        {
            return 0;
        }
        if(len>nn || numCopy>=nn) //Else, if it exceeds the target length (either the length of the string or the length of the copy)
        //Return a large number that will lose to the other path in the Math.min at the end
        {
            return 1000000;
        }
        if(dp[len][numCopy]!=-1) //If we've already explored this path, return the cached value
        {
            return dp[len][numCopy];
        }
        int copy = 1000000;
        if(len!=numCopy) //We only allow copying if what we're about to copy (the current string) is different than what's already in our copy  (len!=numCopy)
        {
            copy = 1 + recurse(len, len);
        }
        int paste = 1000000;
        if(numCopy!=0) //We only allow pasting when we actually have something to paste (numCopy>0)
        {
            paste = 1 + recurse(len+numCopy, numCopy);
        }
        return dp[len][numCopy] = Math.min(copy, paste); //Return the minimum of the two choices
    }
}
