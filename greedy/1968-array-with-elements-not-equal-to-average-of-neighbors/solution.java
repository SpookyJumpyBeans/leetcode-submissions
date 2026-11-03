// 1968. Array With Elements Not Equal to Average of Neighbors
// https://leetcode.com/problems/array-with-elements-not-equal-to-average-of-neighbors/
// Medium | Java | Accepted 2026-11-02
// Runtime 4 ms | Memory 159.3 MB

/*
class Solution {
    public int[] rearrangeArray(int[] nums) {
        //My solution alternates between peaks and valleys, which ensures averages aren't possible
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.sort(nums);
        int i = 0;
        int ind = 0;
        while(2*i<n)
        {
            if((2*i)+1<n)
            {
                ans[ind] = nums[(2*i)+1];
                ind++;
            }
            ans[ind] = nums[2*i];
            ind++;
            i++;
        }
        return ans;
    }
}
*/
class Solution {
    public int[] rearrangeArray(int[] nums) {
        //Intuition: Have peaks and valleys, where the middle element is either smaller than or greater than both of its neighbors
        //That means it's mathematically impossible for the average of the neighbors to equal the middle element
        //Can do this without sorting the array
        //Simply swap the middle element and the neighbor to its left/right
        for(int i = 1; i<nums.length-1; i++)
        {
            if((nums[i-1]<nums[i] && nums[i]<nums[i+1])||(nums[i-1]>nums[i]&&nums[i]>nums[i+1]))
            {
                int temp = nums[i];
                nums[i] = nums[i+1];
                nums[i+1] = temp;
            }
        }
        return nums;
    }
}
