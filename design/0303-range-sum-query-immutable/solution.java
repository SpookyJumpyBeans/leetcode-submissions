// 303. Range Sum Query - Immutable
// https://leetcode.com/problems/range-sum-query-immutable/
// Easy | Java | Accepted 2026-09-06
// Runtime 7 ms | Memory 47.9 MB

class NumArray {
    int[] prefSum;
    public NumArray(int[] nums) {
        //Just build a prefix sum
        prefSum = new int[nums.length+1];
        for(int i = 1; i<prefSum.length; i++)
        {
            prefSum[i] = prefSum[i-1] + nums[i-1]; //Add the previous prefix sum value to the current nums value
        }
    }
    
    public int sumRange(int left, int right) {
        return prefSum[right+1]-prefSum[left]; //Pref[Right+1] - Pref[Left] = the sum of values in [left, right]
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(left,right);
 */
