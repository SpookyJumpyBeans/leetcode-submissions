// 125. Valid Palindrome
// https://leetcode.com/problems/valid-palindrome/
// Easy | Java | Accepted 2026-08-15
// Runtime 15 ms | Memory 46.2 MB

class Solution {
    public boolean isPalindrome(String s) {
        int i = 0; 
        s = s.replaceAll("[^a-zA-Z0-9]", "");
        int j = s.length()-1;
        while(i<=j)
        {
            if(Character.toLowerCase(s.charAt(i))==Character.toLowerCase(s.charAt(j)))
            {
                i++;
                j--;
            }
            else
            {
                return false;
            }
        }
        return true;
    }
}
