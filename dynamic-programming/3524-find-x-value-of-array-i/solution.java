// 3524. Find X Value of Array I
// https://leetcode.com/problems/find-x-value-of-array-i/
// Medium | Java | Accepted 2026-09-21
// Runtime 55 ms | Memory 103 MB

class Solution {
    long[][] dp;
    public long[] resultArray(int[] nums, int k) {
        //The question is asking in a convoluted way the number of contiguous subarrays that when multiplied have a remainder of r when modded by k for 0<=r<k-1
        //Can use memoization
        long[] ans = new long[k]; //This is the answer array for all the possible remainders up to k
        dp = new long[nums.length][k]; //Stores the number of contiguous subarrays that end at a certain index i that have a remainder of r when modded by k
        for(long[] m : dp) //Default all values to -1
        {
            Arrays.fill(m, -1);
        }
        for(int i = 0; i<nums.length; i++) //Since we aren't just returning the number of contiugous subarrays that end at one index and have a remainder of one number, we need to do a nested for loop through all possible combinations of subarrays that end at a certain index and have a remainder of r
        {
            for(int r = 0; r<k; r++)
            {
                ans[r] += recursion(i, r, k, nums); //Add the result of the recursion for the number of subarrays that have a remainder r
            }
        }
        return ans;
    }

    public long recursion(int ind, int remain, int k, int[] nums)
    {
        if(ind<0) //If the index is invalid, no subarray can end so return 0
        {
            return 0;
        }
        if(dp[ind][remain]!=-1) //Return cached state
        {
            return dp[ind][remain];
        }
        long count = 0;
        if(nums[ind]%k==remain) //If the current number itself (subarray of length 1) results in the remainder we want when modded by k, add 1 to the count
        {
            count++;
        }
        for(int rem = 0; rem<k; rem++) //Go through all the possible remainders from the index before this current value
        //This is to find cases where we can extend a previous subarray to this current index 
        {
            if(((long)rem*nums[ind])%k==remain) //If multiplying the current value by the previous remainder and then modding it with k results in the remainder we want, add the number of subarrays that end at the previous index that equal our remainder r when modded by k, since we are extending this subarray to our current value, meaning all the previous subarrays that result in r are also valid
            {
                count+=recursion(ind-1, rem, k, nums);
            }
        }
        return dp[ind][remain] = count; //Store and return
    }
}
