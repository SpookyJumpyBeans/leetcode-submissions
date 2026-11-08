// 103. Binary Tree Zigzag Level Order Traversal
// https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
// Medium | Java | Accepted 2026-11-08
// Runtime 1 ms | Memory 43.2 MB

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
    public record track(TreeNode node, int lev){};
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if(root==null)
        {
            return new ArrayList<>();
        }
        Queue<track> even = new ArrayDeque<>();
        List<List<Integer>> res = new ArrayList<>();
        even.add(new track(root, 0));
        Deque<Integer> temp = new ArrayDeque<>();
        int currLev = 0;
        while(!even.isEmpty())
        {
            track curr = even.poll();
            if(curr.lev()!= currLev)
            {
                currLev++;
                res.add(new ArrayList<>(temp));
                temp = new ArrayDeque<>();
            }
            if(currLev%2==0)
            {
                temp.addLast(curr.node().val);
            }
            if(currLev%2!=0)
            {
                temp.addFirst(curr.node().val);
            }
            if(curr.node().left!=null)
            {
                even.add(new track(curr.node().left, curr.lev()+1));
            }
            if(curr.node().right!=null)
            {
                even.add(new track(curr.node().right, curr.lev()+1));
            }
        }
        res.add(new ArrayList<>(temp));
        return res;
    }
}
