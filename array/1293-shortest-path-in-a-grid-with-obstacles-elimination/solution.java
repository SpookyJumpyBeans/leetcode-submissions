// 1293. Shortest Path in a Grid with Obstacles Elimination
// https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/
// Hard | Java | Accepted 2026-08-11
// Runtime 33 ms | Memory 49 MB

/* BACKTRACKING SOLUTION
class Solution {
    int[][] g;
    boolean[][][] visited;
    int ans = Integer.MAX_VALUE;

    public int shortestPath(int[][] grid, int k) {
        g = grid;
        int m = g.length;
        int n = g[0].length;
        visited = new boolean[m][n][k + 1];
        
        recurse(0, 0, k, 0);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public void recurse(int i, int j, int destroy, int steps) {
        // Out of bounds check
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length) {
            return;
        }
        
        // If already visited this cell with the exact same or greater remaining destroy count, skip
        if (visited[i][j][destroy]) {
            return;
        }

        // Base case: Reached the bottom-right corner
        if (i == g.length - 1 && j == g[0].length - 1) {
            ans = Math.min(ans, steps);
            return;
        }

        // Mark current state as visited
        visited[i][j][destroy] = true;

        // Explore all 4 directions (Down, Right, Up, Left)
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int[] dir : directions) {
            int ni = i + dir[0];
            int nj = j + dir[1];

            if (ni >= 0 && nj >= 0 && ni < g.length && nj < g[0].length) {
                if (g[ni][nj] == 1 && destroy > 0) {
                    g[ni][nj] = 0;
                    recurse(ni, nj, destroy - 1, steps + 1);
                    g[ni][nj] = 1; // backtrack grid
                } else if (g[ni][nj] == 0) {
                    recurse(ni, nj, destroy, steps + 1);
                }
            }
        }

        // Backtrack visited state so other paths can use it if necessary
        visited[i][j][destroy] = false;
    }
}
*/
class Solution {
    public int shortestPath(int[][] grid, int k) {
        if(grid.length == 1 && grid[0].length==1)
        {
            return grid[0][0] == 1 ? -1 : 0;
        }
        Queue<int[]> bfs = new LinkedList<>();
        boolean[][][] visited = new boolean[grid.length][grid[0].length][k+1];
        bfs.add(new int[]{0, 0, k, 0});
        visited[0][0][k] = true;
        while(!bfs.isEmpty())
        {
            int[] state = bfs.poll();
            int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
            for(int[] d : dir)
            {
                int ni = state[0]+d[0];
                int nj = state[1]+d[1];
                if(ni==grid.length-1 && nj == grid[0].length-1)
                {
                    return state[3]+1;
                }
    if(ni>=0 && nj>=0 && ni<grid.length && nj<grid[0].length && !visited[ni][nj][state[2]])
                {
                    visited[ni][nj][state[2]] = true;
                    if(grid[ni][nj]==1 && state[2]>0)
                    {
                        bfs.add(new int[]{ni, nj, state[2]-1, state[3]+1});
                    }
                    else if(grid[ni][nj]==0)
                    {
                        bfs.add(new int[]{ni, nj, state[2], state[3]+1});
                    }
                }
            }
        }
        return -1;
    }
}
