// 102. Binary Tree Level Order Traversal
// https://leetcode.com/problems/binary-tree-level-order-traversal/
// Medium | Java | Accepted 2025-11-28
// Runtime 18 ms | Memory 47 MB

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
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> lo = new ArrayList<List<Integer>>();
        Queue <TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int currentLevel = 0;
        while(!queue.isEmpty()&&root!=null)
        {
            int size = queue.size();
            lo.add(new ArrayList<>());
            System.out.println(size);
            for(int i = 0; i<size; i++)
            {
                TreeNode temp = queue.poll();
                System.out.println(temp.val);
                lo.get(currentLevel).add(temp.val);
                if(temp.left!=null)
                {
                    queue.add(temp.left);
                }
                if(temp.right!=null)
                {
                    queue.add(temp.right);
                }
            }
            currentLevel++;
        }
        return lo;
    }   
}
