// 51. N-Queens
// https://leetcode.com/problems/n-queens/
// Hard | Java | Accepted 2025-12-19
// Runtime 3 ms | Memory 46.9 MB

class Solution {
    List<List<String>> ans = new ArrayList<>();
    int num;
    public List<List<String>> solveNQueens(int n) {
        char[][] board = new char[n][n];
        for(int r = 0; r<n; r++)
        {
            for(int c = 0; c<n; c++)
            {
                board[r][c] = '.';
            }
        }
        num = n;
        recurse(board, 0);
        return ans;
        }

    public void recurse(char[][] board, int r)
    {
        if(r==board.length)
        {
            List<String> res = new ArrayList<>();
            for(int l = 0; l<num; l++)
            {
                res.add(new String(board[l]));
            }
            ans.add(new ArrayList<>(res));
            return;
        }
        for(int j = 0; j<num; j++)
        {
            if(isValid(board, r, j))
            {
            board[r][j] = 'Q';
            recurse(board, r+1);
            board[r][j] = '.';
            }
        }
    }

    public boolean isValid(char[][] board, int r, int c)
    {

        for(int row = 0; row<r; row++)
        {
            if(board[row][c]=='Q')
            {
                return false;
            }
        }
        for(int row1 = r-1, col1 = c-1; row1 >=0 && col1 >=0; row1--, col1--)
        {
            if(board[row1][col1]=='Q')
            {
                return false;
            }
        } 
        for(int row2 = r-1, col2 = c+1; row2>=0 && col2<board.length; row2--, col2++)
        {
            if(board[row2][col2]=='Q')
            {
                return false;
            }
        }    
        return true;  
    }
}
