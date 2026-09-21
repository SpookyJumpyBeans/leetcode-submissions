// 3498. Reverse Degree of a String
// https://leetcode.com/problems/reverse-degree-of-a-string/
// Easy | Java | Accepted 2026-09-20
// Runtime 1 ms | Memory 44.2 MB

class Solution {
    public int reverseDegree(String s) {
        int count = 0;
        for(int i = 0; i<s.length(); i++)
        {
            count+=(26 - (s.charAt(i)-'a'))*(i+1); //26 - the index of the character before reversing it (a-z, 0-25) gives the reverse index of the character in the alphabet
            //Multiply it with the index of the character
        }
        return count;
    }
}
