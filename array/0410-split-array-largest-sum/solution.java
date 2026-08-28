// 410. Split Array Largest Sum
// https://leetcode.com/problems/split-array-largest-sum/
// Hard | Java | Accepted 2026-08-18
// Runtime 0 ms | Memory 43 MB

class Solution {
    int[] num;
    int m;
    public int splitArray(int[] nums, int k) {
        m = k;
        num = nums;
        int max = 0;
        int sum = 0;
        for(int i : nums)
        {
            max = Math.max(max, i);
            sum+=i;
        }
        int l = max;
        int r = sum;
        int ans = 0;
        while(l<=r)
        {
            int mid = l + (r-l)/2;
            if(canSplit(mid))
            {
                ans = mid;
                r = mid-1;
            }
            else
            {
                l = mid+1;
            }
        }
        return ans;
    }
    public boolean canSplit(int mid)
    {
        int i = 0;
        int count = 0;
        int k = 1;
        while(i<num.length)
        {
            if(count+num[i]>mid)
            {
                count = 0;
                k++;
            }
            count+=num[i];
            i++;
        }
        return k<=m ? true : false;
    }
}
