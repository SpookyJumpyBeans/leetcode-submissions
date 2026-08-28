// 217. Contains Duplicate
// https://leetcode.com/problems/contains-duplicate/
// Easy | Java | Accepted 2022-08-15
// Runtime 17 ms | Memory 71.2 MB

class Solution {
    public boolean containsDuplicate(int[] nums) {
        Map<Integer,Integer> jeo = new HashMap<>();
        for(int i = 0; i<nums.length; i++)
        {
            if(jeo.containsKey(nums[i])==true)
            {
               return true; 
            }
            jeo.put(nums[i], 0);
        }
        return false;
    }
}
