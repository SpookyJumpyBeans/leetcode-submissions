// 88. Merge Sorted Array
// https://leetcode.com/problems/merge-sorted-array/
// Easy | Java | Accepted 2026-09-01
// Runtime 0 ms | Memory 43.8 MB

class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] merged = new int[m+n];
        int p1 = 0;
        int p2 = 0;
        int ind = 0;
        while(p1<m && p2<n)
        {
            if(nums1[p1]<nums2[p2])
            {
                merged[ind] = nums1[p1];
                p1++;
                ind++;
            }
            else
            {
                merged[ind] = nums2[p2];
                p2++;
                ind++;
            }
        }
        while(p1<m)
        {
            merged[ind] = nums1[p1];
            ind++;
            p1++;
        }
        while(p2<n)
        {
            merged[ind] = nums2[p2];
            ind++;
            p2++;
        }
        for(int i = 0; i<merged.length; i++)
        {
            nums1[i] = merged[i];
        }
    }
}


//1 2 5 0 0 0 

//6 2 3
