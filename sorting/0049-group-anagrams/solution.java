// 49. Group Anagrams
// https://leetcode.com/problems/group-anagrams/
// Medium | Java | Accepted 2026-09-15
// Runtime 19 ms | Memory 51.2 MB

class Solution { 
    public List<List<String>> groupAnagrams(String[] strs) {
            Map<String, List<String>> map = new HashMap<>();
            for(String str : strs)
            {
                //Can do this way or turn each string into a char array and then sort it
                //This way technically runs faster O(MxN) (Linear) where M is the number of strings and N is the maximum length of the string
                //Sorting the string will cost O(MxNlogN) where NlogN is how long it takes to sort the string
                int[] counts = new int[26]; //Make a frequency count for every word
                for(int i = 0; i<str.length(); i++) 
                {
                    counts[str.charAt(i)-'a']++; //Get the frequencies for every word
                }
                String key = Arrays.toString(counts); //Turn the entire frequency array into a key
                map.computeIfAbsent(key, k -> new ArrayList<>()).add(str); //If this key exists, add this word into the list associated with the key, otherwise, make a new key and empty arraylist
            }
            List<List<String>> ans = new ArrayList<>();
            for(String temp : map.keySet())
            {
                ans.add(map.get(temp)); //Add all the lists to the 2D arraylist
            }
            return ans;
    }
}
