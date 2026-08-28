// 338. Counting Bits
// https://leetcode.com/problems/counting-bits/
// Easy | Java | Accepted 2026-01-07
// Runtime 2 ms | Memory 48.8 MB

class Solution {
    public int[] countBits(int n) {
        int[] ans = new int[n+1];
        for(int i = 0; i<=n; i++)
        {
            int temp = i;
            int count = 0;
            while(temp!=0)
            {
                temp = temp & (temp-1);
                count++;
            }
            ans[i] = count;
        }
        return ans;
    }
}
