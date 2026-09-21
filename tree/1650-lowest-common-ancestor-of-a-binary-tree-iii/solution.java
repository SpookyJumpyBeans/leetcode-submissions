// 1650. Lowest Common Ancestor of a Binary Tree III
// https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii/
// Medium | Java | Accepted on NeetCode 2026-09-21

/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node parent;
};
*/

/*
class Solution {
    public Node lowestCommonAncestor(Node p, Node q) {
        //BRUTE FORCE SOLUTION: TRY EVERY COMBINATION of p's ancestors and q's ancestors
        // BASE CASE 1: Direct Parent-Child Relationship
        // If one node is the immediate parent of the other, the parent is the LCA.
        if(p.parent == q || q.parent == p) {
            return p.parent == q ? q : p;
        }
        
        // BASE CASE 2: Shared Parent (Siblings)
        // If both nodes have the exact same parent, that shared parent is the LCA.
        if(p.parent == q.parent) {
            return p.parent;
        }
        
        // RECURSIVE BRANCH 1: Move 'p' upwards
        // Hold 'q' in place and move 'p' up to its parent to see if an intersection 
        // exists between p's ancestors and q.
        Node pParent = null;
        if(p.parent != null) {
            pParent = lowestCommonAncestor(p.parent, q);
        }
        
        // RECURSIVE BRANCH 2: Move 'q' upwards
        // Hold 'p' in place and move 'q' up to its parent to see if an intersection
        // exists between q's ancestors and p.
        Node qParent = null;
        if(q.parent != null) {
            qParent = lowestCommonAncestor(p, q.parent);
        }
        
        // COMBINE RESULTS
        // Because of the brute-force nature, one of the branches might hit a dead end (null).
        // We return whichever branch successfully found the intersection.
        return qParent == null ? pParent : qParent;
    }
}

*/

class Solution {
    public Node lowestCommonAncestor(Node p, Node q) {
        Set<Integer> pPath = new HashSet<>(); //Track one of the node's paths all the way up to the root
        //I used Node p
        Node pCur = p;
        Node qCur = q;
        while(pCur!=null) //This loop tracks every node value that p goes through on the path up to the root
        {
            pPath.add(pCur.val);
            pCur = pCur.parent;
        }
        while(qCur!=null) //Now walk q up to the root, it is guaranteed that at some point, it will hit a shared path node with node p
        //This first shared path node that both p and q traverse past is the LCA
        {
            if(pPath.contains(qCur.val))
            {
                return qCur; //Return the LCA
            }
            qCur = qCur.parent;
        }
        return null; //This return statement will never get triggered
    }
}
