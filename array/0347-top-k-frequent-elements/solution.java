// 347. Top K Frequent Elements
// https://leetcode.com/problems/top-k-frequent-elements/
// Medium | Java | Accepted 2024-09-05
// Runtime 18 ms | Memory 48 MB

import java.util.Map.*;
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> bruh = new ArrayList<>();
        int[] arr = new int[k];
        for(int i = 0; i<nums.length; i++)
        {
            if(!map.containsKey(nums[i]))
            {
                map.put(nums[i], 1);
            }
            else
            {
                map.put(nums[i], map.get(nums[i])+1);
            }
        }
      List<Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet()); 
      list.sort(Entry.comparingByValue()); 
      Collections.reverse(list);
        int m = 0;
      for (Entry<Integer, Integer> entry : list) { 
        if(m>=k)
        {
            break;
        }
        arr[m] = entry.getKey();
        m++;
        }
            return arr;
    }
}
