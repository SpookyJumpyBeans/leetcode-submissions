// 2502. Design Memory Allocator
// https://leetcode.com/problems/design-memory-allocator/
// Medium | Java | Accepted 2026-09-10
// Runtime 13 ms | Memory 47.4 MB

/*

BRUTE FORCE APPROACH: O(N) for smaller bounds

class Allocator {
    int[] arr;
    public Allocator(int n) {
        arr = new int[n]; 
    }
    
    public int allocate(int size, int mID) {
       int ind = 0; 
       while(ind<arr.length)  
       {
        if(arr[ind]==0) //If the spot is currently free, then we check if the continguous amount of free spots starting at this index is equal to size
        {
            int end = ind;
            while(end+1<arr.length && arr[end+1]==0 && (end-ind+1)<size) //While there's another free spot after and we haven't reached size yet, keep iterating
            {
                end++;
            }
            if(end-ind+1==size) //If we've reached the exact size for the free space needed
            {
                for(int i = ind; i<=end; i++) //Set every free space equal to the mID
                {
                    arr[i] = mID;
                }
                return ind;
            }
            ind = end+1; //If the contiguous amount of free spots isn't equal to size, then make the index equal to end+1, which is the next spot that isn't free
        }
        else
        {
            ind++; //Else, just increment the index
        }
       }
       return -1; //Return -1 if nothing was found
    }
    
    public int freeMemory(int mID) {
        int count = 0;
        for(int i = 0; i<arr.length; i++) //Just iterate through the array and set everything that's equal to the mID to 0
        {
            if(arr[i]==mID)
            {
                arr[i] = 0;
                count++;
            }
        }
        return count;
    }
}
*/
class Allocator {
    //O(Blog(K)) solution
    //B is the number of blocks the mID holds
    //K is the number of free intervals
    Map<Integer, List<int[]>> intervals = new HashMap<>(); //We attach all the occupied intervals to the mID that occupies them
    TreeMap<Integer, Integer> freeMemory = new TreeMap<>(); //We have a treemap that stores the intervals that are currently free and able to be used
    public Allocator(int n) {
        freeMemory.put(0, n); //Start by putting the entire interval in free since the entire array is available at the start
    }
    
    public int allocate(int size, int mID) {
        for(Integer start : freeMemory.keySet()) //Go through all the intervals in the treemap
        {
            int end = freeMemory.get(start);
            if(end-start>=size) //If the interval is large enough
            {
                intervals.computeIfAbsent(mID, k -> new ArrayList<>()).add(new int[]{start, start+size}); //We want to add this interval to the intervals list associated with the mID
                freeMemory.remove(start); //Remove this interval from the free memory
                if(start+size<end) //If the size is less than the entire free interval length, then we put the remainder of the free interval back onto the free memory
                { 
                    freeMemory.put(start+size, end);
                }
                return start;
            }
        }
       return -1; 
    }
    
    public int freeMemory(int mID) {
        if(intervals.get(mID)==null)
        {
            return 0;
        }
        int count = 0;
        List<int[]> temp = intervals.get(mID); //Go through all the occupied intervals associated with the mID
        //We basically want to add each interval back to the free memory one at a time
        //Merge the interval with other free intervals when we add it
        for(int i = 0; i<temp.size(); i++)
        {
            int[] interval = temp.get(i);
            count+=interval[1]-interval[0]; //Add the size of the interval we're about to free 
            int s = interval[0]; //This is the default start and end for this interval we're going to free and add back to the free memory treemap
            int e = interval[1];
            Integer leftKey = freeMemory.floorKey(s);
            if(leftKey!=null && freeMemory.get(leftKey)==s) //If there is an interval to the left of this interval we're trying to add back that has an end that equals the newly freed interval's start, we can merge the two intervals together
            { 
                s = leftKey; //Set the start to the start value of this left interval
                freeMemory.remove(leftKey); //Remove this left interval from the tree map since we just merged it
            }
            Integer rightKey = freeMemory.ceilingKey(e);
            if(rightKey!=null && rightKey==e) //Do the same thing with the right interval if it exists
            {
                e = freeMemory.get(rightKey); //Set the new end to the end of the right interval
                freeMemory.remove(rightKey); //Remove the right interval if we merged it
            }
            freeMemory.put(s, e); //Then, put this newly merged interval (if we even merged any intervals at all) onto the free memory
       }
       intervals.remove(mID); //Remove the mID from the map
       return count;
    }
}


/**
 * Your Allocator object will be instantiated and called as such:
 * Allocator obj = new Allocator(n);
 * int param_1 = obj.allocate(size,mID);
 * int param_2 = obj.freeMemory(mID);
 */
