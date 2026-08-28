// 845. Longest Mountain in Array
// https://leetcode.com/problems/longest-mountain-in-array/
// Medium | Java | Accepted 2026-08-12
// Runtime 3 ms | Memory 47.2 MB

class Solution {
    public int longestMountain(int[] arr) {
        if(arr.length<3)
        {
            return 0;
        }
        int ind = 1;
        int max = 0;
        while(ind+1<arr.length)
        {
            if(arr[ind-1]<arr[ind] && arr[ind+1]<arr[ind])
            {
                int left = ind-1;   
                int right = ind+1;
                while(left-1>=0&&arr[left-1]<arr[left])
                {
                    left--;
                }
                while(right+1<arr.length&&arr[right+1]<arr[right])
                {
                    right++;
                }
                max = Math.max(right-left+1, max);
            }
            ind++;
        }
        return max;
    }
}
