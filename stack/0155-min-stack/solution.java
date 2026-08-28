// 155. Min Stack
// https://leetcode.com/problems/min-stack/
// Medium | Java | Accepted 2025-10-26
// Runtime 5 ms | Memory 44.8 MB

class MinStack {
    private Stack<Integer> stack;
    private ArrayList<Integer> min;
    private int minimum = 2147483647;
    public MinStack() {
        stack = new Stack<>();
        min = new ArrayList<>();
        min.add(minimum);
    }
    
    public void push(int val) {
        if(val<=min.get(min.size()-1))
        {
            minimum = val;
            min.add(minimum);
        }
        stack.push(val);
    }
    
    public void pop() {
        int s = stack.pop();
        if(s==min.get(min.size()-1))
        {
            System.out.println(min);
            min.remove(min.size()-1);
            minimum = Collections.min(min);
        }
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
       return minimum;
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
