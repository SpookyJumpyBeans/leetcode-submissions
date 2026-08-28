// 279. Perfect Squares
// https://leetcode.com/problems/perfect-squares/
// Medium | Java | Accepted 2026-08-23
// Runtime 271 ms | Memory 45 MB

class Solution {
    List<Integer> choose = new ArrayList<>();
    int ans = Integer.MAX_VALUE;
    int[] dp;
    public int numSquares(int n) {
        dp = new int[n+1];
        int start = 1;
        for(int i = 0; i<=n; i++)
        {
                dp[i] = -1;
        }
        while(start*start<=n)
        {
            choose.add(start*start);
            start++;
        }
        return recurse(n);
    }
    
    public int recurse(int left)
    {
        if(left<0)
        {
            return Integer.MAX_VALUE-1;
        }
        if(dp[left]!=-1)
        {
            return dp[left];
        }
        if(left==0)
        {;
            return 0;
        }
        int temp = Integer.MAX_VALUE;
        for(int i = 0; i<choose.size(); i++)
        {
            if (left - choose.get(i) < 0) {
                break; 
            }   
            left-=choose.get(i);
            int t = 1+recurse(left);
            left+=choose.get(i);
            temp = Math.min(temp, t);
        }
        dp[left] = temp;
        return dp[left];
    }
}
/*
Tabulation solution
class Solution {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];

        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = i; 

            for (int j = 1; j * j <= i; j++) {
                int square = j * j;

                dp[i] = Math.min(dp[i], dp[i - square] + 1);
            }
        }

        return dp[n];
    }
}
*/
