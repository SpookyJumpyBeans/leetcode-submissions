// 54. Spiral Matrix
// https://leetcode.com/problems/spiral-matrix/
// Medium | Java | Accepted 2026-08-11
// Runtime 0 ms | Memory 42.8 MB

class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        int top = 0;
        int bottom = matrix.length-1;
        int right = matrix[0].length-1;
        int left = 0;
        List<Integer> ans = new ArrayList<>(); 
        while(top<=bottom && left<=right) 
        {
            for(int i = left; i<=right; i++) //Go from left to right
            {
                ans.add(matrix[top][i]);
            }

            top++; //Adding 1 to top after ensures we don't include the corner twice
            if(top>bottom || left>right) 
            {
                break;
            }
                for(int i = top; i<=bottom; i++) //Go from top to bottom
            {

                ans.add(matrix[i][right]); 
            }
            right--; //Don't include corner twice
           if(top>bottom || left>right) 
            {
                break;
            }
                for(int i = right; i>=left; i--) //Go from right to left
            {
                ans.add(matrix[bottom][i]); 
            }
            bottom--; //Don't include the turn twice
            if(top>bottom || left>right) 
            {
                break;
            }
                for(int i = bottom; i>=top; i--) //Go from bottom to top
            {
                ans.add(matrix[i][left]);
            } 
            left++; //Don't include turn twice
        }
        return ans;
    }
}
