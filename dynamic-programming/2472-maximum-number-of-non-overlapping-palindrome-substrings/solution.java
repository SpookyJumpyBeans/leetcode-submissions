// 2472. Maximum Number of Non-overlapping Palindrome Substrings
// https://leetcode.com/problems/maximum-number-of-non-overlapping-palindrome-substrings/
// Hard | Java | Accepted 2026-09-16
// Runtime 5 ms | Memory 46.1 MB

class Solution {
    public int maxPalindromes(String s, int k) {
        int count = 0;
        //This problem can be implemented easily with greedy, but requires intuition and observation
        //A larger palindrome of length >=k+2 can always be broken down into a smaller palindrome of size k or k+1 depending on whether it has an even/odd amount of letters
        //Since we want to maximize the number of palindrome substrings, we can just check all intervals for size k and k+1 substrings
        //The second we find a substring of size k or k+1, we just set the search window to the end of the substring 
        //Return the number of substrings we find that are of size k/k+1
        for(int i = 0; i<=s.length()-k; i++)
        {
            if(s.substring(i, i+k).equals(new StringBuilder(s.substring(i, i+k)).reverse().toString()))
            {
                i = i+k-1;
                count++;
            }
            else if(i+k+1<=s.length() && s.substring(i, i+k+1).equals(new StringBuilder(s.substring(i, i+k+1)).reverse().toString()))
            {
                i = i+k;
                count++;
            }      
        }
        return count;
    }
}
