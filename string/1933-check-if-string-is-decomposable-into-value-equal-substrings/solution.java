// 1933. Check if String Is Decomposable Into Value-Equal Substrings
// https://leetcode.com/problems/check-if-string-is-decomposable-into-value-equal-substrings/
// Easy | Java | Accepted 2026-11-20
// Runtime 16 ms | Memory 46.3 MB

class Solution {
    public boolean isDecomposable(String s) {
        for(int i = 0; i<s.length()-1; i++)
        {
            if(s.charAt(i)!=s.charAt(i+1))
            {
                continue;
            }
            String temp = s.substring(0, i) + s.substring(i+2);
            if(temp.length()%3!=0)
            {
                continue;
            }
            boolean flag = false;
            for(int j = 0; j<temp.length(); j+=3)
            {
                if(temp.charAt(j)!=temp.charAt(j+1) || temp.charAt(j+1)!=temp.charAt(j+2))
                {
                    flag = true;
                    break;
                }
            }
            if(!flag)
            {
                return true;
            }
        }
        return false;
    }
}
