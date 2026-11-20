// 2243. Calculate Digit Sum of a String
// https://leetcode.com/problems/calculate-digit-sum-of-a-string/
// Easy | Java | Accepted 2026-11-19
// Runtime 1 ms | Memory 42.6 MB

class Solution {
    public String digitSum(String s, int k) {
        if(s.length()<=k)
        {
            return s;
        }
            StringBuilder temp = new StringBuilder();
            while(true)
            {
                for(int i = 0; i<s.length(); i+=k)
                {
                    int count = 0;
                    for(int j = i; j<i+k && j<s.length(); j++)
                    {
                        count+=s.charAt(j) - '0';
                    }
                    temp.append(count);
                }
                if(temp.length()<=k)
                {
                    break;
                }
                s = temp.toString();
                temp = new StringBuilder();
            }
            return temp.toString();
    }
}
