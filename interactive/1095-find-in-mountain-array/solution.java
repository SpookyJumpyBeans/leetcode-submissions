// 1095. Find in Mountain Array
// https://leetcode.com/problems/find-in-mountain-array/
// Hard | Java | Accepted 2026-09-04
// Runtime 0 ms | Memory 46.6 MB

/**
 * // This is mountainArr's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface mountainArr {
 *     public int get(int index) {}
 *     public int length() {}
 * }
 */
 
class Solution {
    public int findInMountainArray(int target, MountainArray mountainArr) {
        int len = mountainArr.length();
        int l = 0;
        int r = len-1;
        //To do this question, we can't use the same idea as search in rotated array, since in search in rotated array, you know exactly which half is continuously sorted, you can check if your target falls within its range. If it does, you discard the unsorted half. If it doesn't, you discard the sorted half.
        //In this problem, you can't cut the search space in half at every single step due to ambiguity
        //If you pick a midpoint without knowing where the peak is, you have no clue whether the target is on the increasing/decreasing side or both
        while(l<r) //First find the peak of the array
        {
            int mid = l + (r-l)/2;
            int val = mountainArr.get(mid); //We only need to look at the current mid and the value directly after the mid to determine whether we are on the increasing/decreasing side of the array
            int valA = mountainArr.get(mid+1);
             if(val<valA) //If this value is on the increasing side of the peak, make the left boundary mid+1
            {
                l = mid+1;
            }
            else if(val>valA) //Make the right boundary mid if the value is on the decreasing side of the peak
            {
                r = mid;
            }
        }
        //Now do binary search on both the increasing/decreasing sides of the peak
        l = 0;
        int peak = r; 
        //Start with the increasing side, because if we find the value on this side, return the index immediately since its index is smaller
        while(l<=r)
        {
            int mid = l + (r-l)/2;
            int val = mountainArr.get(mid);
            if(val==target)
            {
                return mid;
            }
            else if(target<val)
            {
                r = mid-1;
            }
            else if(target>val)
            {
                l = mid+1;
            }
        }
        l = peak;
        r = len-1;
        while(l<=r) //Binary search on decreasing side, just switch logic
        {
            int mid = l + (r-l)/2;
            int val = mountainArr.get(mid);
            if(val==target)
            {
                return mid;
            }
            else if(target<val)
            {
                l = mid+1;
            }
            else if(target>val)
            {
                r = mid-1;
            }
        }
        return -1; //Return -1 if we don't find anything after both searches
    }
}
