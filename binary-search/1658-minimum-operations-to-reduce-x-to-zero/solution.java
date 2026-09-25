// 1658. Minimum Operations to Reduce X to Zero
// https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero/
// Medium | Java | Accepted 2026-09-23
// Runtime 4 ms | Memory 102 MB

class Solution{
    public int minOperations(int[] nums, int x) {
        //Use a sliding window approach since a DP solution would be 2D and O(N^2)
        //We essentially want to find a contiguous subarray that equals the total sum of the nums array - x, since the contiguous subarray is formed by removing elements from the left and right
        //The second we get a subarray that has the sum of nums - x, we update a global minimum
        int targ = 0;
        for(int i : nums)
        {
            targ+=i;
        }
        targ-=x; //This is the target sum, any subarray that has this sum reduced x to 0 to get there
        if(targ<0) //If the target is less than 0 (the sum of the array is less than x)
        {
            return -1; //Return -1 since it's not possible to reduce x to 0
        } 
        int currSum = 0; //Tracks the sliding window's sum
        //The start and end of the sliding window, which represents the contiguous subarray 
        //Add values at p2, and if the sum goes over our target, remove values at p1 and increment
        int p1 = 0; 
        int p2 = 0;
        int min = Integer.MAX_VALUE; //Global min
        while(p2<nums.length)
        {
            currSum+=nums[p2]; //Add the value at p2 
            while(currSum>targ && p1<=p2) //While this value at p2 makes the sum greater than targ, we subtract the value at our p1 pointer and increment it
            //As long as p1<=p2
            {
                currSum-=nums[p1];
                p1++;
            }
            if(currSum==targ) //If the sum after adding p2 and ensuring it's <=targ equals targ, we update the minimum
            {
                min = Math.min(min, p1+nums.length-p2-1); //p1 represents how many values from the left we have to remove and nums.length-p2-1 represents how many values from the right we have to remove to get nums sum - x
            }
            p2++; //Increment p2 at the end no matter what, since it's guaranteed that currSum<=targ after the iteration of the lop
        }
        return min == Integer.MAX_VALUE ? -1 : min; //Return the value
    }

}
