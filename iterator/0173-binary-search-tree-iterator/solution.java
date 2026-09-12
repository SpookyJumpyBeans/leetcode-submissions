// 173. Binary Search Tree Iterator
// https://leetcode.com/problems/binary-search-tree-iterator/
// Medium | Java | Accepted 2026-09-11
// Runtime 17 ms | Memory 50.4 MB

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
class BSTIterator {
    //This method adds stores the in order traversal in a list
    //Then just uses a pointer to go to the next index in the list
    List<Integer> inOrderTraversal = new ArrayList<>();
    int pointer = 0;
    public BSTIterator(TreeNode root) {
        dfs(root);
    }
    
    public void dfs(TreeNode root)
    {
        if(root==null)
        {
            return;
        }
        dfs(root.left);
        inOrderTraversal.add(root.val);
        dfs(root.right);
    }
    public int next() {
        if(hasNext())
        {
            return inOrderTraversal.get(pointer++);
        }
        return -1;
    }
    
    public boolean hasNext() {
        return pointer<inOrderTraversal.size() ? true : false;
    }
}
*/

class BSTIterator {
   //This is the method using a stack to simulate the traversal, and also adheres to what an iterator is, being able to stop at every step, not just going through the entire recursive tree
    Stack<TreeNode> iterator = new Stack<>();
    public BSTIterator(TreeNode root) {
        addLeft(root); //We start by adding the all the elements to the left of the root node to a stack (the root node will be at the bottom of the stack)
    }
    
    public void addLeft(TreeNode root)
    {
        while(root!=null) //While the node has left children
        {
            iterator.push(root); //We push the current node to the stack and then traverse to its left child
            root = root.left;
        }
    }
    public int next() {
        TreeNode curr = iterator.pop(); //The top of the stack will have the leftest node, so we just pop from the stack 
        addLeft(curr.right); //Since the stack is the traversal of the left nodes, popping from the stack is the root, we now need to traverse the right side to do in order traversal
        //Call addLeft on the popped node's right children, since we're traversing the right side for in order traversal
        //We will then add all the leftmost children of this right subtree including the root onto the stack and do inorder again
        return curr.val;
    }
    
    public boolean hasNext() {
        return iterator.size() > 0; //If the stack has elements
    }
}
/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */
