// 516. Longest Palindromic Subsequence
// https://leetcode.com/problems/longest-palindromic-subsequence/
// Medium | Java | Accepted 2026-09-21
// Runtime 102 ms | Memory 64.1 MB

class Solution {
    int[][] dp;
    String ss;
    public int longestPalindromeSubseq(String s) {
        dp = new int[s.length()][s.length()]; //Stores the longest palindromic subsequence with a window that starts at a specific index and ends at a specific index 
        for(int[] d : dp)
        {
            Arrays.fill(d, -1);
        }
        ss = s;
        return recurse(0, s.length()-1); //We want the longest palindromic subsequence that covers the entire string, so the initial start and end indices is the entire string
        //We basically want to keep decrementing the two start/end pointers until they converge
    }

    public int recurse(int start, int end)
    {
        if(start==end) //If the start and end pointers equal each other, return 1 since the palindrome is increasing by a length of 1
        {
            return 1;
        }
        if(start>end) //If the start is greater than the end, this is invalid, return 0
        {
            return 0;
        }
        if(dp[start][end]!=-1) //Return the cached state
        {
            return dp[start][end];
        } 
        int length = 0;
        if(ss.charAt(start)==ss.charAt(end)) //If the start and end pointers equal, they both contribute 1 character (total of 2 characters) to the length of the palindrome
        {
            length = 2 + recurse(start+1, end-1); //Now decrement both pointers and add the 2 characters contributed to the result of the longest subsequence generated with the now smaller window
        }   
        length = Math.max(length, Math.max(recurse(start+1, end), recurse(start, end-1))); //If the characters at the start/end pointers don't equal each other, then we want to try both options of moving the start pointer forward and moving the end pointer back
        //Return the maximum of all 3 possibilities
        return dp[start][end] = length;
    }
}
