// 212. Word Search II
// https://leetcode.com/problems/word-search-ii/
// Hard | Java | Accepted 2026-01-03
// Runtime 158 ms | Memory 47.1 MB

class Solution {
    class TrieNode {
        private TrieNode[] children;
        private String word;

        public TrieNode()
        {
            children = new TrieNode[26];
        }
    }
    public Trie tree;
    public List<String> ans;
    class Trie {

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
        temp.word = word;
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
        return temp.word!=null;
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
    public List<String> findWords(char[][] board, String[] words) {
        tree = new Trie();
        ans = new ArrayList<>();
        for(int i = 0; i<words.length; i++)
        {
            tree.insert(words[i]);
        }
        for(int j = 0; j<board.length; j++)
        {
            for(int k = 0; k<board[0].length; k++)
            {
                recurse(board, j, k, tree.root);
            }
        }
    return ans;
    }

    public void recurse(char[][] board, int r, int c, TrieNode temp)
    {
        if(r>=board.length || c>=board[0].length || r<0 || c<0)
        {
            return;
        }
        int index = board[r][c] - 'a';
        if(board[r][c]=='#' || temp.children[index]==null)
        {
            return;
        }
        temp = temp.children[index];
        if(temp.word!=null)
        {
            ans.add(temp.word);
            temp.word = null;
        }
        char temp2 = board[r][c];
        board[r][c] = '#';
        recurse(board, r+1, c, temp);
        recurse(board, r-1,c, temp);
        recurse(board, r, c+1, temp);
        recurse(board, r, c-1, temp);
        board[r][c] = temp2;
    }
}
