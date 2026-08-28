// 8. String to Integer (atoi)
// https://leetcode.com/problems/string-to-integer-atoi/
// Medium | Java | Accepted 2026-08-13
// Runtime 1 ms | Memory 44.1 MB

class Solution {
    public int myAtoi(String s) {
        int ans = 0;
        int i = 0;
        boolean negative = false; //Flag to check if the number is negative or not
        while(i<s.length())
        {
            char t = s.charAt(i);
            if(!Character.isDigit(t) && t!='-' && t!='+' && t!=' ') //If the character isn't whitespace and not a digit or a sign, then end the iteration immediately
            {
                break;
            }
            if(t==' ') //If the character is whitespace, just keep iterating
            {
                i++;
                continue;
            }
            if(t=='+') //If the character is a sign, check if the next character is a digit
            //If it isn't, immediately return 0
            {
                 if(i+1<s.length() && !Character.isDigit(s.charAt(i+1)))
                {
                   return 0;
                }
                i++;
                continue;
            }
            if(t=='-')
            {
                if(i+1<s.length() && Character.isDigit(s.charAt(i+1))) //If the character is a sign, check if the next character is a digit
            //If it isn't, immediately return 0
            //Also set the negative flag to true if the next character is a digit
                {
                    negative = true;
                }
                else
                {
                    return 0;
                }
                i++;
                continue;
            }
            if(Character.isDigit(t)) //Now that all the signs and whitespace are taken care of, start iterating through the actual integer if there are digits
            {
                while(i<s.length() && s.charAt(i)-'0'==0) //Take care of leading zeroes first
                {
                    i++;
                }
                
                while(i<s.length()&&Character.isDigit(s.charAt(i)))
                {
                    int prev = ans;
                    if(negative)
                    {
                        if(ans < Integer.MIN_VALUE/10 || (ans==Integer.MIN_VALUE/10 && (s.charAt(i)-'0')>8))
                        {
                            return Integer.MIN_VALUE;
                        }
                        ans*=10;
                        ans-=(s.charAt(i)-'0');
                    }
                    else
                    {
                        if(ans > Integer.MAX_VALUE/10 || (ans==Integer.MAX_VALUE/10 && (s.charAt(i)-'0')>7))
                        {
                            return Integer.MAX_VALUE;
                        }
                       ans*=10;
                        ans+=(s.charAt(i)-'0');
                                               
                    }
                    i++;
                }
                break;
            }
        }
        return ans;
    }
}
