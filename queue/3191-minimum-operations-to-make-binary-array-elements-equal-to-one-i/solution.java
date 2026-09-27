// 3191. Minimum Operations to Make Binary Array Elements Equal to One I
// https://leetcode.com/problems/minimum-operations-to-make-binary-array-elements-equal-to-one-i/
// Medium | Java | Accepted 2026-09-26
// Runtime 6 ms | Memory 102.2 MB

class Solution {
    public int minOperations(int[] nums) {
        //Don't worry about any theory just simulate it
        int count = 0;
        for(int i = 0; i<nums.length-2; i++)
        {
            if(nums[i]==0) //If we hit a 0, we want to flip it
            {
                count++;
                nums[i] = 1; //It is now guaranteed everything up to and including i is now 1
                nums[i+1] = nums[i+1] == 1 ? 0 : 1; //Flip the 2 other elements in the window
                nums[i+2] = nums[i+2] == 1 ? 0 : 1;
            }
        }
        if(nums[nums.length-2]==0 || nums[nums.length-1]==0) //The only possible way we can't make all elements 1 is if the last 2 elements aren't 1
        {
            return -1;
        }
        return count;
    }
}
