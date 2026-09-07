// 3242. Design Neighbor Sum Service
// https://leetcode.com/problems/design-neighbor-sum-service/
// Easy | Java | Accepted 2026-09-06
// Runtime 18 ms | Memory 47.5 MB

class NeighborSum {
    Map<Integer, int[]> map = new HashMap<>();
    int[][] g;
    public NeighborSum(int[][] grid) {
        g = grid;
       for(int i = 0; i<grid.length; i++)
       {
        for(int j = 0; j<grid[0].length; j++)
        {
            map.put(grid[i][j], new int[]{i, j});
        }
       } 
    }
    
    public int adjacentSum(int value) {
        int count = 0;
        int i = map.get(value)[0];
        int j = map.get(value)[1];
        if(i-1>=0)
        {
            count+=g[i-1][j];
        }
        if(i+1<g.length)
        {
            count+=g[i+1][j];
        }
        if(j-1>=0)
        {
            count+=g[i][j-1];
        }
        if(j+1<g[0].length)
        {
            count+=g[i][j+1];
        }
        return count;
    }
    
    public int diagonalSum(int value) {
        int count = 0;
        int i = map.get(value)[0];
        int j = map.get(value)[1];
        if(i-1>=0 && j-1>=0)
        {
            count+=g[i-1][j-1];
        }
        if(i+1<g.length && j-1>=0)
        {
            count+=g[i+1][j-1];
        }
        if(i-1>=0 && j+1<g[0].length)
        {
            count+=g[i-1][j+1];
        }
        if(i+1<g[0].length && j+1<g[0].length)
        {
            count+=g[i+1][j+1];
        }
        return count;
    }
}

/**
 * Your NeighborSum object will be instantiated and called as such:
 * NeighborSum obj = new NeighborSum(grid);
 * int param_1 = obj.adjacentSum(value);
 * int param_2 = obj.diagonalSum(value);
 */
