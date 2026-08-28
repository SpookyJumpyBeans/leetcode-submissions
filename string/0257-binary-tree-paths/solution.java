// 257. Binary Tree Paths
// https://leetcode.com/problems/binary-tree-paths/
// Easy | Java | Accepted 2026-07-16
// Runtime 5 ms | Memory 50 MB

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
    List<String> res = new ArrayList<>();
    public List<String> binaryTreePaths(TreeNode root) {
        recurse(root, "");
        return res;
    }

    public void recurse(TreeNode root, String ans)
    {
        TreeNode temp = root;
        ans+=temp.val;
        ans+="->";
        if(temp.left==null&&temp.right==null)
        {
            ans = ans.substring(0,ans.length()-2);
            res.add(ans);
            return;
        }
        if(temp.left!=null)
        {
        recurse(temp.left, ans);
        }
        if(temp.right!=null)
        {
        recurse(temp.right, ans);
        }
    }
}
