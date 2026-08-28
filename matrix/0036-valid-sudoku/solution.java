// 36. Valid Sudoku
// https://leetcode.com/problems/valid-sudoku/
// Medium | Java | Accepted 2024-09-06
// Runtime 74 ms | Memory 45.2 MB

class Solution {
    public boolean isValidSudoku(char[][] board) {
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j<board[0].length; j++)
            {
                System.out.print(board[i][j] + " ");
                if(!map.containsKey(board[i][j]))
                {
                    map.put(board[i][j], 1);
                }
                else
                {
                  if(board[i][j]!='.')
                    {
                    return false;
                    }
                }
            }
            System.out.println();
            map.clear();
        }
        for(int k = 0; k < board.length; k++)
        {
            for(int l = 0; l<board[0].length; l++)
            {
                if(!map.containsKey(board[l][k]))
                {
                    map.put(board[l][k], 1);
                }
                else
                {
                    if(board[l][k]!='.')
                    {
                    return false;
                    }
                }
            }
            map.clear();
        } 
        int count = 0;
         for(int m = 0; m < board.length; m++)
        {
            if(count>=3)
            {
                break;
            }
            m = 0;
            map.clear();
            for(int n = count*3; n<board[0].length; n++)
            {
                if(!map.containsKey(board[m][n]))
                {
                    map.put(board[m][n], 1);
                }
                else
                {
                     if(board[m][n]!='.')
                    {
                    return false;
                    }
                }
                if((n+1)%3==0&&n!=0)
                {
                    m++;
                    n-=3;
                    if(m%3==0)
                    {
                        map.clear();
                    }
                }
                if(m==9)
                {
                    break;
                }
            }
            m = 0;
            count++;
        } 
        return true;
    }
}
