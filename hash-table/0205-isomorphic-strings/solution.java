// 205. Isomorphic Strings
// https://leetcode.com/problems/isomorphic-strings/
// Easy | Java | Accepted 2022-08-21
// Runtime 399 ms | Memory 342.7 MB

class Solution {
    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> joe = new HashMap<>();
                Map<Character, Character> joes = new HashMap<>();
        String bruh = "";
        for(int i = 0; i<s.length(); i++)
        {
            if(joe.get(s.charAt(i))==null&&joes.get(t.charAt(i))==null)
               {
            joe.put(s.charAt(i), t.charAt(i));
joes.put(t.charAt(i), s.charAt(i));
               }
            bruh+=joe.get(s.charAt(i));
        }
        if(bruh.equals(t))
        {
            return true;
        }
        return false;
    }
}
