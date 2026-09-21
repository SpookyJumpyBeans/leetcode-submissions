// 733. Flood Fill
// https://leetcode.com/problems/flood-fill/
// Easy | Java | Accepted 2026-09-20
// Runtime 0 ms | Memory 47 MB

class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        //DFS
        if(image[sr][sc]==color)
        {
            return image;
        }
        dfs(image, image[sr][sc], color, sr, sc);
        return image;
    }

    public void dfs(int[][] image, int og, int color, int r, int c)
    {
       if(r<0 || c<0 || r>=image.length || c>=image[0].length || image[r][c]!=og) //If the indices are out of bounds or the current indices don't correlate to the original color, end the call early
       {
            return;
       }
       image[r][c] = color; //Else set the value to the color
       //Try all 4 directions
       dfs(image, og, color, r+1, c);
       dfs(image, og, color, r-1, c);
       dfs(image, og, color, r, c+1);
       dfs(image, og, color, r, c-1);
    }
}
