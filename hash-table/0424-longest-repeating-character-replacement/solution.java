// 424. Longest Repeating Character Replacement
// https://leetcode.com/problems/longest-repeating-character-replacement/
// Medium | Java | Accepted 2025-09-19
// Runtime 62 ms | Memory 45.5 MB

class Solution {
    public int characterReplacement(String s, int k) {
        Map<String, Integer> map = new HashMap<>();
        int maxLen = 0;
        int l = 0;
        int maxFreq = 0;
        for(int r = 0; r<s.length(); r++)
        {
            if(!map.containsKey(s.substring(r,r+1)))
            {
                map.put(s.substring(r,r+1), 1);
            }
            else   
            {
                map.put(s.substring(r,r+1),map.get(s.substring(r,r+1))+1);
            }
            maxFreq = Math.max(map.get(s.substring(r,r+1)), maxFreq);
            while((r-l+1)-maxFreq > k)
            {
                map.put(s.substring(l,l+1),map.get(s.substring(l,l+1))-1);
                l++;
            }
            maxLen = Math.max((r-l+1), maxLen);
        }
        return maxLen;
    }
}
