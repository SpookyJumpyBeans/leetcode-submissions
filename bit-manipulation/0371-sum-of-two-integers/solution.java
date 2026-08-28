// 371. Sum of Two Integers
// https://leetcode.com/problems/sum-of-two-integers/
// Medium | Java | Accepted 2026-01-08
// Runtime 0 ms | Memory 41.9 MB

class Solution {
    public int getSum(int a, int b) {
        int sum = 0;
        int carry = 0;
        while(b!=0)
        {
            sum = a ^ b;
            carry = (a&b)<<1;
            a = sum;
            b = carry;
        }
        return a;
    }
}
