// 509. Fibonacci Number
// https://leetcode.com/problems/fibonacci-number/
// Easy | Java | Accepted 2022-10-08
// Runtime 0 ms | Memory 41.2 MB

class Solution {
    public int fib(int n) {
        int[] arr = new int[n+1];
        arr[0] = 0;
        if(n>=1)
        {
            arr[1] = 1;
        for(int i = 2; i<n+1; i++)
        {
            arr[i] = arr[i-1] + arr[i-2];
        }
        }
        return arr[n];
    }
}
