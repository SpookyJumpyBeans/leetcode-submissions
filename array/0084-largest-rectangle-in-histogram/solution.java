// 84. Largest Rectangle in Histogram
// https://leetcode.com/problems/largest-rectangle-in-histogram/
// Hard | Java | Accepted 2025-11-05
// Runtime 1180 ms | Memory 92 MB

class Solution {
    public int largestRectangleArea(int[] heights) {
        ArrayList<Integer> nse = new ArrayList<>();
        ArrayList<Integer> lse = new ArrayList<>();
         for (int i = 0; i < heights.length; i++) 
         {
            nse.add(heights.length);
            lse.add(-1);
         }
         Stack<Integer> stack = new Stack<>();
         for(int j = heights.length-1; j>=0; j--)
        {
            while(!stack.isEmpty() && heights[stack.peek()]>=heights[j])
            {
                stack.pop();
            }
            if(!stack.isEmpty())
            {
                nse.set(j, stack.peek());
            }
            stack.push(j);
        }
        stack.clear();
        for(int k = 0; k<heights.length; k++)
        {
            while(!stack.isEmpty() && heights[stack.peek()]>=heights[k])
            {
                stack.pop();
            }
            if(!stack.isEmpty())
            {
                lse.set(k, stack.peek());
            }
            stack.push(k);
        }
        int max = 0;
        System.out.println(nse);
        System.out.println(lse);
        for(int l = 0; l<heights.length; l++)
        {
            int start = lse.get(l);
            int end = nse.get(l);
            System.out.println((end-start-1));
            max = Math.max(max, (end-start-1)*heights[l]);
        }
        return max;
        }
    }
