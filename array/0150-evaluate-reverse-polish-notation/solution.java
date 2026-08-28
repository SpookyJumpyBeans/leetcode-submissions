// 150. Evaluate Reverse Polish Notation
// https://leetcode.com/problems/evaluate-reverse-polish-notation/
// Medium | Java | Accepted 2025-10-26
// Runtime 6 ms | Memory 45 MB

class Solution {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i<tokens.length; i++)
        {
            if(tokens[i].equals("+"))
            {
                int int1 = stack.pop();
                int int2 =  (stack.pop());
                int2+=int1;
                stack.push(int2);
            }
            else if(tokens[i].equals("-"))
            {
                int int1 =  (stack.pop());
                int int2 =  (stack.pop());
                int2-=int1;
                stack.push(int2);
            }
            else if(tokens[i].equals("*"))
            {
                int int1 =  (stack.pop());
                int int2 =  (stack.pop());
                int2*=int1;
                stack.push(int2);
            }
            else if(tokens[i].equals("/"))
            {
                int int1 =  (stack.pop());
                int int2 =  (stack.pop());
                int2/=int1;
                stack.push(int2);
            }
            else
            {
                stack.push(Integer.parseInt(tokens[i]));
            }
        }
        return stack.pop();
    }
}
