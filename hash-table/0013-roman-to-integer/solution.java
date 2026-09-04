// 13. Roman to Integer
// https://leetcode.com/problems/roman-to-integer/
// Easy | Java | Accepted 2026-09-02
// Runtime 3 ms | Memory 47 MB

class Solution {
    public int romanToInt(String s) {
        int ind = 0;
        int count = 0;
        while(ind<s.length())
        {
            if(s.charAt(ind)=='V')
            {
                count+=5;
            }
            if(s.charAt(ind)=='L')
            {
                count+=50;
            }
            if(s.charAt(ind)=='D')
            {
                count+=500;
            }
            if(s.charAt(ind)=='M')
            {
                count+=1000;
            }
            if(s.charAt(ind)=='I')
            {
                    if(ind+1<s.length() && s.charAt(ind+1)=='V')
                    {
                        count+=4;
                        ind++;
                    }
                    else if(ind+1<s.length() && s.charAt(ind+1)=='X')
                    {
                        count+=9;
                        ind++;
                    }
                    else
                    {
                        count++;
                    }
            }
            else if(s.charAt(ind)=='X')
            {
                    if(ind+1<s.length() && s.charAt(ind+1)=='L')
                    {
                        count+=40;
                        ind++;
                    }
                    else if(ind+1<s.length() && s.charAt(ind+1)=='C') 
                    {
                        count+=90;
                        ind++;
                    }
                    else
                    {
                        count+=10;
                    }
            }
            else if(s.charAt(ind)=='C')
            {
                    if(ind+1<s.length() && s.charAt(ind+1)=='D')
                    {
                        count+=400;
                        ind++;
                    }
                    else if(ind+1<s.length() && s.charAt(ind+1)=='M')
                    {
                        count+=900;
                        ind++;
                    }
                    else
                    {
                        count+=100;
                    }
            }
            ind++;
        }
        return count;
    }
}
