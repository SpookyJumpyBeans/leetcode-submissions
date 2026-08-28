// 3622. Check Divisibility by Digit Sum and Product
// https://leetcode.com/problems/check-divisibility-by-digit-sum-and-product/
// Easy | Java | Accepted 2026-08-22
// Runtime 0 ms | Memory 42.3 MB

class Solution {
    public boolean checkDivisibility(int n) {
        long sum = 0;
        long product = 1;
        int temp = n;
        while(n>0)
        {
            int t = n%10;
            sum+=t;
            product*=t;
            n/=10;
        }
        return temp%(sum+product) == 0 ? true : false;
    }
}
