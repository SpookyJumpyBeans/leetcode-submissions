// 69. Sqrt(x)
// https://leetcode.com/problems/sqrtx/
// Easy | Java | Accepted 2026-08-30
// Runtime 1 ms | Memory 42.7 MB

class Solution {
    public int mySqrt(int x) {
        int l = 0;
        int r = x;
        int ans = 0;
        while(l<=r)
        {
            int mid = l+(r-l)/2;
            if(mid>46340)
            {
                r = mid-1;
                continue;
            }
            if(mid*mid>x)
            {
                r = mid-1;
            }
            else if(mid*mid<x)
            {
                ans = mid;
                l = mid+1;
            }
            else
            {
                return mid;
            }
        }
        return ans;
    }
}
