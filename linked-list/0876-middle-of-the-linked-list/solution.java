// 876. Middle of the Linked List
// https://leetcode.com/problems/middle-of-the-linked-list/
// Easy | Java | Accepted 2022-08-16
// Runtime 0 ms | Memory 42 MB

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
    public ListNode middleNode(ListNode head) {
       ListNode joe = head;
        int count = 0;
       while(head!=null)
       {
           count++;
           head = head.next;
       }
        count/=2;
        while(count>0)
        {
            joe = joe.next;
            count--;
        }
        return joe;
    }
}
