// 344. Reverse String
// https://leetcode.com/problems/reverse-string/
// Easy | Java | Accepted 2026-08-30
// Runtime 0 ms | Memory 48.4 MB

class Solution {
    public void reverseString(char[] s) {
        int p1 = 0;
        int p2 = s.length-1;
        while(p1<p2)
        {
            char temp = s[p1];
            s[p1] = s[p2];
            s[p2] = temp;
            p1++;
            p2--;
        }
    }
}
