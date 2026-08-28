// 19. Remove Nth Node From End of List
// https://leetcode.com/problems/remove-nth-node-from-end-of-list/
// Medium | Java | Accepted 2025-11-05
// Runtime 0 ms | Memory 43.5 MB

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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode start = dummy.next;
        ListNode copy = head;
        int count = 0;
        while(copy!=null)
        {
            count++;
            copy = copy.next;
        }
        int stop = count-n-1;
        if(stop==-1)
        {
            return dummy.next.next;
        }
        for(int i = 0; i<stop; i++)
        {
            start = start.next;
        }
        start.next = start.next.next;
        return dummy.next;
    }
}
