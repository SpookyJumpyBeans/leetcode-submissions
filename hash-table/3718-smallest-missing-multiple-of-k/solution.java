// 3718. Smallest Missing Multiple of K
// https://leetcode.com/problems/smallest-missing-multiple-of-k/
// Easy | Java | Accepted 2026-08-25
// Runtime 2 ms | Memory 45.8 MB

class Solution {
    public int missingMultiple(int[] nums, int k) {
        Set<Integer> temp = new HashSet<>();
        for(int i : nums)
        {
            temp.add(i);
        }
        int t = k;
        while(temp.contains(k))
        {
            k+=t;
        }
        return k;
    }
}
