// 50. Pow(x, n)
// https://leetcode.com/problems/powx-n/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {
    public double myPow(double x, int n) {
         if(x==0)
                {
                    return 0;
                }
            if(n==0)
                {
                    return 1;
            }
            double res = recurse(x, Math.abs(n/2));
            System.out.println(res);
            res*=res;
            if(n%2!=0)
            {
                res*=x;
            }
            if(n<0)
            {
                res = 1/res;
            }
            return res;
    }
    public double recurse(double x, int n)
    {
        if(n==0)
        {
            return 1;
        }
        return x*recurse(x, n-1);
    }
}
