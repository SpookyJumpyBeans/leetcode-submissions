// 2807. Insert Greatest Common Divisors in Linked List
// https://leetcode.com/problems/insert-greatest-common-divisors-in-linked-list/
// Medium | Java | Accepted 2026-08-29
// Runtime 1 ms | Memory 47 MB

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
    public ListNode insertGreatestCommonDivisors(ListNode head) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        dummy = dummy.next;
        while(dummy!=null && dummy.next!=null)
        {
            int curr = dummy.val;
            int next = dummy.next.val;
            ListNode nex = dummy.next;
            int start = Math.max(curr, next);
            int divide = Math.min(curr, next);
            while(divide>0)
            {
                int temp = divide;
                divide = start%temp;
                start = temp;
            }
            ListNode insert = new ListNode(start);
            dummy.next = insert;
            insert.next = nex;
            dummy = nex;
        }
        return head;
    }
}
