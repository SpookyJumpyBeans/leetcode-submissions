// 2471. Minimum Number of Operations to Sort a Binary Tree by Level
// https://leetcode.com/problems/minimum-number-of-operations-to-sort-a-binary-tree-by-level/
// Medium | Java | Accepted 2026-11-15
// Runtime 89 ms | Memory 81.2 MB

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
    public record Node(TreeNode node, int lev){};
    public int minimumOperations(TreeNode root) {
        Queue<Node> bfs = new ArrayDeque<>(); //Do BFS on the tree
        ArrayList<Integer> temp = new ArrayList<>(); //Keeps track of each level's nodes
        bfs.add(new Node(root, 0)); //Start with the root node
        int level = 0; //Keeps track of which level we're currently on
        int count = 0; //Counts the number of swaps needed
        while(!bfs.isEmpty())
        {
            Node curr = bfs.poll();
            if(curr.lev()!=level) //If this is the first node of a new level, process the current temp list to check how many swaps are needed
            {
                Map<Integer, Integer> mapping = new HashMap<>(); //Make a mapping mapping each value in the array to its index
                ArrayList<Integer> sorted = new ArrayList<>(temp); //Sort the current list
                Collections.sort(sorted);
                for(int i = 0; i<temp.size(); i++)
                {
                    mapping.put(temp.get(i), i); //Map each list value to its index in the list
                }
                count += getMin(mapping, temp, sorted); //Add the result of calling the swap method to the global count
                temp.clear(); //Clear the list for the new level
                level++; //Add 1 to the level since we're on the new level
            }
            temp.add(curr.node().val); //Add this node's value to the list
            if(curr.node().left!=null) //Add the children if they exist
            {
                bfs.add(new Node(curr.node().left, curr.lev()+1));
            }
            if(curr.node().right!=null)
            {
                bfs.add(new Node(curr.node().right, curr.lev()+1));
            }
        }
        Map<Integer, Integer> mapping = new HashMap<>(); //Do the check one last time since when the queue is empty, the last level isn't processed
        ArrayList<Integer> sorted = new ArrayList<>(temp);
        Collections.sort(sorted);
        for(int i = 0; i<temp.size(); i++)
        {
            mapping.put(temp.get(i), i);
        }
        count += getMin(mapping, temp, sorted);
        return count;
    }

    public int getMin(Map<Integer, Integer> map, ArrayList<Integer> temp, ArrayList<Integer> sorted)
    {
        int count = 0;
        for(int i = 0; i<temp.size(); i++)
        {
            if(!temp.get(i).equals(sorted.get(i))) //If the current value in the unsorted list doesn't equal the value in the sorted list
            {
                int ind = map.get(sorted.get(i)); //Get the index of the value that should be in the current index
                int t = temp.get(i); //Store the value that's in the current index
                temp.set(i, temp.get(ind)); //Set the value at the current index to the value that should be in the current index
                temp.set(ind, t); //Set the value of the index of the element we just swapped to the value at the current index that we just replaced
                map.put(t, ind); //Update the map for the 2 values that we swapped
                //Essentially just swap their indexes
                map.put(sorted.get(i), i);
                count++; //Add 1 to the count of swaps required
            }
        }
        return count;
    }
}
