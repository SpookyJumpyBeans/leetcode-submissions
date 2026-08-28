// 143. Reorder List
// https://leetcode.com/problems/reorder-list/
// Medium | Java | Accepted 2025-11-04
// Runtime 3 ms | Memory 50.4 MB

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public void reorderList(ListNode head) {
        ListNode dummy = new ListNode();
        ListNode start = dummy;
        ListNode copy = head;
        Stack<ListNode> stack = new Stack<>();
        while(copy!=null)
        {
            stack.push(copy);
            copy = copy.next;
        }
        int size = stack.size();
        int popNum = size/2;
        ListNode copy2 = head;
        for(int i = 0; i<popNum; i++)
        {
            start.next = copy2;
            copy2 = copy2.next;
            start = start.next;
            start.next = stack.pop();
            start = start.next;
        }
        if(size%2!=0)
        {
            start.next = copy2;
            start = start.next;
        }
        start.next = null;
        head = dummy.next;
    }
}
