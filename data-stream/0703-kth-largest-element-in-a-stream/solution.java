// 703. Kth Largest Element in a Stream
// https://leetcode.com/problems/kth-largest-element-in-a-stream/
// Easy | Java | Accepted 2025-11-29
// Runtime 22 ms | Memory 52 MB

class KthLargest {
    private PriorityQueue<Integer> minHeap;
    private int k;
    public KthLargest(int k, int[] nums) {
        this.k = k;
        minHeap = new PriorityQueue<>();
        for(int i = 0; i<nums.length; i++)
        {
            add(nums[i]);
        }
    }
    
    public int add(int val) {
        if(minHeap.size()<k || val>minHeap.peek())
        {
            minHeap.add(val);
        }
        if(minHeap.size()>k)
        {
            minHeap.remove();
        }
        return minHeap.peek();
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */
