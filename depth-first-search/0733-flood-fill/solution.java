// 733. Flood Fill
// https://leetcode.com/problems/flood-fill/
// Easy | Java | Accepted 2022-08-08
// Runtime 1 ms | Memory 47.4 MB

class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        if(image[sr][sc]==color)
        {
            return image;
        }
        joe(image,sr,sc,image[sr][sc], color);
        return image;
    }
    
    public void joe (int[][] kay, int r, int c, int color, int newColor)
    {
      if(r<0||r>=kay.length||c<0||c>=kay[0].length||kay[r][c]!=color)
      {
        return;
      }
        kay[r][c] = newColor;
        joe(kay, r-1, c, color, newColor);
        joe(kay, r+1, c, color, newColor);
        joe(kay, r, c+1, color, newColor);
        joe(kay, r, c-1, color, newColor);
    }
}

/*

DFS-recursive(G, s):
        mark s as visited
        for all neighbours w of s in Graph G:
            if w is not visited:
                DFS-recursive(G, w)


*/
