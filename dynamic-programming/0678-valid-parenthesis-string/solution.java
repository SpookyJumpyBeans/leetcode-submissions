// 678. Valid Parenthesis String
// https://leetcode.com/problems/valid-parenthesis-string/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {
    public boolean checkValidString(String s) {
       Stack<Integer> stars = new Stack<>();
       Stack<Integer> left = new Stack<>();
       for(int i = 0; i<s.length(); i++)
       {
        if(s.charAt(i)=='(')
        {
            left.push(i);
        }
        if(s.charAt(i)=='*')
        {
            stars.push(i);
        }
        if(s.charAt(i)==')')
        {
            if(!left.isEmpty())
            {
                left.pop();
            }
            else if(!stars.isEmpty())
            {
                stars.pop();
            }
            else
            {
                return false;
            }
        }
       }
        while(!left.isEmpty() && !stars.isEmpty())
        {
            int l = left.pop();
            int ss = stars.pop();
            if(ss<l)
            {
                return false;
            }
        }
        if(stars.size()>=left.size())
        {
            return true;
        }
        return false;
    }
}
