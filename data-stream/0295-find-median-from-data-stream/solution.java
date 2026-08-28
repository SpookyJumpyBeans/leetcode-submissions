// 295. Find Median from Data Stream
// https://leetcode.com/problems/find-median-from-data-stream/
// Hard | Java | Accepted 2025-12-19
// Runtime 124 ms | Memory 111.6 MB

class MedianFinder {
    PriorityQueue<Integer> smaller;
    PriorityQueue<Integer> larger;

    public MedianFinder() {
        smaller = new PriorityQueue<>(Collections.reverseOrder());
        larger = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        if(smaller.peek()!=null && num<=smaller.peek())
        {
            smaller.add(num);
              if(smaller.size()>larger.size()+1)
        {
            larger.add(smaller.poll());
        }
        }   
        else
        {
            larger.add(num);
             if(smaller.size()<larger.size())
        {
            smaller.add(larger.poll());
        }
        }
    }
    
    public double findMedian() {
        if(smaller.size()>larger.size())
        {
            return (double) smaller.peek();
        }
        else{
            return (smaller.peek() + larger.peek())/(double)2;
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
