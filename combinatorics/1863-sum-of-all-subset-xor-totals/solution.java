// 1863. Sum of All Subset XOR Totals
// https://leetcode.com/problems/sum-of-all-subset-xor-totals/
// Easy | Java | Accepted 2026-08-27
// Runtime 0 ms | Memory 42.8 MB

class Solution {
    int ans = 0;
    int[] n;
    public int subsetXORSum(int[] nums) {
        n = nums;
        recurse(0, 0);
        return ans;
    }

    public void recurse(int startInd, int currXOR)
    {
        ans+=currXOR;
        for(int i = startInd; i<n.length; i++)
        {
            int temp = n[i];
            currXOR^=n[i];
            recurse(i+1, currXOR);
            currXOR ^= temp;
        }
    }
}
//5 1 6    2 
//5 6      3 
//5 6 
