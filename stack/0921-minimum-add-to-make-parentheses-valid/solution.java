// 921. Minimum Add to Make Parentheses Valid
// https://leetcode.com/problems/minimum-add-to-make-parentheses-valid/
// Medium | Java | Accepted 2026-10-06
// Runtime 2 ms | Memory 42.9 MB

class Solution {
    public int minAddToMakeValid(String s) {
       Stack<Character> stack = new Stack<>();
       //Use a stack and do the basic procedure for parentheses
       //If it's an open parentheses, push it to the stack, if it's a closed parentheses, pop the stack
       //There are two cases where we need parentheses, if there are open parentheses that don't have closed parentheses (the resulting size of the stack)
       //And if there are closed parentheses before open parentheses (If the stack if empty when we hit a closed parentheses)
       int count = 0; //Keeps track of how many closed parentheses don't have an open parentheses
       for(int i = 0; i<s.length(); i++)
       {
           char temp = s.charAt(i);
           if(temp=='(')
           {
            stack.push(temp);
           }
           else if(temp==')')
           {
                if(stack.isEmpty()) //Case 2
                {
                    count++;
                }
                else
                {
                    stack.pop();
                }
           }
       }
       return stack.size() + count; //Case 1 count + Case 2 count
    }
}
