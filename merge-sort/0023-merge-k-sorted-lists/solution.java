// 23. Merge k Sorted Lists
// https://leetcode.com/problems/merge-k-sorted-lists/
// Hard | Java | Accepted 2025-11-17
// Runtime 2 ms | Memory 46.7 MB

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
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode tempHead = new ListNode();
        ListNode dummy = tempHead;
        for(int i = 0; i<lists.length; i++)
        {
            ListNode head = lists[i];
            while(head!=null)
            {
                tempHead.next = head;
                head = head.next;
                tempHead = tempHead.next;
            }
        }
        return MergeSort(dummy.next);
        
    }

    public ListNode MergeSort(ListNode list)

    {
        if(list==null || list.next==null)
        {
            return list;
        }
        ListNode slow = list;
        ListNode prev = null;
        ListNode fast = list;
        while(fast!=null && fast.next!=null)
        {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;   
        }
        prev.next = null;
        ListNode left = MergeSort(list);
        ListNode right = MergeSort(slow);
        return Merge(left, right);
    }

    public ListNode Merge(ListNode left, ListNode right)
    {
        ListNode dummy = new ListNode();
        ListNode temp = dummy;
        while(left!=null && right!=null)
        {
            if(right.val < left.val)
            {
                temp.next = right;
                right = right.next;
                temp = temp.next;
            }
            else
            {
                temp.next = left;
                left = left.next;
                temp = temp.next;
            }
        }
        temp.next = right == null ? left : right;
        return dummy.next;
    }
}
