// 1190. Reverse Substrings Between Each Pair of Parentheses
// https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/
// Medium | Java | Accepted 2026-10-04
// Runtime 4 ms | Memory 45.2 MB

class Solution {
    public String reverseParentheses(String s) {
        Stack<String> stack = new Stack<>();
        //Stack problem
        //3 cases:
        //We see an open bracket
        //We see a closed bracket
        //We see a letter
        for(int i = 0; i<s.length(); i++)
        {
            if(s.charAt(i)=='(')  
            //Every time we see an open parentheses, find all the characters that directly come after it that are letters
            //Add them to the stack
            {
                int next = i;
                while(next+1<s.length() && Character.isLetter(s.charAt(next+1)))
                {
                    next++;
                }
                stack.push(s.substring(i+1, next+1));
                i = next;
                //Set the i to the index of the last letter
            }
            else if(s.charAt(i)==')') 
            //Every time we see a closed parentheses, that means we need to reverse the last string we pushed onto the stack
            {
                String reversed = new StringBuilder(stack.pop()).reverse().toString(); //Reverse the string at the top of the stack
                //If this parentheses was nested in other parentheses, to make the reversal work, we need to append this string to the next string at the top of the stack (which is the string directly outside the current parentheses pair)
                if(!stack.isEmpty())
                {
                    String newTop = new StringBuilder(stack.pop()).append(reversed).toString(); //Append this string to the next string at the top of the stack
                    stack.push(newTop);
                }
                else
                {
                    stack.push(reversed); //If this string isn't nested, then just push the reversed string to the stack
                }
            }
            else if(Character.isLetter(s.charAt(i))) //Else if we reach letters that are on the other side of closed parentheses, we just want to do the same thing as if we saw a closed parentheses but don't reverse the string
            {
                int next = i;
                while(next+1<s.length() && Character.isLetter(s.charAt(next+1)))
                {
                    next++;
                }
                if(!stack.isEmpty())
                {
                    String append = new StringBuilder(stack.pop()).append(s.substring(i, next+1)).toString();
                    stack.push(append); //Append these stray characters to the string that's at the top of the stack if this is nested parentheses
                }
                else
                {
                    stack.push(s.substring(i, next+1)); //Else, just push this string to the stack
                }
                i = next; //Set the i to the index of the last letter
            }
        }
        return stack.pop(); //Should only have one complete reversed string on the stack at the end
        //Pop this string
    }
}
