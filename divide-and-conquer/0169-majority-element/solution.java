// 169. Majority Element
// https://leetcode.com/problems/majority-element/
// Easy | Java | Accepted 2026-09-01
// Runtime 9 ms | Memory 55.6 MB

class Solution {
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        int max = Integer.MIN_VALUE;
        int maxNum = nums[0];
        int temp = 1;
        int j = 0;
        for(int i = j+1; i<nums.length; i++)
        {
            if(nums[i]!=nums[j])
            {
                if(temp>max)
                {
                    max = temp;
                    maxNum = nums[j];
                }
                temp = 1;
                j = i;
            }
            else
            {
                temp++;
            }
        }
        if(temp>max)
        {
                    max = temp;
                    maxNum = nums[j];
        }
        return maxNum;
    }
}
