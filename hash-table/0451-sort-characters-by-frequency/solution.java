// 451. Sort Characters By Frequency
// https://leetcode.com/problems/sort-characters-by-frequency/
// Medium | Java | Accepted 2026-02-07
// Runtime 376 ms | Memory 47.8 MB

class Solution {
    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i<s.length(); i++)
        {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0)+1);
        }
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> (b[1]-a[1]));
        for(Map.Entry<Character, Integer> e : map.entrySet())
        {
            q.add(new int[]{(int)e.getKey(), e.getValue()});
        }
        String ret = "";
        while(!q.isEmpty())
        {
            int[] temp = q.poll();
            for(int j = 0; j<temp[1]; j++)
            {
                ret+=(char)temp[0];
            }
        }
        return ret;
    }
}
