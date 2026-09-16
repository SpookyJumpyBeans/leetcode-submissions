// 84. Largest Rectangle in Histogram
// https://leetcode.com/problems/largest-rectangle-in-histogram/
// Hard | Java | Accepted 2026-09-15
// Runtime 64 ms | Memory 78.4 MB

class Solution {
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>(); //Strictly increasing monotonic stack
        int max = Integer.MIN_VALUE; //Max value calculated
        for(int i = 0; i<heights.length; i++)
        {
                while(!stack.isEmpty() && heights[i] < heights[stack.peek()]) //Basically, the intuition is that the second we hit an element that is smaller than the top of the stack, this index will be the right boundary for whatever taller bars there are on the stack, since the taller bars can't extend their rectangle into this index
                {
                    int leftBoundary = stack.pop(); //Pop the index of this taller bar
                    if(stack.isEmpty()) //If the stack is empty, then this means that this taller bar goes from the start of the array to this right index (it has no left boundary)
                    {
                        max = Math.max(max, i*heights[leftBoundary]); //Area would be the width (index of this right boundary) * the height of this bar
                    }
                    else
                    {
                        max = Math.max(max, (i-stack.peek()-1)*heights[leftBoundary]); //Else, the width of this bar would be the right boundary subtracted by the index of the left boundary (the next bar on the stack since this bar is smaller than the currently popped bar). This left boundary means that from the index of this next smaller bar to the index of the bigger bar is all part of the bigger bar's rectangle. Multiply this by the height of the bigger bar.
                    }
                }
                stack.push(i); //Push this index to the stack
        }
        while(!stack.isEmpty()) //Do one final run through for all the elements that remain on the stack
        {
            int ind = stack.pop();
            if(stack.isEmpty())
            {
                max = Math.max(max, heights.length*heights[ind]); //Same logic except the right boundary is the length of the array
            }
            else
            {
                max = Math.max(max, (heights.length-stack.peek()-1)*heights[ind]);
            }
        }
        return max;
        }
    }

    //1 2 3
