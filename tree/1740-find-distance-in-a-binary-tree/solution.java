// 1740. Find Distance in a Binary Tree
// https://leetcode.com/problems/find-distance-in-a-binary-tree/
// Medium | Java | Accepted 2026-10-31
// Runtime 1 ms | Memory 47.9 MB

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
 /*
class Solution {
    List<String> paths = new ArrayList<>();
    public int findDistance(TreeNode root, int p, int q) {
        //My solution finds the aboslute paths to both nodes then gets the distances between them based on differences in the paths
        dfs(root, p, new StringBuilder());
        dfs(root, q, new StringBuilder());
        String pp = paths.get(0);
        String qq = paths.get(1);
        int diff = 0;
        for(int i = 0; i<Math.max(pp.length(), qq.length()); i++)
        {
            if(i>=pp.length() && i<qq.length())
            {
                diff++;
            }
            else if(i>=qq.length() && i<pp.length())
            {
                diff++;
            }
            else if(pp.charAt(i)!=qq.charAt(i))
            {
                diff+=(Math.min(pp.length(), qq.length())-i)*2;
                i = Math.min(pp.length(), qq.length())-1;
            }
        }
        return diff;
    }

    public void dfs(TreeNode root, int targ, StringBuilder path)
    {
        if(root==null)
        {
            return;
        }
        if(root.val==targ)
        {
            paths.add(path.toString());
            return;
        }
        path.append("L");
        dfs(root.left, targ, path);
        path.setLength(path.length()-1);
        path.append("R");
        dfs(root.right, targ, path);
        path.setLength(path.length()-1);
    }
}
*/

class Solution {
    public int findDistance(TreeNode root, int p, int q) {
        //Find the LCA of both nodes
        //Find the distance from the LCA to each node
        if(p==q)
        {
            return 0;
        }
        TreeNode lca = getLCA(root, p, q);
        return dist(lca, p) + dist(lca, q);
    }

    public TreeNode getLCA(TreeNode root, int p, int q)
    {
        if(root == null || root.val==p || root.val==q)
        {
            return root;
        }
        TreeNode left = getLCA(root.left, p, q);
        TreeNode right = getLCA(root.right, p, q);
        if(left!=null && right!=null)
        {
            return root;
        }
        return right == null ? left : right;
    }

    public int dist(TreeNode lca, int targ)
    {
        if(lca==null)
        {
            return 99999;
        }
        if(lca.val==targ)
        {
            return 0;
        }
        int left = 1 + dist(lca.left, targ);
        int right = 1 + dist(lca.right, targ);
        return Math.min(left, right);
    }
}
