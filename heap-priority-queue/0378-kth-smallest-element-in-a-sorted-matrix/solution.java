// 378. Kth Smallest Element in a Sorted Matrix
// https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/
// Medium | Java | Accepted 2026-02-08
// Runtime 17 ms | Memory 50.8 MB

class Solution {
    public class Node
    {
        int r;
        int c;
        int val;
        public Node(int r, int c, int val)
        {
            this.r = r;
            this.c = c;
            this.val = val;
        }
    }
    public int kthSmallest(int[][] matrix, int k) {
       PriorityQueue<Node> q = new PriorityQueue<>((a, b)-> (a.val-b.val));
       for(int i = 0; i<matrix.length; i++)
       {
        q.add(new Node(i, 0, matrix[i][0]));
       }
       int ans = 0;
       while(k-->0)
       {
        Node temp = q.poll();
        if(temp.c+1<matrix[0].length)
        {
            q.add(new Node(temp.r, temp.c+1, matrix[temp.r][temp.c+1]));
        }
        if(k==0)
        {
            ans = temp.val;
        }
       }
       return ans;
    }
}
