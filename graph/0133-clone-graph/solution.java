// 133. Clone Graph
// https://leetcode.com/problems/clone-graph/
// Medium | Java | Accepted 2026-01-04
// Runtime 24 ms | Memory 44.3 MB

/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

class Solution {
    Map<Node, Node> map = new HashMap<>();
    public Node cloneGraph(Node node) {
        if(node==null)
        {
            return null;
        }
        if(map.containsKey(node))
        {
            return map.get(node);
        }
        Node nodeCopy = new Node(node.val);
        if(!map.containsKey(node))
        {
           map.put(node, nodeCopy);
        }
        for(Node n : node.neighbors)
        {
           Node temp = cloneGraph(n);
           nodeCopy.neighbors.add(temp);
        }
        return nodeCopy;
    }
}
