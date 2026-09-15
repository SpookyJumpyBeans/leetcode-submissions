// 2974. Minimum Number Game
// https://leetcode.com/problems/minimum-number-game/
// Easy | Java | Accepted 2026-09-14
// Runtime 6 ms | Memory 47.4 MB

class Solution {
    public int[] numberGame(int[] nums) {
        int[] ans = new int[nums.length];
        Arrays.sort(nums);
        for(int i = 0; i<nums.length; i+=2)
        {
            ans[i] = nums[i+1];
            ans[i+1] = nums[i];
        }
        return ans;
    }
}
