// 206. Reverse Linked List
// https://leetcode.com/problems/reverse-linked-list/
// Easy | Java | Accepted 2025-11-04
// Runtime 0 ms | Memory 43.9 MB

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
    public ListNode reverseList(ListNode head) {
       ListNode prev = null;
       ListNode now = head;
       ListNode forw = null;
       while(now!=null)
       {
         forw = now.next;
         now.next = prev;
         prev = now;
         now = forw;
       }
       return prev;
    }
}
