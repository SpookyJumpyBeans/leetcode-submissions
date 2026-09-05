// 416. Partition Equal Subset Sum
// https://leetcode.com/problems/partition-equal-subset-sum/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {
    int[] num;
    int sum;
    boolean ans = false;
    public boolean canPartition(int[] nums) {
        num = nums;
        for(int i = 0; i<nums.length; i++)
        {
            sum+=nums[i];
        }
        recurse(0, 0);
        return ans;
    }

    public void recurse(int tempSum, int ind)
    {
        System.out.println(tempSum + " " + ind);
        for(int i = ind; i<num.length; i++)
        {
            tempSum+=num[i];
            recurse(tempSum, i+1);
            tempSum-=num[i];
        }
        if(tempSum==sum-tempSum)
        {
            ans = true;
            return;
        }
        return;
    }
}
