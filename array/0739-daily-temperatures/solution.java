// 739. Daily Temperatures
// https://leetcode.com/problems/daily-temperatures/
// Medium | Java | Accepted 2025-10-28
// Runtime 82 ms | Memory 60.7 MB

import java.util.*;

class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] nge = new int[n]; // stores distance to next warmer day
        Stack<Integer> st = new Stack<>(); // stores indices of days
        
        // Move from right to left
        for (int i = n - 1; i >= 0; i--) {
            // Pop all indices where the temperature is less than or equal to current day's temperature
            while (!st.isEmpty() && temperatures[st.peek()] <= temperatures[i]) {
                st.pop();
            }

            // If stack not empty, top of stack is next greater (warmer) temperature
            if (!st.isEmpty()) {
                nge[i] = st.peek() - i; // distance to next warmer day
            }

            // Push current day's index onto stack
            st.push(i);
        }

        return nge;
    }
}
