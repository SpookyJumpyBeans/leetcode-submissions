// 11. Container With Most Water
// https://leetcode.com/problems/container-with-most-water/
// Medium | Java | Accepted 2024-11-14
// Runtime 916 ms | Memory 56.4 MB

class Solution {
    public int maxArea(int[] height) {
        int pointer1 = 0;
        int pointer2 = height.length-1;
        double max = 0;
        while(pointer1<pointer2)
        {
            double prod = Math.min(height[pointer1], height[pointer2])*Math.abs(pointer1-pointer2);
            System.out.println(height[pointer1] + " " + height[pointer2]);
            if(prod>max)
            {
                max = prod;
            }
            if(height[pointer1]<height[pointer2])
            {
                pointer1++;
            }
            else
            {
                pointer2--;
            }
        }
        return (int) max;
    }
}
