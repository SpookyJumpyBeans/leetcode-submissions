// 1071. Greatest Common Divisor of Strings
// https://leetcode.com/problems/greatest-common-divisor-of-strings/
// Easy | Java | Accepted 2026-09-02
// Runtime 75 ms | Memory 47.6 MB

class Solution {
    public String gcdOfStrings(String str1, String str2) {
        if(str1.indexOf(str2)<0 && str2.indexOf(str1)<0)
        {
            return "";
        }
        String longer;
        String shorter;
        if(str1.length()>str2.length())
        {
            longer = str1;
            shorter = str2;
        }
        else
        {
            longer = str2;
            shorter = str1;
        }
        String ans = "";
        for(int i = 1; i<=shorter.length(); i++)
        {
            String pref = shorter.substring(0, i);
            while(str1.indexOf(pref)==0)
            {
                str1 = str1.substring(0, str1.indexOf(pref)) + str1.substring(str1.indexOf(pref)+pref.length());
            }
            while(str2.indexOf(pref)==0)
            {
                str2 = str2.substring(0, str2.indexOf(pref)) + str2.substring(str2.indexOf(pref)+pref.length());
            }
            if(str1.length() == 0 && str2.length() == 0)
            {
                if(pref.length() > ans.length())
                {
                    ans = pref;
                }
            }
            str1 = shorter;
            str2 = longer;
        }
        return ans;
    }
}
