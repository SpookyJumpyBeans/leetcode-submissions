// 680. Valid Palindrome II
// https://leetcode.com/problems/valid-palindrome-ii/
// Easy | Java | Accepted 2026-08-30
// Runtime 4 ms | Memory 47.9 MB

class Solution {
    public boolean validPalindrome(String s) {
        int p1 = 0;
        int p2 = s.length()-1;
        while(p1<=p2)
        {
            if(s.charAt(p1)!=s.charAt(p2))
            {
                return palin(s, p1+1, p2) || palin(s, p1, p2-1);
            }
            p1++;
            p2--;
        }
        return true;
    }
    
    public boolean palin(String s, int st, int e)
    {
        int p1 = st;
        int p2 = e;
        while(p1<=p2)
        {
              if(s.charAt(p1)!=s.charAt(p2))
            {
                return false;
            }
            p1++;
            p2--;
        }
        return true;
    }
}
