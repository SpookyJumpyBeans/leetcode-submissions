// 1614. Maximum Nesting Depth of the Parentheses
// https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses/
// Easy | Java | Accepted 2026-09-28
// Runtime 1 ms | Memory 42.4 MB

class Solution {
    public int maxDepth(String s) {
        Stack<Character> stack = new Stack<>();
        int max = 0;
        int depth = 0;
        for(int i = 0; i<s.length(); i++)
        {
            if(s.charAt(i)=='(')
            {
                stack.push('(');
                depth++;
                max = Math.max(max, depth);
            }
            else if(s.charAt(i)==')')
            {
                stack.pop();
                depth--;
            }   
        }
        return max;
    }
}
