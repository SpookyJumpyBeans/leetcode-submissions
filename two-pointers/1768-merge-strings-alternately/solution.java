// 1768. Merge Strings Alternately
// https://leetcode.com/problems/merge-strings-alternately/
// Easy | Java | Accepted 2026-08-29
// Runtime 1 ms | Memory 43 MB

class Solution {
    public String mergeAlternately(String word1, String word2) {
        int w1 = 0;
        int w2 = 0;
        StringBuilder ans = new StringBuilder();
        while(w1<word1.length() && w2<word2.length())
        {
            ans.append(word1.charAt(w1));
            ans.append(word2.charAt(w2));
            w1++;
            w2++;
        }
        if(w1!=word1.length())
        {
            ans.append(word1.substring(w1));
        }
        if(w2!=word2.length())
        {
            ans.append(word2.substring(w2));
        }
        return ans.toString();
    }
}
