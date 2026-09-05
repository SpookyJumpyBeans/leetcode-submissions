// 463. Island Perimeter
// https://leetcode.com/problems/island-perimeter/
// Easy | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    vector<vector<int>> gridd;
    int ans = 0;
    int islandPerimeter(vector<vector<int>>& grid) {
        gridd = grid;
        for(int i = 0 ; i<grid.size(); i++)
        {
            for(int j = 0; j<grid[0].size();j++)
            {
                if(grid[i][j]==1)
                {
                    recurse(i, j);
                    return ans;
                }
            }
        }
        return -1;
    }

    void recurse(int i, int j)
    {
        if(i>=gridd.size() || i<0 || j<0 || j>=gridd[0].size() || gridd[i][j]==0 || gridd[i][j]==2)
        {
            return;
        }
        int perim = 4;
        if(i+1<gridd.size() && (gridd[i+1][j]==1 || gridd[i+1][j] == 2))
        {
            perim--;
        }
          if(j+1<gridd[0].size() && (gridd[i][j+1]==1 || gridd[i][j+1]==2))
        {
            perim--;
        }
          if(i-1>=0 && (gridd[i-1][j]==1 || gridd[i-1][j]==2))
        {
            perim--;
        }
          if(j-1>=0 && (gridd[i][j-1]==1 || gridd[i][j-1]==2))
        {
            perim--;
        }
        ans+=perim;
        gridd[i][j] = 2;
        recurse(i+1, j);
        recurse(i, j+1);
        recurse(i-1, j);
        recurse(i, j-1);
    }
};
