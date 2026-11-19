// 1509. Minimum Difference Between Largest and Smallest Value in Three Moves
// https://leetcode.com/problems/minimum-difference-between-largest-and-smallest-value-in-three-moves/
// Medium | Java | Accepted 2026-11-13
// Runtime 21 ms | Memory 60 MB

class Solution {
    public int minDifference(int[] nums) {
        if(nums.length<=3)
        {
            return 0;
        }
        Arrays.sort(nums);
        int n = nums.length;
        int min = Math.min(nums[n-4]-nums[0], Math.min(nums[n-3]-nums[1], Math.min(nums[n-2]-nums[2], nums[n-1] - nums[3])));
        //nums[n-4] - nums[0] (drop the 3 largest elements)
        //nums[n-3] - nums[1] (drop the 2 largest and smallest)
        //nums[n-2] - nums[2] (drop the 2 smallest and largest)
        //nums[n-1] - nums[3] (drop the 3 smallest);
        return min;
    }
}
