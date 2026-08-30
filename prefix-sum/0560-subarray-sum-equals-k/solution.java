// 560. Subarray Sum Equals K
// https://leetcode.com/problems/subarray-sum-equals-k/
// Medium | Java | Accepted 2026-08-29
// Runtime 23 ms | Memory 49.1 MB

import java.util.HashMap;

class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        int currSum = 0;
        // HashMap stores (prefix_sum, frequency)
        HashMap<Integer, Integer> prefixMap = new HashMap<>();
        
        // Base case: a prefix sum of 0 has occurred 1 time
        prefixMap.put(0, 1);
        
        for (int num : nums) {
            currSum += num;
            
            // If (currSum - k) exists, a valid subarray is found
            if (prefixMap.containsKey(currSum - k)) {
                count += prefixMap.get(currSum - k);
            }
            
            // Add the current sum to the map or increment its frequency
            prefixMap.put(currSum, prefixMap.getOrDefault(currSum, 0) + 1);
        }
        
        return count;
    }
}
