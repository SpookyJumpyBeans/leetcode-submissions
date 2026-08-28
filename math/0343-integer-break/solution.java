// 343. Integer Break
// https://leetcode.com/problems/integer-break/
// Medium | Java | Accepted 2026-08-28
// Runtime 0 ms | Memory 42 MB

class Solution {
    int[] dp;
    public int integerBreak(int n) {
        if(n==2)
        {
            return 1;
        }
        if(n==3)
        {
            return 2;
        }
        int start = 1;
       while(n>4)
       {    
        start*=3;
        n-=3;
       }
       return start*n;
    }
    
}
