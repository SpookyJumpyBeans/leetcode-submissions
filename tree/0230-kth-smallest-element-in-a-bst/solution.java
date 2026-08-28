// 230. Kth Smallest Element in a BST
// https://leetcode.com/problems/kth-smallest-element-in-a-bst/
// Medium | Java | Accepted 2025-12-01
// Runtime 0 ms | Memory 46.6 MB

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
    int count = 0;
    int ans;
    public int kthSmallest(TreeNode root, int k) {
      recurse(root, k);
      return ans;
    }
    public void recurse(TreeNode root, int k)
    {
        if(root == null)
        {
            return;
        }
        recurse(root.left, k);
        count++;
        if(count==k)
        {
            ans = root.val;
        }
        recurse(root.right, k);
    }
}
