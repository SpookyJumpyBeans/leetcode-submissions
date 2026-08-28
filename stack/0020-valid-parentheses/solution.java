// 20. Valid Parentheses
// https://leetcode.com/problems/valid-parentheses/
// Easy | Java | Accepted 2025-10-26
// Runtime 9 ms | Memory 45.6 MB

class Solution {
    public boolean isValid(String s) {
        Stack<String> stack = new Stack<>();
        for(int i = 0 ; i<s.length(); i++)
        {
            if(s.substring(i,i+1).equals(")"))
            {
                if(stack.isEmpty())
                {
                    return false;
                }
                String c = stack.pop();
                if(!c.equals("("))
                {
                    return false;
                }
            }
            if(s.substring(i,i+1).equals("]"))
            {
                if(stack.isEmpty())
                {
                    return false;
                }
                String c = stack.pop();
                if(!c.equals("["))
                {
                    return false;
                }
            }
            if(s.substring(i,i+1).equals("}"))
            {
                if(stack.isEmpty())
                {
                    return false;
                }
                String c = stack.pop();
                if(!c.equals("{"))
                {
                    return false;
                }
            }
            else if(s.substring(i,i+1).equals("(")||s.substring(i,i+1).equals("[")||s.substring(i,i+1).equals("{"))
            {
                stack.push(s.substring(i,i+1));
            }
        }
        if(!stack.isEmpty())
        {
            return false;
        }
        return true;
    }
}
