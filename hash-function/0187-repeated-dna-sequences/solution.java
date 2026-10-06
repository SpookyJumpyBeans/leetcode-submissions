// 187. Repeated DNA Sequences
// https://leetcode.com/problems/repeated-dna-sequences/
// Medium | Java | Accepted 2026-10-05
// Runtime 421 ms | Memory 273.7 MB

class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        //Use two sets
        //One stores the values we encounter (If we encounter an item more than once, the set will already contain the first instance we saw it)
        //One stores the values we're going to return
        Set<String> set = new HashSet<>();
        Set<String> added = new HashSet<>();
        for(int i = 0; i+10<=s.length(); i++)
        {
            String curr = s.substring(i, i+10); //Get the current 10 length squence
            if(!set.add(curr)) //Add it to the set
            //If we can't add it to the set, this means this item occurs more than once in the string, so add it to the set of answers
            {
                added.add(curr);
            }
        }
        return new ArrayList<>(added); //Return the set of answers
    }
}
