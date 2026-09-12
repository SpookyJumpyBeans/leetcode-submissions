// 1381. Design a Stack With Increment Operation
// https://leetcode.com/problems/design-a-stack-with-increment-operation/
// Medium | Java | Accepted 2026-09-11
// Runtime 5 ms | Memory 46.7 MB

class CustomStack {
    int[] stack;
    int maxSize;
    int top = -1;
    //Represent the stack as an array of maxSize
    //Use a top pointer and just increment the pointer forward/backward to simulate lazy stack pushing/popping
    public CustomStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }
    
    public void push(int x) {
        if(top+1<maxSize) //If the top pointer isn't out of bounds
        {
            stack[++top] = x;  //First increment the top pointer then use it
        }
    }
    
    public int pop() {
        return top==-1 ? -1 : stack[top--]; //Check if the top pointer isn't out of bounds (empty stack) and then return the value at top and then decrement the pointer
    }
    
    public void increment(int k, int val) {
        int limit = Math.min(k, maxSize); //We want to start at the lowest value in the stack (starting at 0) and increment all the values from index 0 to the smaller of either k or the size of the entire stack
        for(int i = 0; i<limit; i++)
        {
            stack[i]+=val;
        }
    }
}

/**
 * Your CustomStack object will be instantiated and called as such:
 * CustomStack obj = new CustomStack(maxSize);
 * obj.push(x);
 * int param_2 = obj.pop();
 * obj.increment(k,val);
 */
