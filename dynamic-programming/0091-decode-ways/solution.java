// 91. Decode Ways
// https://leetcode.com/problems/decode-ways/
// Medium | Java | Accepted 2026-01-15
// Runtime 1 ms | Memory 43.2 MB

class Solution {
    public int numDecodings(String s) {
        int[] dp = new int[s.length()+1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '0' ? 0 : 1;
        for(int j = 2; j<=s.length(); j++)
        {
            if(s.charAt(j-1)!='0')
            {
                dp[j]+=dp[j-1];
            }
            if(Integer.parseInt(s.substring(j-2, j))>=10 && Integer.parseInt(s.substring(j-2, j))<=26)
            {
                dp[j]+=dp[j-2];
            }
            if(dp[j]==0)
            {
                return 0;
            }
        }
        return dp[s.length()];
    }
}
