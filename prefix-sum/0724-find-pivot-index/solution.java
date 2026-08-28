// 724. Find Pivot Index
// https://leetcode.com/problems/find-pivot-index/
// Easy | Java | Accepted 2022-08-20
// Runtime 5 ms | Memory 53.5 MB

class Solution {
    public int pivotIndex(int[] nums) {
        int[] wow = new int[nums.length];
        int[] cow = new int[nums.length];
        int[] copy = nums.clone();
        if(nums.length == 1)
        {
            return 0;
        }
        for(int i = 0; i<nums.length; i++)
        {
            if(i==0)
            {
                wow[i] = nums[i];
            }
            else
            {
                wow[i] = nums[i] + nums[i-1];
                nums[i] = wow[i];
            }
        }
        for(int k = nums.length-1; k>=0 ;k--)
        {
            if(k == nums.length-1)
            {
                cow[k] = copy[k];
            }
            else
            {
                cow[k] = copy[k] + copy[k+1];
                copy[k] = cow[k];
            }
        }
        for(int m = 0 ;m<cow.length; m++)
        {
            if(cow[1]==0)
            {
                return 0;
            }
            else
            {
                if(cow[m]==wow[m])
                {
                    return m;
                }
            }
        }
        return -1;
    }
}
