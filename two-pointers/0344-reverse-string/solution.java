// 344. Reverse String
// https://leetcode.com/problems/reverse-string/
// Easy | Java | Accepted 2022-08-06
// Runtime 2 ms | Memory 57.7 MB

class Solution {
    public void reverseString(char[] s) {
        int j = s.length-1;
        for(int i = 0; i<s.length/2; i++)
        {
            char temp = s[i];
            s[i] = s[j];
            s[j] = temp;
            j--;
        }
    }
}
