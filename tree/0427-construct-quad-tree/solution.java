// 427. Construct Quad Tree
// https://leetcode.com/problems/construct-quad-tree/
// Medium | Java | Accepted 2026-08-26
// Runtime 1 ms | Memory 46.4 MB

/*
// Definition for a QuadTree node.
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;

    
    public Node() {
        this.val = false;
        this.isLeaf = false;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }
    
    public Node(boolean val, boolean isLeaf) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }
    
    public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
}
*/

class Solution {
    int[][] g;
    public Node construct(int[][] grid) {
        g = grid;
        Node root = new Node();
        root = recurse(root, 0, grid.length, 0, grid.length);
        return root;
    }
    public Node recurse(Node root, int rowS, int rowE, int colS, int colE)
    {
        if(rowS+1==rowE && colS+1==colE)
        {
            return new Node(g[rowS][colS] == 0 ? false:true, true);
        }
        int temp = -1;
        for(int i = rowS; i<rowE; i++)
        {
            for(int j = colS; j<colE; j++)
            {
                if(g[i][j]==0 && temp == -1)
                {
                    temp = 0;
                }
                else if(g[i][j]==1 && temp == -1)
                {
                    temp = 1;
                }
                else if(g[i][j]!=temp)
                {
                   root.topLeft = new Node();
                    root.topRight = new Node();
                    root.bottomLeft = new Node();
                    root.bottomRight = new Node();
                    root.topLeft = recurse(root.topLeft, rowS, rowS+(rowE-rowS)/2, colS, colS+(colE-colS)/2);
                    root.topRight = recurse(root.topRight, rowS, rowS+(rowE-rowS)/2, colS+(colE-colS)/2, colE);
                    root.bottomLeft = recurse(root.bottomLeft, rowS+(rowE-rowS)/2, rowE, colS, colS+(colE-colS)/2);
                    root.bottomRight = recurse(root.bottomRight, rowS+(rowE-rowS)/2, rowE, colS+(colE-colS)/2, colE);
                    return root;
                }
            }
        }
        return new Node(g[rowS][colS] == 0 ? false:true, true);
    }   
}
