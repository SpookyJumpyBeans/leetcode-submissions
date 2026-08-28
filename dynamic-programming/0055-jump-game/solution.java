// 55. Jump Game
// https://leetcode.com/problems/jump-game/
// Medium | Java | Accepted 2026-01-12
// Runtime 3 ms | Memory 47.9 MB

class Solution {
    public boolean canJump(int[] nums) {
        int maxReach = 0;
        for(int i = 0; i<nums.length; i++)
        {
            if(i>maxReach)
            {
                return false;
            }
            maxReach = Math.max(maxReach, nums[i] + i);
        }
        return true;
    }
}
