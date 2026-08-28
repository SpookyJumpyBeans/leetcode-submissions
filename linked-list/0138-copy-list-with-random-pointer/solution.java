// 138. Copy List with Random Pointer
// https://leetcode.com/problems/copy-list-with-random-pointer/
// Medium | Java | Accepted 2025-11-08
// Runtime 0 ms | Memory 46.8 MB

/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    public Node copyRandomList(Node head) {
        Map<Node, Node> nodeOldtoNew= new HashMap<>();
        Node dummy = new Node(-1);
        Node start = head;
        Node headCopy = dummy;
        while(start!=null)
        {
            Node newNode = new Node(start.val);
            headCopy.next = newNode;
            nodeOldtoNew.put(start, newNode);
            headCopy = headCopy.next;
            start = start.next;
        }
        Node through = head;
        Node throughDum = dummy.next;
        while(through!=null)
        {
            if(through.random==null)
            {
                throughDum.random = null;
            }
            else
            {
                throughDum.random = nodeOldtoNew.get(through.random);
            }
            through = through.next;
            throughDum = throughDum.next;
        }
        return dummy.next;
    }
}
