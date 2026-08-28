// 3090. Maximum Length Substring With Two Occurrences
// https://leetcode.com/problems/maximum-length-substring-with-two-occurrences/
// Easy | Java | Accepted 2026-08-14
// Runtime 1 ms | Memory 43.6 MB

class Solution {
    public int maximumLengthSubstring(String s) {
       int[] freq = new int[26];
       for(int i = 0; i<2; i++)
       {
        freq[s.charAt(i)-'a']++;
       }
        int max = 2;
        int i = 0;
        int j = 2;
        while(j<s.length())
        {
            freq[s.charAt(j)-'a']++;
            while(freq[s.charAt(j)-'a']>2)
            {
                freq[s.charAt(i)-'a']--;
                i++;
            }
            max = Math.max(j-i+1, max);
            j++;
        }
        return max;
    }
}
