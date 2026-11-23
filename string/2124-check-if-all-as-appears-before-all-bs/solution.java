// 2124. Check if All A's Appears Before All B's
// https://leetcode.com/problems/check-if-all-as-appears-before-all-bs/
// Easy | Java | Accepted 2026-11-22
// Runtime 1 ms | Memory 42.4 MB

class Solution {
    public boolean checkString(String s) {
        int b = -1;
        for(int i = 0; i<s.length(); i++)
        {
            if(s.charAt(i)=='a')
            {
                if(b!=-1 && b<i)
                {
                    return false;
                }
            }
            else
            {
                b = i;
            }
        }
        return true;
    }
}
