// 83. Remove Duplicates from Sorted List
// https://leetcode.com/problems/remove-duplicates-from-sorted-list/
// Easy | Java | Accepted 2022-08-17
// Runtime 1 ms | Memory 43.6 MB

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
        ListNode joe = head;
        while(joe!=null)
        {
            if(joe.next==null)
            {
                break;
            }
            if(joe.next.val==joe.val)
            {
                joe.next = joe.next.next;
            }
else
{
    joe = joe.next;
}
        }
        return head;
    }
}
