// 362. Design Hit Counter
// https://leetcode.com/problems/design-hit-counter/
// Medium | Java | Accepted 2026-10-08
// Runtime 1 ms | Memory 42.8 MB

class HitCounter {
    Deque<int[]> stream = new ArrayDeque<>(); //Keeps track of both the timestamp and the frequency of hits to that timestamp
    int total = 0;
    //Use a deque to remove timestamps from the start (when the timestamps at the start is outdated)
    //And to add timestamps to the end (either their first time being added or to add 1 to a timestamp that's been hit multiple times)
    public HitCounter() {
        
    }
    
    public void hit(int timestamp) {
        if(stream.isEmpty() || stream.peekLast()[0]!=timestamp) //If the deque is empty or this is a new timestamp that's being hit (timestamps are in increasing order, so the new timestamp will always be >= the one at the end of the deque), add it to the end of the deque with a frequency of 1
        {
            stream.addLast(new int[]{timestamp, 1});
        }
        else
        {
            stream.peekLast()[1]++; //If this timestamp was one we recently hit, increment its counter by 1
        }
        total++; //Add 1 to the total count of hits since no matter what the total will go up by 1
    }
    
    public int getHits(int timestamp) {
        int end = timestamp-300; //Get the cutoff timestamp
        while(!stream.isEmpty() && stream.peekFirst()[0]<=end) //While the start timestamps are under the cutoff, remove them and also remove their frequency counts from the total
        {
           total-=stream.removeFirst()[1];
        }
        return total;
    }
}

/**
 * Your HitCounter object will be instantiated and called as such:
 * HitCounter obj = new HitCounter();
 * obj.hit(timestamp);
 * int param_2 = obj.getHits(timestamp);
 */
