// 2130. Maximum Twin Sum of a Linked List
// https://leetcode.com/problems/maximum-twin-sum-of-a-linked-list/
// Medium | Java | Accepted 2026-10-04
// Runtime 4 ms | Memory 101.2 MB

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
    public int pairSum(ListNode head) {
        //Use Floyd's Algorithm to find the middle of the linked list
        //We then reverse the second half of the linked list and add the nodes that the pointers point to from the first and second half (these are the twin sums)
        ListNode slow = head;
        ListNode fast = head;
        while(fast!=null)
        {
            slow = slow.next;
            fast = fast.next.next;
        }
        //Slow is now at the second half of the list
        //Reverse the second half of the list
        ListNode prev = null;
        ListNode curr = slow;
        while(curr!=null)
        {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;            
        }
        //Now prev is the new head of the reversed second half of the list
        //This lines up with the head of the first half of the list as the twin pairs
        int max = 0;
        while(head!=null && prev!=null)
        {
            max = Math.max(head.val + prev.val, max);
            head = head.next;
            prev = prev.next;
        }
        return max;
    }
}
