// 295. Find Median from Data Stream
// https://leetcode.com/problems/find-median-from-data-stream/
// Hard | Java | Accepted 2026-09-11
// Runtime 136 ms | Memory 111.6 MB

class MedianFinder {
    PriorityQueue<Integer> larger = new PriorityQueue<>();
    PriorityQueue<Integer> smaller = new PriorityQueue<>(Collections.reverseOrder());
    public MedianFinder() {
        
    }
    //INTUITION
    //We want to keep two prioirty queues 
    //One is going to contain smaller numbers and one is going to contain larger numbers
    //They need to be as close in size as possible, with the boundary being smaller can have 1 more element thatn larger at all times
    //We also make smaller a max heap, so we can access the largest small number and the smallest large number at all times
    //Since we keep the queues as close in size as possible at all times, the median is simply the top of the queues divided by 2 (When the sizes are even) and just the top of the smaller queue (When the sizes are odd) because the smaller one is going to have 1 more element that the larger queue
    public void addNum(int num) {
      if(smaller.peek()!=null && num<=smaller.peek()) //IF the number is smaller than the largest small number, add it to the small heap
      {
        smaller.add(num); 
        if(smaller.size()>larger.size()+1) //If the smaller heap is now too big (2 ore more elements more than the larger heap) poll the largest small number and add it to the larger heap. The largest small number will now become the smallest large number
        {
            larger.add(smaller.poll());
        }
      }
      else
      { 
        larger.add(num); //Vice versa
        if(larger.size()>smaller.size())
        {
            smaller.add(larger.poll());
        }
      }
    }
    
    public double findMedian() { 
        if((larger.size()+smaller.size())%2==0) //If even, take both queues top elements and find average
        {
            return (smaller.peek() + larger.peek())/2.0;
        }
        return smaller.peek(); //If odd, just take the smaller queues top value
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
