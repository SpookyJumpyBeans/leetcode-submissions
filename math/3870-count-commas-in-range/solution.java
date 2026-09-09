// 3870. Count Commas in Range
// https://leetcode.com/problems/count-commas-in-range/
// Easy | Java | Accepted 2026-09-08
// Runtime 1 ms | Memory 42.5 MB

class Solution {
    public int countCommas(int n) {
        if(n<1000)
        {
            return 0;
        }
        return n-1000+1;
    }
}
