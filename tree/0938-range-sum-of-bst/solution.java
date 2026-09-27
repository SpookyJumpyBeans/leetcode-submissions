// 938. Range Sum of BST
// https://leetcode.com/problems/range-sum-of-bst/
// Easy | Java | Accepted 2026-09-26
// Runtime 1 ms | Memory 54 MB

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
    public int rangeSumBST(TreeNode root, int low, int high) {
        if(root==null)
        {
            return 0;
        }
        int currSum = 0;
        if(root.val>=low && root.val<=high)
        {
           currSum+=root.val;
        }
        return currSum + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high);
    }
}
