// 242. Valid Anagram
// https://leetcode.com/problems/valid-anagram/
// Easy | Java | Accepted 2024-09-04
// Runtime 71 ms | Memory 54.6 MB

class Solution {
    public boolean isAnagram(String s, String t) {
       if(s.length()!=t.length())
       {
        return false;
       }
       String[] ss = s.split("");
       String[] tt = t.split("");
       Arrays.sort(ss);
       Arrays.sort(tt);
       for(int i = 0; i<s.length(); i++)
       {
        if(!ss[i].equals(tt[i]))
        {
            return false;
        }
       }
       return true;
    }
}
