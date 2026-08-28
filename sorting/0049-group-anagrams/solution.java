// 49. Group Anagrams
// https://leetcode.com/problems/group-anagrams/
// Medium | Java | Accepted 2024-09-05
// Runtime 6 ms | Memory 47.4 MB

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> bruh = new ArrayList<List<String>>();
        Map <String, List<String>> map = new HashMap<>();
        for(int i = 0; i<strs.length; i++)
        {
            String bruhh = strs[i];
            char[] arr = bruhh.toCharArray();
            Arrays.sort(arr);
            String b = new String(arr);
            if(!map.containsKey(b))
            {
                map.put(b, new ArrayList<>());
                map.get(b).add(bruhh);
            }
            else
            {
                map.get(b).add(bruhh);
            }
        }
        return new ArrayList<>(map.values());
    }
}
