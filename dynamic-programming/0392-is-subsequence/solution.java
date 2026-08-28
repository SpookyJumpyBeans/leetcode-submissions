// 392. Is Subsequence
// https://leetcode.com/problems/is-subsequence/
// Easy | Java | Accepted 2022-08-21
// Runtime 1 ms | Memory 41.8 MB

class Solution {
    public boolean isSubsequence(String s, String t) {
        if(s.length()==0&&t.length()>0)
        {
            return true;
        }
        if(s.length()>0&&t.length()==0)
        {
            return false;
        }
        int m = 0;
        for(int i = 0; i<t.length(); i++)
        {
            if(m==s.length())
            {
                break;
            }
            if(t.charAt(i)==s.charAt(m))
            {
                m++;
            }
        }
        System.out.println(m);
        if(m==s.length())
        {
            return true;
        }
        return false;
    }
}
