// 387. First Unique Character in a String
// https://leetcode.com/problems/first-unique-character-in-a-string/
// Easy | Java | Accepted 2026-10-29
// Runtime 6 ms | Memory 46.8 MB

class Solution {
    public int firstUniqChar(String s) {
        int[] count = new int[26];
        for(int i = 0; i<s.length(); i++)
        {
            count[s.charAt(i)-'a']++;
        }
        for(int i = 0; i<s.length(); i++)
        {
            if(count[s.charAt(i)-'a']==1)
            {
                return i;
            }
        }
        return -1;
    }
}
