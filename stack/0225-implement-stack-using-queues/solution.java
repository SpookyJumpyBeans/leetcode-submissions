// 225. Implement Stack using Queues
// https://leetcode.com/problems/implement-stack-using-queues/
// Easy | Java | Accepted 2026-09-01
// Runtime 2 ms | Memory 43 MB

class MyStack {
    Queue<Integer> q1 = new LinkedList<>();
    Queue<Integer> q2 = new LinkedList<>();
    public MyStack() {
        
    }
    
    public void push(int x) {
        q1.add(x);
        while(!q2.isEmpty())
        {
            q1.add(q2.poll());
        }
        Queue<Integer> temp = q2;
        q2 = q1;
        q1 = temp;
    }
    
    public int pop() {
        return q2.poll();
    }
    
    public int top() {
        return q2.peek();
    }
    
    public boolean empty() {
        return q1.isEmpty() && q2.isEmpty();
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
