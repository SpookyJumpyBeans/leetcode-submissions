// 83. Remove Duplicates from Sorted List
// https://leetcode.com/problems/remove-duplicates-from-sorted-list/
// Easy | Java | Accepted 2026-10-11
// Runtime 0 ms | Memory 45.6 MB

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
    public ListNode deleteDuplicates(ListNode head) {
        ListNode temp = head;
        while(head!=null)
        {
            while(head!=null && head.next!=null && head.val==head.next.val) //While the current pointer's node is equal to the next node's value, set the current pointer to skip past the next node
            {
                head.next = head.next.next;
            }
            //Keep doing that until the next node's value isn't the same as the current node's value
            if(head==null)
            {
                break;
            }
            head = head.next; //Set the pointer to the next node
        }
        return temp;
    }
}
