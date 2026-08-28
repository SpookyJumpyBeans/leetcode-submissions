// 48. Rotate Image
// https://leetcode.com/problems/rotate-image/
// Medium | Java | Accepted 2026-04-20
// Runtime 0 ms | Memory 44 MB

class Solution {
    public void rotate(int[][] matrix) {
        for(int i = 0; i<matrix.length/2; i++)
        {
            int[] temp = matrix[matrix.length-i-1];
            matrix[matrix.length-i-1] = matrix[i];
            matrix[i] = temp;
        }
        for(int j = 0; j<matrix.length; j++)
        {
            for(int k = j+1; k<matrix.length; k++)
            {
                int temp1 = matrix[j][k];
                matrix[j][k] = matrix[k][j];
                matrix[k][j] = temp1;
            }
        }
    }
}
