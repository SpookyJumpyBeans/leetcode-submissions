// 977. Squares of a Sorted Array
// https://leetcode.com/problems/squares-of-a-sorted-array/
// Easy | Java | Accepted 2022-08-06
// Runtime 2 ms | Memory 55.4 MB

class Solution {
    public int[] sortedSquares(int[] nums) {
        int[] ans = new int[nums.length];
        
        if(nums.length == 0)
            return ans;
        int n = nums.length;
        int i = 0, j = n - 1;
        for(int z = n-1; z >= 0; z--) {
            if(Math.abs(nums[i]) > Math.abs(nums[j])) {
                ans[z] = nums[i] * nums[i];
                i++;
            } else {
                ans[z] = nums[j] * nums[j];
                j--;
            }
        }
        return ans;
    }
}
