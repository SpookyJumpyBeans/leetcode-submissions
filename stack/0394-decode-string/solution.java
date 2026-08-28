// 394. Decode String
// https://leetcode.com/problems/decode-string/
// Medium | Java | Accepted 2026-08-25
// Runtime 0 ms | Memory 43.1 MB

class Solution {
    public String decodeString(String s) {
        StringBuilder ans = new StringBuilder();
        int ind = 0;
        while(ind<s.length())
        {
            if(Character.isDigit(s.charAt(ind)))
            {
                int temp1 = ind+1;
                while(Character.isDigit(s.charAt(temp1)))
                {   
                    temp1++;
                }
                int num = Integer.parseInt(s.substring(ind, temp1));
                ind = temp1+1;
                int temp = temp1+2;
                int count = 1;
                while(s.charAt(temp)!=']' || count!=0)
                {
                    if(s.charAt(temp)=='[')
                    {
                        count++;
                    }
                    if(s.charAt(temp)==']')
                    {
                        count--;
                    }
                    if(count==0)
                    {
                        break;
                    }
                    temp++;
                }
                String res = decodeString(s.substring(ind, temp));
                ans.repeat(res, num);
                ind = temp+1;            
            }
            else
            {
                ans.append(s.charAt(ind));
                ind++;
            }
        }
        return ans.toString();
    }
}
