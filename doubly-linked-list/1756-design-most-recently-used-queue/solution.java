// 1756. Design Most Recently Used Queue
// https://leetcode.com/problems/design-most-recently-used-queue/
// Medium | Java | Accepted 2026-11-08
// Runtime 16 ms | Memory 46.3 MB

class MRUQueue {
    ArrayList<Integer> mru = new ArrayList<>();
    public MRUQueue(int n) {
        for(int i = 1; i<=n; i++)
        {
            mru.add(i-1, i);
        }
    }
    
    public int fetch(int k) {
        int temp = mru.remove(k-1);
        mru.add(temp);
        return temp;
    }
}

/**
 * Your MRUQueue object will be instantiated and called as such:
 * MRUQueue obj = new MRUQueue(n);
 * int param_1 = obj.fetch(k);
 */
