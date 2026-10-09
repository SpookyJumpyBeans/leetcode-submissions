// 348. Design Tic-Tac-Toe
// https://leetcode.com/problems/design-tic-tac-toe/
// Medium | Java | Accepted 2026-10-08
// Runtime 4 ms | Memory 48.1 MB

class TicTacToe {
    // Arrays to store the number of marks each player has in a specific row or column.
    // Dimensions are [n][3] because player IDs are 1 and 2 (index 0 is left unused for simplicity).
    int[][] rows;
    int[][] cols;
    
    // Stores the marks for the 2 diagonals. 
    // diag[0] is the main diagonal, diag[1] is the anti-diagonal.
    int[][] diag = new int[2][3];
    int n;
    
    public TicTacToe(int n) {
        // Initialize the board size and the tracking arrays
        rows = new int[n][3];
        cols = new int[n][3];
        this.n = n;
    }
    
    public int move(int row, int col, int player) {
        // Increment the mark tally for this player in the given row and column
        rows[row][player]++;
        cols[col][player]++;
        
        // If the row and column indices are equal, the move is on the main diagonal (e.g., [0,0], [1,1])
        if(row == col) {
            diag[0][player]++;
        }
        
        // If the row and column indices sum to n - 1, the move is on the anti-diagonal (e.g., [0,2], [1,1], [2,0] for n=3)
        if(row + col == n - 1) {
            diag[1][player]++;
        }
        
        // A player wins if their tally reaches 'n' for any row, column, or diagonal touched by this move
        if(rows[row][player] >= n || cols[col][player] >= n || diag[0][player] >= n || diag[1][player] >= n) {
            return player;
        }
        
        // Return 0 if the game has not been won yet
        return 0;
    }
}

/**
 * Your TicTacToe object will be instantiated and called as such:
 * TicTacToe obj = new TicTacToe(n);
 * int param_1 = obj.move(row,col,player);
 */
