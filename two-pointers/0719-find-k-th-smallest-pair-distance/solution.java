// 719. Find K-th Smallest Pair Distance
// https://leetcode.com/problems/find-k-th-smallest-pair-distance/
// Hard | Java | Accepted 2026-11-04
// Runtime 10 ms | Memory 46.3 MB

class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums); //Sort the array so we can easily check pairs in O(N) time throug hthe slidding window technique
        int l = 0; //The minimum distance pair is 0 if there are duplicated
        int r = nums[nums.length-1]-nums[0]; //The maximum is the last value in the array minus the first element in the array
        int ans = 0;
        while(l<=r) //Do binary search
        {
            int mid = l + (r-l)/2;
            if(getLessThan(nums, mid)>=k) //Count how many pairs in the array are less than or equal to our current mid distance guess
            //If this value is >= k, we set our ans to mid as a possibility, then decrement the right pointer to try to find a smaller mid value 
            {
                ans = mid;
                r = mid-1;
            }
            else
            {
                l = mid+1; //Else increase the left bound
            }
        }
        return ans;
    }

    public int getLessThan(int[] nums, int targ)
    {
        int count = 0; //Same logic as most Kth smallest problems
        int left = 0; //Left pointer 
        for(int i = 1; i<nums.length; i++) //Right pointer iterating from i = 1 to i = nums.length-1
        {
            int diff = nums[i]-nums[left]; //We are basically counting all valid pairs ending at the right pointer (i)
            //The maximum difference is going to be nums[i] - nums[left]
            //If nums[i] - nums[left] is valid, then every pair between them is also valid  
            while(nums[i]-nums[left]>targ) //If the distance is greater than the distance we want, increase the left pointer to make the window smaller (decrease the max distance of the window)
            {
                left++;
            }
            count+=(i-left); //Now that the window is valid, we count every pair ending at i, which is going to be every value from left to i-1 paired with i, meaning there are i - left pairs ending at i
        }
        return count; //Return the total count of pairs that are <= to the target distance
    }
}
