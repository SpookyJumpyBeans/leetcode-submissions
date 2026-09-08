// 2336. Smallest Number in Infinite Set
// https://leetcode.com/problems/smallest-number-in-infinite-set/
// Medium | Java | Accepted 2026-09-07
// Runtime 10 ms | Memory 47.1 MB

class SmallestInfiniteSet {
    PriorityQueue<Integer> sfs;
    Set<Integer> addedBack;
    int next;

    public SmallestInfiniteSet() {
        sfs = new PriorityQueue<>(); //This is solely for numbers that are added back 
        addedBack = new HashSet<>(); //This is to track if the number has already been added back
        next = 1; //This is to keep track of the smallest number and also the boundary of the continuous sequence of numbers that haven't been touched
    }
    
    public int popSmallest() {
        if(!sfs.isEmpty()){ //If the added back numbers pq isn't empty, then poll from the pq and remove the number from the set
            int num = sfs.poll();
            addedBack.remove(num);
            return num;
        }
        //If the added back numbers pq is empty, that means all the numbers before next have been popped
        //The next smallest number is going to be next itself
        return next++;
    }
    
    public void addBack(int num) {
        if(num < next && addedBack.add(num)){ //We only add back if the number is less than next, since next is the boundary for untouched numbers
        //Also if the added back number doesn't already exist in the set
            sfs.offer(num); //Add the number to the pq to sort it
        }
    }
}

/**
 * Your SmallestInfiniteSet object will be instantiated and called as such:
 * SmallestInfiniteSet obj = new SmallestInfiniteSet();
 * int param_1 = obj.popSmallest();
 * obj.addBack(num);
 */
