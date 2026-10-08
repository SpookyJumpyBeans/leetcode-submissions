// 99. Recover Binary Search Tree
// https://leetcode.com/problems/recover-binary-search-tree/
// Medium | Java | Accepted 2026-10-07
// Runtime 5 ms | Memory 46.7 MB

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
    List<TreeNode> traverse = new ArrayList<>();
    public void recoverTree(TreeNode root) {
        //Made a list of the inorder traversal
        //Then made a list of the sorted traversal 
        //An inorder traversal of a BST should always be sorted, so just find the two nodes in the inorder traversal that differ from the sorted traversal and swap their values
       recurse(root); 
       List<TreeNode> sorted = new ArrayList<>(traverse);
       Collections.sort(sorted, (a, b) -> Integer.compare(a.val, b.val));
       TreeNode first = null;
       TreeNode second = null;
       for(int i = 0; i<traverse.size(); i++)
       {
            if(first==null && traverse.get(i).val!=sorted.get(i).val) //If this is the first value that differs
            {
                first = traverse.get(i);
            }
            else if(first!=null && second==null && traverse.get(i).val!=sorted.get(i).val) //If this is the second value that differs
            {
                second = traverse.get(i);
                break;
            }
       }
       int temp = first.val; //Swap the two values
       first.val = second.val;
       second.val = temp;
    }

    public void recurse(TreeNode root) //Inorder traversal
    {
        if(root==null)
        {
            return;
        }
        recurse(root.left);
        traverse.add(root);
        recurse(root.right);
    }
}
