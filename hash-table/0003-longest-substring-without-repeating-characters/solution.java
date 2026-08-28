// 3. Longest Substring Without Repeating Characters
// https://leetcode.com/problems/longest-substring-without-repeating-characters/
// Medium | Java | Accepted 2022-08-07
// Runtime 241 ms | Memory 118 MB

class Solution {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> joe = new HashMap<>();
        int maxx = 0;
        int m = 0; 
        int k = 0;
        if(s.length()==1)
        {
            return 1;
        }
        while(k<s.length())
        {
           if(joe.containsKey(s.charAt(k)))
           {
               maxx = Math.max(maxx,k-m);
               m = joe.get(s.charAt(k))+1;
               joe.clear();
               k = m;
           }
            else
            {
                joe.put(s.charAt(k),k);
                k++;
            }
        }
        maxx = Math.max(maxx, k-m);
        return maxx;
    }
}
