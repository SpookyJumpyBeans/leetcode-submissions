// 208. Implement Trie (Prefix Tree)
// https://leetcode.com/problems/implement-trie-prefix-tree/
// Medium | Java | Accepted 2025-12-20
// Runtime 33 ms | Memory 62.2 MB

class Trie {
    class TrieNode {
        private TrieNode[] children;
        private boolean isEnd;

        public TrieNode()
        {
            children = new TrieNode[26];
            isEnd = false;
        }
    }

    private TrieNode root;
    public Trie() {
        root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode temp = root;
        char[] arr = word.toCharArray();
        for(char c : arr)
        {
            int ind = c - 'a';
            if(temp.children[ind] == null)
            {
                temp.children[ind] = new TrieNode();
            }
                temp = temp.children[ind];
        }
        temp.isEnd = true;
    }
    
    public boolean search(String word) {
        TrieNode temp = root;
        char[] arr = word.toCharArray();
        for(char c : arr)
        {
             int ind = c - 'a';
             if(temp.children[ind]==null)
             {
                return false;
             }
             temp = temp.children[ind];
        }
        return temp.isEnd;
    }
    
    public boolean startsWith(String prefix) {
        TrieNode temp = root;
        char[] arr = prefix.toCharArray();
        for(char c : arr)
        {
             int ind = c - 'a';
             if(temp.children[ind]==null)
             {
                return false;
             }
             temp = temp.children[ind];
        }
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
