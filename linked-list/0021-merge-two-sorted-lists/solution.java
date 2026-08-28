// 21. Merge Two Sorted Lists
// https://leetcode.com/problems/merge-two-sorted-lists/
// Easy | Java | Accepted 2025-11-04
// Runtime 0 ms | Memory 43.9 MB

class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
     ListNode dummy = new ListNode();
     ListNode start = dummy;
     ListNode a = list1;
     ListNode b = list2;
     while(a != null && b != null)
     {
        if(a.val < b.val)
        {
            start.next = a;
            a = a.next;
        }
        else
        {
            start.next = b;
            b = b.next;
        }
        start = start.next;
     }
     start.next = a != null ? a : b;
     return dummy.next;
    }
}
