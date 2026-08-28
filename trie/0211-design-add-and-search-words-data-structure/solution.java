// 211. Design Add and Search Words Data Structure
// https://leetcode.com/problems/design-add-and-search-words-data-structure/
// Medium | Java | Accepted 2025-12-20
// Runtime 177 ms | Memory 274.1 MB

class WordDictionary {

    class TrieNode{
        private TrieNode[] arr;
        private boolean isEnd;
        public TrieNode()
        {
            arr = new TrieNode[26];
            isEnd = false;
        }
    }

    public TrieNode root;
    public WordDictionary() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode temp = root;
        char[] charr = word.toCharArray();
        for(char c : charr)
        {
            int i = c-'a';
            if(temp.arr[i]==null)
            {
                temp.arr[i] = new TrieNode();
            }
            temp = temp.arr[i];
        }
        temp.isEnd = true;
    }
    
    public boolean search(String word) {
         TrieNode temp = root;
        char[] charr = word.toCharArray();
        return recurse(temp, 0, charr);
    }
    
    public boolean recurse(TrieNode temp, int index, char[] charr)
    {
       if(index==charr.length)
       {
        return temp.isEnd;
       }

        if(charr[index]!='.')
            {
                if(temp.arr[charr[index]-'a']!=null)
                {
                return recurse(temp.arr[charr[index]-'a'], index+1, charr);
                }
                else
                {
                    return false;
                }
            }
        if(charr[index]=='.')
        {
       for(int i = 0; i<26; i++)
       {
            if(temp.arr[i]!=null)
            {
              if(recurse(temp.arr[i],index+1, charr))
              {
                return true;
              }
            }
       }
        }
       return false;
        
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
