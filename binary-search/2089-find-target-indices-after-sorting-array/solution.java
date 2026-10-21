// 2089. Find Target Indices After Sorting Array
// https://leetcode.com/problems/find-target-indices-after-sorting-array/
// Easy | Java | Accepted 2026-10-20
// Runtime 6 ms | Memory 47.2 MB

class Solution {
    public List<Integer> targetIndices(int[] nums, int target) {
        List<Integer> list = new ArrayList<>();
        Arrays.sort(nums);
        for(int i = 0; i<nums.length; i++)
        {
            if(target==nums[i])
            {
                list.add(i);
            }
        }
        return list;
    }
}
