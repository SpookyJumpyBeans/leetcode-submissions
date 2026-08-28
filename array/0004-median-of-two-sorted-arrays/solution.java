// 4. Median of Two Sorted Arrays
// https://leetcode.com/problems/median-of-two-sorted-arrays/
// Hard | Java | Accepted 2025-11-04
// Runtime 21 ms | Memory 48.9 MB

class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] arr = new int[nums1.length + nums2.length];
        for(int i = 0; i<nums1.length; i++)
        {
            arr[i] = nums1[i];
        }
        for(int j = 0; j<nums2.length; j++)
        {
            arr[j+nums1.length] = nums2[j];
        }
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        if(arr.length%2!=0)
        {
            return arr[arr.length/2];
        }
        return ((double)arr[arr.length/2] + arr[arr.length/2-1])/2;
    }
}
