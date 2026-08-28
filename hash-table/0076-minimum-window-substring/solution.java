// 76. Minimum Window Substring
// https://leetcode.com/problems/minimum-window-substring/
// Hard | Java | Accepted 2025-11-05
// Runtime 2 ms | Memory 45.7 MB

class Solution {
    public String minWindow(String s, String t) {
      int start = 0; 
      int end = 0;
      int minStart = 0;
      int minLength = Integer.MAX_VALUE;
      int counter = t.length();
      int[] map = new int[128];
      for(int i = 0; i<t.length(); i++)
      {
        map[(int)t.charAt(i)]++;
      }
      while(end<s.length())
      {
        if(map[(int)s.charAt(end)]>0)
        {
            counter--;
        }
        map[s.charAt(end)]--;
        end++;
        while(counter == 0)
        {
            if(end-start < minLength)
            {
                minStart = start;
                minLength = end-start;
            }
            map[s.charAt(start)]++;
            if(map[(int) s.charAt(start)]>0)
            {
                counter++;
            }
            start++;
        }
      }
      return minLength == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart+minLength);
    }
}
