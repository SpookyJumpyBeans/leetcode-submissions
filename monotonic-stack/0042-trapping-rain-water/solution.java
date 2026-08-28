// 42. Trapping Rain Water
// https://leetcode.com/problems/trapping-rain-water/
// Hard | Java | Accepted 2025-07-20
// Runtime 0 ms | Memory 46.6 MB

class Solution {
    public int trap(int[] height) {
        int left = 0;
        int right = height.length-1;
        int maxL = 0;
        int maxR = 0;
        int count = 0;
        while(left<right)
        {
            if(height[left]<height[right])
            {
                if(height[left]>=maxL)
                {
                    maxL = height[left];
                }
                else
                {
                    count+=maxL-height[left];
                }
                left++;
            }
            else
            {
                if(height[right]>=maxR)
                {
                    maxR = height[right];
                }
                else

                {
                    count+=maxR-height[right];
                }
                right--;
            }
        }
        return count;
    }
}
