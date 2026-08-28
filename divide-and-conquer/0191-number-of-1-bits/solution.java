// 191. Number of 1 Bits
// https://leetcode.com/problems/number-of-1-bits/
// Easy | Java | Accepted 2026-01-07
// Runtime 0 ms | Memory 42.3 MB

class Solution {
    public int hammingWeight(int n) {
        int count = 0;
        while(n>0)
        {
            if(n%2!=0)
            {
                count++;
            }
            n/=2;
        }
        return count;
    }
}
