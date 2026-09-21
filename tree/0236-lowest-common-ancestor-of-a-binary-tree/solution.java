// 236. Lowest Common Ancestor of a Binary Tree
// https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
// Medium | Java | Accepted 2026-09-21
// Runtime 15 ms | Memory 69.9 MB

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null) //If the root is null, return null
        {
            return null;
        }
        if(root.val==p.val || root.val==q.val) //If we've found one of the given nodes, return the node
        {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q); //Traverse the left and right subtrees
        //The idea is that if a subtree has a given node in it, the node will be returned
        //Else, null will be returned, which means we didn't find the target node in the subtree
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if(left!=null && right!=null) //If both left and right subtrees don't return null, that means that the two given nodes were found in THIS root's left and right subtrees
        //This must mean that this node is the lowest common ancestor
        {
            return root;
        }
        //Else, if only one subtree returned a non null result, we want to return this non null result and propogate up the tree
        //In the case where one node is a descendant of itself, when we first reach the node that is the lowest common ancestor, it will return a non null value for the given subtree
        //It won't explore further down to find the other node
        //In this case, the return will propogate all the way up to the original root of the entire tree
        //We will end up with either right or left being non null
        //Return this non null node
        return left == null ? right : left;
    }
}
