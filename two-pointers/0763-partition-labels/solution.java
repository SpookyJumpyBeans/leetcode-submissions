// 763. Partition Labels
// https://leetcode.com/problems/partition-labels/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {
    public List<Integer> partitionLabels(String s) {
        int size = 0;
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i<s.length(); i++)
        {
                map.put(s.charAt(i), i);
        }
        List<Integer> ans = new ArrayList<>();
        int max = 0;
        for(int j = 0; j<s.length(); j++)
        {
            size++;
            max = Math.max(max, map.get(s.charAt(j)));
            if(j==max)
            {
                ans.add(size);
                size = 0;
            }
        }
        return ans;
    }
}
