// 141. Linked List Cycle
// https://leetcode.com/problems/linked-list-cycle/
// Easy | Java | Accepted 2025-11-04
// Runtime 0 ms | Memory 46.8 MB

/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(fast!=null && fast.next!=null)
        {
            slow = slow.next;
            fast = fast.next.next;
            if(slow==fast)
            {
                return true;
            }
        }
        return false;
    }
}
