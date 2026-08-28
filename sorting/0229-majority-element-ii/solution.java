// 229. Majority Element II
// https://leetcode.com/problems/majority-element-ii/
// Medium | Java | Accepted 2026-08-18
// Runtime 13 ms | Memory 51.1 MB

class Solution {
    public List<Integer> majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i : nums)
        {
           map.put(i, map.getOrDefault(i, 0)+1);
        }
        int thresh = nums.length/3;
        List<Integer> ans = new ArrayList<>();
        for(Map.Entry<Integer, Integer> e : map.entrySet())
        {
            if(e.getValue()>thresh)
            {
            ans.add(e.getKey());
            }
        }
        return ans;
    }
}
