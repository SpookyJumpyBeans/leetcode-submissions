// 7. Reverse Integer
// https://leetcode.com/problems/reverse-integer/
// Medium | Java | Accepted 2026-01-08
// Runtime 1 ms | Memory 42.5 MB

class Solution {
    public int reverse(int x) {
       long res = 0;
       while(x!=0)
       {
        int temp = x%10;
        res = res*10+temp;
        if(res>Integer.MAX_VALUE || res<Integer.MIN_VALUE)
        {
            return 0;
        }
        x/=10;
       }
       return (int) res;
    }
}
