// 337. House Robber III
// https://leetcode.com/problems/house-robber-iii/
// Medium | Java | Accepted 2026-09-06
// Runtime 0 ms | Memory 46.7 MB

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
    public int rob(TreeNode root) {
        int[] res = dfs(root);
        return Math.max(res[0], res[1]);
    }

    public int[] dfs(TreeNode root)
    {
        if(root==null)
        {
            return new int[]{0, 0};
        }
        int[] left = dfs(root.left);
        int[] right = dfs(root.right);
        int maxSkip = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        int maxTake = root.val + left[0] + right[0];
        return new int[]{maxSkip, maxTake};
    }
}
