// 346. Moving Average from Data Stream
// https://leetcode.com/problems/moving-average-from-data-stream/
// Easy | Java | Accepted 2026-10-19
// Runtime 61 ms | Memory 51.9 MB

class MovingAverage {
    Queue<Integer> temp = new ArrayDeque<>();
    int max;
    int sum = 0;
    public MovingAverage(int size) {
        max = size;
    }
    
    public double next(int val) {
        if(temp.size()==max)
        {
            sum-=temp.poll();
        }
        sum+=val;
        temp.add(val);
        return (double)sum/temp.size();
    }
}

/**
 * Your MovingAverage object will be instantiated and called as such:
 * MovingAverage obj = new MovingAverage(size);
 * double param_1 = obj.next(val);
 */
