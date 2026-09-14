// 3871. Count Commas in Range II
// https://leetcode.com/problems/count-commas-in-range-ii/
// Medium | Java | Accepted 2026-09-13
// Runtime 1 ms | Memory 42.9 MB

class Solution {
    public long countCommas(long n) {
        long count = 0;
        long range = 1000;
        //Intuition:
        //Count how many numbers are in each digit range (1000, 1000000, 1000000000, etc)
        //Since each range introduces one more comma, just add how many values of n are in each range (n-range+1)
        //Then multiply the range by 1000 to get to the next digit range which introduces another comma
        while(n>=range)
        {
            count+=(n-range+1);
            range*=1000;
        }
        return count;
    }
}
