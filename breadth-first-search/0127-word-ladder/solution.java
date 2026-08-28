// 127. Word Ladder
// https://leetcode.com/problems/word-ladder/
// Hard | Java | Accepted 2026-01-05
// Runtime 77 ms | Memory 47.9 MB

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Queue<Pair<String, Integer>> queue = new LinkedList<>();
        Set<String> set = new HashSet<>();
        for(int k = 0; k<wordList.size(); k++)
        {
            set.add(wordList.get(k));
        }
        if(!set.contains(endWord))
        {
            return 0;
        }
        queue.add(new Pair(beginWord, 1));
        while(!queue.isEmpty())
        {
            Pair pair = queue.poll();
            String key = (String) pair.getKey();
            char[] arr = key.toCharArray();
            for(int j = 0; j<beginWord.length(); j++)
            {
                
            for(char i = 'a'; i<='z'; i++)
            {
                arr[j] = i;
                String newText = new String(arr);
                if(set.contains(newText))
                {
                    queue.add(new Pair(newText, (int) pair.getValue()+1));
                    set.remove(newText);
                }
                if(newText.equals(endWord))
                {
                    return (int) pair.getValue()+1;
                }
            }
            arr = key.toCharArray();
            }
        }
        return 0;
    }
}
