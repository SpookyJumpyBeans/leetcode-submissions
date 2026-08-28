// 933. Number of Recent Calls
// https://leetcode.com/problems/number-of-recent-calls/
// Easy | Java | Accepted 2026-08-10
// Runtime 22 ms | Memory 59.6 MB

class RecentCounter {
    Queue<Integer> queue;
    int count = 0;
    public RecentCounter() {
        queue = new LinkedList<>();
    }
    
    public int ping(int t) {
        queue.add(t);
        count++;
        while(queue.peek()<t-3000)
        {
            queue.poll();
            count--;
        }
        return count;
    }
}

/**
 * Your RecentCounter object will be instantiated and called as such:
 * RecentCounter obj = new RecentCounter();
 * int param_1 = obj.ping(t);
 */
