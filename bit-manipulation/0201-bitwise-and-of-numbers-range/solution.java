// 201. Bitwise AND of Numbers Range
// https://leetcode.com/problems/bitwise-and-of-numbers-range/
// Medium | Java | Accepted 2026-08-29
// Runtime 4 ms | Memory 45.6 MB

class Solution {
    public int rangeBitwiseAnd(int left, int right) {
        int start = 31;
        int ans = left;
        while(start>=0)
        {
            if(left>>start == right>>start)
            {
                start--;
            }
            else
            {
                break;
            }
        }   
            ans>>=start+1;
            ans<<=start+1;
            return ans;
    }
}
