// 190. Reverse Bits
// https://leetcode.com/problems/reverse-bits/
// Easy | Java | Accepted 2026-01-08
// Runtime 0 ms | Memory 42.5 MB

class Solution {
    public int reverseBits(int n) {
        int ans = 0;
        for(int i = 0; i<=31; i++)
        {
            ans<<=1;
            ans |= n&1;
            n>>=1;
        }
        return ans;
    }
}
