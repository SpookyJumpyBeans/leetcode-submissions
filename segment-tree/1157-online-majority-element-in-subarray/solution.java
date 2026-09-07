// 1157. Online Majority Element In Subarray
// https://leetcode.com/problems/online-majority-element-in-subarray/
// Hard | Java | Accepted 2026-09-07
// Runtime 121 ms | Memory 88 MB

class MajorityChecker {
    Map<Integer, List<Integer>> map = new HashMap<>();
    int[] a;
    Random rand = new Random();
    public MajorityChecker(int[] arr) {
        //Original method was doing the Boyer-Moore Majority Voting Algorithm but a more interesting solution, use randomization + binary search
        //Since the majority element is going to be >= 50% of the array, if we randomly pick an index from the valid range [left, right], then we have a minimum 50% chance of picking the majority element. 
        //We pick randomly 20 times for each range query
        //We also store every index of every value in a map
        //The map will hold the value as well as all occurrences of the value in the array
        a = arr;
        for(int i = 0; i<arr.length; i++)
        {
            map.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }
    }
    
    public int query(int left, int right, int threshold) {
       for(int i = 0; i<20; i++)
       { 
        int randIndex = left + rand.nextInt(right-left+1); //Randomly pick an index 
        int candidate = a[randIndex]; //Get the value at that index
        List<Integer> list = map.get(candidate); //Get all occurences of this value 
        if(list.size()<threshold) //If all occurrences of this value is already smaller than the threshold, pick another index
        {
            continue;
        }
        int lowerBound = bsL(left, list); //Else, find the first index in all of this value's list of occurences >= the left boundary
        int upperBound = bsU(right, list); //Find the last index in all of this value's list of occurences <= the right boundary
        if(upperBound-lowerBound+1 >= threshold) //Subtracting the two bounds and adding 1 wil give us how many occurrences of the candidate is within the left and right boundaries
        //The second this value is >= the threshold, return the candidate
        {
            return candidate;
        }
       }
       return -1;
    }

    public int bsL(int targ, List<Integer> list) //Find the first value that's >= the left boundary
    {
        int l = 0;
        int r = list.size()-1;
        int ans = 0;
        while(l<=r)
        {
            int mid = l + (r-l)/2;
            if(list.get(mid)>=targ)
            {
                r = mid-1;
                ans = mid;
            }
            else
            {
                l = mid+1;
            }
        }
        return ans;
    }

    public int bsU(int targ, List<Integer> list) //Find the last value that's <= the right boundary
    {
        int l = 0;
        int r = list.size()-1;
        int ans = 0;
        while(l<=r)
        {
            int mid = l + (r-l)/2;
            if(list.get(mid)<=targ)
            {
                l = mid+1;
                ans = mid;
            }
            else
            {
                r = mid-1;
            }
        }
        return ans;
    }
}

/**
 * Your MajorityChecker object will be instantiated and called as such:
 * MajorityChecker obj = new MajorityChecker(arr);
 * int param_1 = obj.query(left,right,threshold);
 */
