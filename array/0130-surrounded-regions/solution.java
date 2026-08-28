// 130. Surrounded Regions
// https://leetcode.com/problems/surrounded-regions/
// Medium | Java | Accepted 2026-01-05
// Runtime 8 ms | Memory 47.7 MB

class Solution {
    public class Ind
    {
        public int row;
        public int col;
        public Ind(int r, int c)
        {
            row = r;
            col = c;
        }
    }
    List<Ind> convert = new ArrayList<>();
    boolean flag = false;
    public void solve(char[][] board) {

            for(int j = 0; j<board[0].length; j++)
            {
                if(board[0][j]=='O')
                {
                    recurse(board, 0, j);
                }
                if(board[board.length-1][j]=='O')
                {
                    recurse(board, board.length-1, j);
                }
            }
             for(int l = 0; l<board.length; l++)
            {
                if(board[l][0]=='O')
                {
                    recurse(board, l, 0);
                }
                if(board[l][board[0].length-1]=='O')
                {
                    recurse(board, l, board[0].length-1);
                }
            }
        for(int k = 0; k<board.length; k++)
        {
            System.out.println(Arrays.toString(board[k]));
            for(int l = 0; l<board[0].length; l++)
            {
                  if(board[k][l]=='O')
                {
                    board[k][l]='X';
                }
                if(board[k][l]=='T')
                {
                    board[k][l]='O';
                }
            }
        }
    }

    public void recurse(char[][] b, int r, int c)
    {
        if(r<0 || c<0 || r>=b.length || c>=b[0].length)
        {
            return;
        }
        if(b[r][c]=='X')
        {
            return;
        }
        if(b[r][c]=='O')
        {
        b[r][c]= 'T';
        recurse(b, r+1, c);
        recurse(b, r-1, c);
        recurse(b, r, c+1);
        recurse(b, r, c-1);
        }
    }
}
