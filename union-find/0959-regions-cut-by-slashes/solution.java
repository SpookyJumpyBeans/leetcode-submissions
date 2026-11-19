// 959. Regions Cut By Slashes
// https://leetcode.com/problems/regions-cut-by-slashes/
// Medium | Java | Accepted 2026-11-12
// Runtime 16 ms | Memory 45.7 MB

class Solution {
    public int regionsBySlashes(String[] grid) {
        char[][] bigGrid = new char[grid.length*3][grid.length*3];
        for(char[] t : bigGrid)
        {
            Arrays.fill(t, '0');
        }
        //Count islands problem
        //Represent lines as 1s
        //Increase the size of each 1x1 cell to 3x3 so we can distinctly create islands of 0s and 1s
        //For every forward slash, do 
        // 0 0 1
        // 0 1 0
        // 1 0 0 
        //For every back slash, do
        // 1 0 0
        // 0 1 0
        // 0 0 1
        //Then, count the number of islands where 0s represent the regions
        for(int i = 0; i<grid.length; i++)
        {
            String curr = grid[i];
            for(int j = 0; j<curr.length(); j++)
            {
                if(curr.charAt(j)=='/')
                {
                    int x = i*3;
                    int y = j*3+2;
                    bigGrid[x][y] = '1';
                    bigGrid[x+1][y-1] = '1';
                    bigGrid[x+2][y-2] = '1';
                }
                else if(curr.charAt(j)!=' ')
                {
                    int x = i*3;
                    int y = j*3;
                    bigGrid[x][y] = '1';
                    bigGrid[x+1][y+1] = '1';
                    bigGrid[x+2][y+2] = '1';
                }
            }
        }
        int count = 0;
        for(int i = 0; i<bigGrid.length; i++)
        {
            for(int j = 0; j<bigGrid.length; j++)
            {
                if(bigGrid[i][j]=='0')
                {
                    Queue<int[]> bfs = new ArrayDeque<>();
                    count++;
                    bfs.add(new int[]{i, j});
                    bigGrid[i][j] = '1';
                    int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
                    while(!bfs.isEmpty())
                    {
                        int[] curr = bfs.poll();
                        for(int[] d : dir)
                        {
                            int x = curr[0] + d[0];
                            int y = curr[1] + d[1];
                            if(x>=0 && y>=0 && x<bigGrid.length && y<bigGrid.length && bigGrid[x][y] == '0')
                            {
                                bfs.add(new int[]{x, y});
                                bigGrid[x][y] = '1';
                            }
                        }
                    }
                }
            }
        }
        return count;
    }
}
