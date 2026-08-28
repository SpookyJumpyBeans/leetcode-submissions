// 2392. Build a Matrix With Conditions
// https://leetcode.com/problems/build-a-matrix-with-conditions/
// Hard | Java | Accepted 2026-08-27
// Runtime 10 ms | Memory 58.8 MB

class Solution {
    public int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {
        int[] countRow = new int[k];
        int[] countCol = new int[k];
        int[] rows = new int[k];
        int[] cols = new int[k];
        List<Integer>[] graphRow = new ArrayList[k];
        List<Integer>[] graphCol = new ArrayList[k];
        Queue<Integer> rowBFS = new LinkedList<>();
        Queue<Integer> colBFS = new LinkedList<>();
        for(int[] row : rowConditions)
        {
            if(graphRow[row[0]-1]==null)
            {
                graphRow[row[0]-1] = new ArrayList<>();
            }
            graphRow[row[0]-1].add(row[1]-1);
            countRow[row[1]-1]++;
        }
        for(int[] col : colConditions)
        {
            if(graphCol[col[0]-1]==null)
            {
                graphCol[col[0]-1] = new ArrayList<>();
            }
            graphCol[col[0]-1].add(col[1]-1);
            countCol[col[1]-1]++;
        }
        for(int i = 0; i<countRow.length; i++)
        {
            if(countRow[i]==0)
            {
                rowBFS.add(i);
            }
        }
        for(int i = 0; i<countCol.length; i++)
        {
            if(countCol[i]==0)
            {
                colBFS.add(i);
            }
        }
        int rowStart = 0;
        while(!rowBFS.isEmpty())
        {
            int curr = rowBFS.poll();
            rows[curr] = rowStart;
            rowStart++;
            List<Integer> neigh = graphRow[curr];
            if(neigh!=null)
            {
                for(int i = 0; i<neigh.size(); i++)
                {
                    int node = neigh.get(i);
                    countRow[node]--;
                    if(countRow[node]==0)
                    {
                        rowBFS.add(node);
                    }
                }
            }
        }
        if(rowStart!=k)
        {
            return new int[0][0];
        }
        int colStart = 0;
        while(!colBFS.isEmpty())
        {
            int curr = colBFS.poll();
            cols[curr] = colStart;
            colStart++;
            List<Integer> neigh = graphCol[curr];
            if(neigh!=null)
            {
                for(int i = 0; i<neigh.size(); i++)
                {
                    int node = neigh.get(i);
                    countCol[node]--;
                    if(countCol[node]==0)
                    {
                        colBFS.add(node);
                    }
                }
            }
        }
        if(colStart!=k)
        {
            return new int[0][0];
        }
        int[][] ans = new int[k][k];
        for(int i = 0; i<k; i++)
        {
            ans[rows[i]][cols[i]] = i+1;
        }   
        return ans;
    }
}
