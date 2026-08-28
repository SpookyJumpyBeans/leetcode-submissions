// 199. Binary Tree Right Side View
// https://leetcode.com/problems/binary-tree-right-side-view/
// Medium | Java | Accepted 2025-11-28
// Runtime 1 ms | Memory 43.8 MB

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
    public List<Integer> rightSideView(TreeNode root) {
        if(root==null)
        {
            return new ArrayList<Integer>();
        }
        List<Integer> res = new ArrayList<>();
        Queue <TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty())
        {
            int size = queue.size();
            for(int i = 0 ; i<size; i++)
            {
                TreeNode temp = queue.poll();
                if(i==size-1)
                {
                    res.add(temp.val);
                }
                if(temp.left!=null)
                {
                    queue.add(temp.left);
                }
                if(temp.right!=null)
                {
                    queue.add(temp.right);
                }
            }
        }
        return res;
    }
}
