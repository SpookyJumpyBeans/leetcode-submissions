// 215. Kth Largest Element in an Array
// https://leetcode.com/problems/kth-largest-element-in-an-array/
// Medium | Java | Accepted 2025-12-02
// Runtime 26 ms | Memory 69.9 MB

class Solution {
   public int findKthLargest(int[] nums, int k) {
        final int N = nums.length;
        Arrays.sort(nums);
        return nums[N - k];
}
}
