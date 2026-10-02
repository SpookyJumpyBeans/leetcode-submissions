// 2032. Two Out of Three
// https://leetcode.com/problems/two-out-of-three/
// Easy | Java | Accepted 2026-10-01
// Runtime 1 ms | Memory 46.8 MB

class Solution {
    public List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {
        int[] freq1  = new int[101];
        int[] freq2 = new int[101];
        int[] freq3 = new int[101];
        for(int i : nums1)
        {
            freq1[i] += freq1[i] == 0 ? 1 : 0;
        }
        for(int i : nums2)
        {
            freq2[i] += freq2[i] == 0 ? 1 : 0;
        }
        for(int i : nums3)
        {
            freq3[i] += freq3[i] == 0 ? 1 : 0;
        }
        List<Integer> ans = new ArrayList<>();
        for(int i = 0; i<freq1.length; i++)
        {
            if(freq1[i] + freq2[i] + freq3[i] > 1)
            {
                ans.add(i);
            }
        }
        return ans;
    }
}
