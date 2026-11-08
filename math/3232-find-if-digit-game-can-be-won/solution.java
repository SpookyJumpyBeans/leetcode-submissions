// 3232. Find if Digit Game Can Be Won
// https://leetcode.com/problems/find-if-digit-game-can-be-won/
// Easy | Java | Accepted 2026-11-07
// Runtime 1 ms | Memory 44.9 MB

class Solution {
    public boolean canAliceWin(int[] nums) {
        int countS = 0;
        int countD = 0;
        for(int i : nums)
        {
            countS += i < 10 ? i : 0;
            countD += i >=10 ? i : 0;
        }
        return countS>countD || countD>countS;
    }
}
