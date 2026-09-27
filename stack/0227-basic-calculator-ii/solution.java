// 227. Basic Calculator II
// https://leetcode.com/problems/basic-calculator-ii/
// Medium | Java | Accepted 2026-09-26
// Runtime 26 ms | Memory 48.1 MB

import java.util.*;

class Solution {
    public int calculate(String s) {
        // Stack to store intermediate results. 
        // Multiplication and division are evaluated immediately, 
        // while addition and subtraction are deferred until the end.
        Stack<Integer> st = new Stack<>();

        int num = 0;
        
        // Tracks the operator that came BEFORE the current number we are building.
        // Initialized to '+' so the very first number is pushed as a positive integer.
        char sign = '+';

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // 1. Build the number digit by digit
            if (Character.isDigit(ch)) {
                // Shift the current number left by 1 base-10 place and add the new digit
                num = num * 10 + (ch - '0');
            }

            // 2. Process the number if we hit a new operator OR the end of the string
            // We ignore spaces, so this block only triggers on +, -, *, /, or the last index.
            if ((!Character.isDigit(ch) && ch != ' ') || i == s.length() - 1) {
                
                // We apply the PREVIOUS sign to the CURRENT num we just finished building
                if (sign == '+') {
                    st.push(num);
                }
                else if (sign == '-') {
                    // Treat subtraction as adding a negative number
                    st.push(-num);
                }
                else if (sign == '*') {
                    // Precedence: Evaluate multiplication immediately using the previous number on the stack
                    st.push(st.pop() * num);
                }
                else if (sign == '/') {
                    // Precedence: Evaluate division immediately using the previous number on the stack
                    st.push(st.pop() / num);
                }

                // Update the state for the NEXT number in the expression
                sign = ch;
                num = 0;
            }
        }

        int ans = 0;

        // 3. Sum up all the remaining values in the stack
        // Subtractions were pushed as negatives, and */ were already resolved, 
        // so a simple sum gives the final result.
        while (!st.isEmpty()) {
            ans += st.pop();
        }

        return ans;
    }
}
