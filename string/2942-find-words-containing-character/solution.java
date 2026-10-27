// 2942. Find Words Containing Character
// https://leetcode.com/problems/find-words-containing-character/
// Easy | Java | Accepted 2026-10-27
// Runtime 2 ms | Memory 46.3 MB

class Solution {
    public List<Integer> findWordsContaining(String[] words, char x) {
        List<Integer> ans = new ArrayList<>();
        for(int i = 0; i<words.length; i++)
        {
            String word = words[i];
            if(word.indexOf(x)>=0)
            {
                ans.add(i);
            }
        }
        return ans;
    }
}
