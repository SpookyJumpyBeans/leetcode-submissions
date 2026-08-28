// 27. Remove Element
// https://leetcode.com/problems/remove-element/
// Easy | Java | Accepted 2025-10-02
// Runtime 0 ms | Memory 41.9 MB

class Solution {
    public int removeElement(int[] nums, int val) {
        int[] arr = new int[nums.length];
        for(int i = 0; i<nums.length; i++)
        {
            arr[i] = nums[i];
        }
        int start = 0; 
        int count = 0;
        int numsI = 0;
        while(start<nums.length)
        {
            while(start<nums.length&& arr[start]==val){
                start++;
                count++;
            }
            if(start>=nums.length)
            {
                break;
            }
            nums[numsI] = arr[start];
            numsI++;
            start++;
            
        }
        return nums.length - count;
    }
}
