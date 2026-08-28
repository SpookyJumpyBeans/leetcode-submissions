// 901. Online Stock Span
// https://leetcode.com/problems/online-stock-span/
// Medium | Java | Accepted 2026-08-18
// Runtime 32 ms | Memory 55.3 MB

class StockSpanner {
    Stack<int[]> span = new Stack<>();
    public StockSpanner() {
        
    }
    
    public int next(int price) {
        int count = 1;
        while(!span.isEmpty() && span.peek()[0]<=price)
        {
            count+=span.pop()[1];
        }
        span.push(new int[]{price, count});
        return count;
    }
}

/**
 * Your StockSpanner object will be instantiated and called as such:
 * StockSpanner obj = new StockSpanner();
 * int param_1 = obj.next(price);
 */
