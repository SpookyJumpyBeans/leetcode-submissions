// 79. Word Search
// https://leetcode.com/problems/word-search/
// Medium | Java | Accepted 2025-12-14
// Runtime 135 ms | Memory 43.2 MB

class Solution {
    public boolean exist(char[][] board, String word) {
        // 1. Loop through every cell to find the start
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // If we find a path starting here, return true immediately
                if (recurse(board, i, j, word, 0)) return true;
            }
        }
        return false;
    }

    // Returns true if word is found starting at (r, c)
    public boolean recurse(char[][] board, int r, int c, String word, int index) {
        // BASE CASE 1: We found all letters successfully
        if (index == word.length()) return true;

        // BASE CASE 2: Bounds Check / Mismatch Check / Visited Check
        // If r or c are out of bounds OR letter doesn't match, return false
        if (r < 0 || r >= board.length || c<0 || c>=board[0].length || board[r][c] != word.charAt(index)) {
            return false;
        }

        // MARK VISITED (To prevent loops)
        char temp = board[r][c];
        board[r][c] = '#'; // Mark as used

        // RECURSE (Check Up, Down, Left, Right)
        // We look for 'index + 1' now
        boolean found = recurse(board, r+1, c, word, index+1) || recurse(board, r-1, c, word, index+1) || recurse(board, r, c+1, word, index+1) || recurse(board, r, c-1, word, index+1);

        // BACKTRACK (Unmark)
        board[r][c] = temp; 

        return found;
    }
}
