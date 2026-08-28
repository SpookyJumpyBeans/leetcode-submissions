// 1137. N-th Tribonacci Number
// https://leetcode.com/problems/n-th-tribonacci-number/
// Easy | Java | Accepted 2026-08-27
// Runtime 0 ms | Memory 42.2 MB

class Solution {
    public int tribonacci(int n) {
        if(n<2)
        {
            return n == 0 ? 0 : 1;
        }
        int[] trib = new int[n+1];
        trib[0] = 0;
        trib[1] = 1;
        trib[2] = 1;
        for(int i = 3; i<=n; i++)
        {
            trib[i] = trib[i-1] + trib[i-2] + trib[i-3];
        }
        return trib[n];
    }
}
