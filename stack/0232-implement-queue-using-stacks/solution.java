// 232. Implement Queue using Stacks
// https://leetcode.com/problems/implement-queue-using-stacks/
// Easy | Java | Accepted 2026-09-01
// Runtime 1 ms | Memory 42.5 MB

class MyQueue {
    Stack<Integer> first = new Stack<>();
    Stack<Integer> sec = new Stack<>();
    public MyQueue() {
        
    }
    
    public void push(int x) { //first = [2, 3, 4, 5
        while(!sec.isEmpty())
        {
            first.push(sec.pop());
        }
        first.push(x);
    }
    
    public int pop() {
        while(!first.isEmpty())
        {
            sec.push(first.pop()); //4 3 2 
        }
        return sec.pop();
    }
    
    public int peek() {
        while(!first.isEmpty())
        {
            sec.push(first.pop());
        }
        return sec.peek();
    }
    
    public boolean empty() {
        return (first.isEmpty() && sec.isEmpty());
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
