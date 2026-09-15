// 2265. Count Nodes Equal to Average of Subtree
// https://leetcode.com/problems/count-nodes-equal-to-average-of-subtree/
// Medium | Java | Accepted 2026-09-14
// Runtime 0 ms | Memory 45.6 MB

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    int cnt = 0;
    public int averageOfSubtree(TreeNode root) {
        getAverages(root, 1);
        return cnt;
    }
    
    public int[] getAverages(TreeNode root, int count)
    {
        if(root==null)
        {
            return new int[]{-1, -1};
        }
        int[] leftAvg = getAverages(root.left, count);
        int[] rightAvg = getAverages(root.right, count);
        int curr = root.val;
        int nodes = count;
        if(leftAvg[0]!=-1)
        {
            curr+=leftAvg[0];
            nodes+=leftAvg[1];
        }
        if(rightAvg[0]!=-1)
        {
            curr+=rightAvg[0];
            nodes+=rightAvg[1];
        }
        if(curr/nodes==root.val)
        {
            cnt++;
        }
        return new int[]{curr, nodes};
    }
}
