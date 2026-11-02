// 453. Minimum Moves to Equal Array Elements
// https://leetcode.com/problems/minimum-moves-to-equal-array-elements/
// Medium | Java | Accepted 2026-11-01
// Runtime 2 ms | Memory 46.6 MB

class Solution {
    public int minMoves(int[] nums) {
        //Look at the operation backwards
        //Increasing all other elements by 1 is the same as decreasing an element by 1
        //The relative difference between all elements is the same
        //Ex: [1, 2, 3]
        //Increase by 1 
        //[2, 3, 3]
        //Relative difference is 1 
        //Decrease 3 by 1
        //[1, 2, 2]
        //Relative difference is still 1
        //Effectively, we want to sum the difference between all elements in the array and the smallest element
        int min = Integer.MAX_VALUE;
        for(int i : nums)
        {
            min = Math.min(min, i);
        }
        int tot = 0;
        for(int i = 0; i<nums.length; i++)
        {
            tot+=(nums[i]-min);
        }
        return tot;
    }
}
