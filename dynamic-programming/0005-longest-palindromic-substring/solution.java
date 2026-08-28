// 5. Longest Palindromic Substring
// https://leetcode.com/problems/longest-palindromic-substring/
// Medium | Java | Accepted 2026-01-14
// Runtime 121 ms | Memory 49.2 MB

class Solution {
    public String longestPalindrome(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        for(int i = 0; i<s.length(); i++)
        {
            dp[i][i]= true;
        }
        int max = 1;
        String ans = s.substring(0,1);
        for(int j = s.length()-1; j>=0; j--)
        {
            for(int k = j+1; k<s.length(); k++)
            {
                if(s.charAt(j)==s.charAt(k) && (k-j==1 || dp[j+1][k-1]))
                { 
                   if(k-j+1>max)
                   {
                    max = k-j+1;
                    ans = s.substring(j, k+1);
                   }
                    dp[j][k] = true;
                }
            }
        }
        return ans;
    }
}
