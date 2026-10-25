// 1822. Sign of the Product of an Array
// https://leetcode.com/problems/sign-of-the-product-of-an-array/
// Easy | Java | Accepted 2026-10-23
// Runtime 1 ms | Memory 44.7 MB

class Solution {
    public int arraySign(int[] nums) {
        int countNeg = 0;
        boolean zero = false;
        for(int i : nums)
        {
            if(i==0)
            {
                return 0;
            }
            countNeg += i<0 ? 1 : 0;
        }
        return countNeg%2==0 ? 1 : -1;
    }
}
