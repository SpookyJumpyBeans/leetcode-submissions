// 647. Palindromic Substrings
// https://leetcode.com/problems/palindromic-substrings/
// Medium | Java | Accepted 2026-01-15
// Runtime 15 ms | Memory 46.5 MB

class Solution {
    public int countSubstrings(String s) {
       boolean[][] dp = new boolean[s.length()][s.length()];
       for(int i = 0; i<s.length(); i++)
       {
        dp[i][i] = true;
       }
       int count = s.length();
       for(int j = s.length()-1; j>=0; j--)
       {
        for(int k = j+1; k<s.length(); k++)
        {
            if(s.charAt(k)==s.charAt(j) && (k-j==1 || dp[j+1][k-1]))
            {
                dp[j][k] = true;
                count++;
            } 
        }
       }
       return count;
    }
}
