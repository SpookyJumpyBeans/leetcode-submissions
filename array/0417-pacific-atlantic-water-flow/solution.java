// 417. Pacific Atlantic Water Flow
// https://leetcode.com/problems/pacific-atlantic-water-flow/
// Medium | Java | Accepted 2026-01-05
// Runtime 10 ms | Memory 48.4 MB

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
    List<List<Integer>> ans = new ArrayList<>();
    boolean[][] atlantic;
    boolean[][] pacific;
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        atlantic = new boolean[heights.length][heights[0].length];
        pacific = new boolean[heights.length][heights[0].length];
        Queue<Ind> qA = new LinkedList<>();
        Queue<Ind> qP = new LinkedList<>();
        for(int i = 0; i<heights.length; i++)
        {
            for(int j = 0; j<heights[0].length; j++)
            {
                if(i==0)
                {
                    pacific[i][j] = true;
                    qP.add(new Ind(i, j));
                }
                if(j==0)
                {
                    pacific[i][j] = true;
                    qP.add(new Ind(i, j));
                }
                if(i==heights.length-1)
                {
                    atlantic[i][j] = true;
                    qA.add(new Ind(i, j));
                }
                if(j==heights[0].length-1)
                {
                    atlantic[i][j] = true;
                    qA.add(new Ind(i, j));
                }
            }
        }
        while(!qP.isEmpty())
        {
            Ind temp = qP.poll();
            int r = temp.row;
            int c = temp.col;
            if(r+1<heights.length && c<heights[0].length && heights[r+1][c]>=heights[r][c]&&!pacific[r+1][c])
            {
                pacific[r+1][c] = true;
                qP.add(new Ind(r+1, c));
            }
            if(r-1>=0 && c<heights[0].length &&  heights[r-1][c]>=heights[r][c]&&!pacific[r-1][c])
            {
                pacific[r-1][c] = true;
                qP.add(new Ind(r-1, c));
            }
             if(r<heights.length &&c+1<heights[0].length &&heights[r][c+1]>=heights[r][c]&&!pacific[r][c+1])
            {
               pacific[r][c+1] = true;
                qP.add(new Ind(r, c+1));
            }
             if(r<heights.length &&c-1>=0 &&heights[r][c-1]>=heights[r][c]&&!pacific[r][c-1])
            {
                pacific[r][c-1] = true;
                qP.add(new Ind(r, c-1));
            }
        }
         while(!qA.isEmpty())
        {
            Ind temp = qA.poll();
            int r = temp.row;
            int c = temp.col;
            if(r+1<heights.length && c<heights[0].length && heights[r+1][c]>=heights[r][c]&&!atlantic[r+1][c])
            {
                atlantic[r+1][c] = true;
                qA.add(new Ind(r+1, c));
            }
            if(r-1>=0 && c<heights[0].length &&  heights[r-1][c]>=heights[r][c]&&!atlantic[r-1][c])
            {
                atlantic[r-1][c] = true;
                qA.add(new Ind(r-1, c));
            }
             if(r<heights.length &&c+1<heights[0].length &&heights[r][c+1]>=heights[r][c]&&!atlantic[r][c+1])
            {
               atlantic[r][c+1] = true;
                qA.add(new Ind(r, c+1));
            }
             if(r<heights.length &&c-1>=0 &&heights[r][c-1]>=heights[r][c]&&!atlantic[r][c-1])
            {
                atlantic[r][c-1] = true;
                qA.add(new Ind(r, c-1));
            }
        }
        for(int k = 0; k<heights.length; k++)
        {
            for(int l = 0; l<heights[0].length; l++)
            {
                if(atlantic[k][l]&&pacific[k][l])
                {
                    ans.add(new ArrayList<>(Arrays.asList(k, l)));
                }
            }
        }
        return ans;
    }

   
}
