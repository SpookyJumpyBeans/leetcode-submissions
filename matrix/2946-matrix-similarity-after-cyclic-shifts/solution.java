// 2946. Matrix Similarity After Cyclic Shifts
// https://leetcode.com/problems/matrix-similarity-after-cyclic-shifts/
// Easy | Java | Accepted 2026-11-04
// Runtime 2 ms | Memory 46.6 MB

class Solution {
    public boolean areSimilar(int[][] mat, int k) {
        int n = mat[0].length;
        k = k % n; // Simplify k right away
        
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < n; j++) {
                if (i % 2 != 0) { // Odd rows (Right shift)
                    if (mat[i][j] != mat[i][(j - k + n) % n]) return false;
                } else { // Even rows (Left shift)
                    if (mat[i][j] != mat[i][(j + k) % n]) return false;
                }
            }
        }
        return true;
    }
}
