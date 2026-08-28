// 16. 3Sum Closest
// https://leetcode.com/problems/3sum-closest/
// Medium | Java | Accepted 2026-02-08
// Runtime 17 ms | Memory 45.8 MB

class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int currBest = 10000000;
        int num = 0;
        for(int i = 0; i<nums.length; i++)
        {
            int point1 = i+1;
            int point2 = nums.length-1;
            while(point1<point2)
            {
                    if(Math.abs(nums[point1]+nums[point2]+nums[i]-target)<currBest)
                    {
                        currBest = Math.abs(nums[point1]+nums[point2]+nums[i]-target);
                        num = nums[point1]+nums[point2]+nums[i];
                    }
                    if(nums[point1]+nums[point2]+nums[i]>target)
                    {
                        point2--;
                    }
                    else
                    {
                        point1++;
                    }
            }
        }
        return num;
    }
}
