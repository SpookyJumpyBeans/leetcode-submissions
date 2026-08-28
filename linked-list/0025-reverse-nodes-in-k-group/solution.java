// 25. Reverse Nodes in k-Group
// https://leetcode.com/problems/reverse-nodes-in-k-group/
// Hard | Java | Accepted 2025-11-23
// Runtime 5 ms | Memory 46.7 MB

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
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(-1);
        int len = 0;
        ListNode length = head;
        while(length!=null)
        {
            length = length.next;
            len++;
        }   
        int numGroups = len/k;
        ListNode prev = null, curr = head;
        ListNode first = curr;
        int count = 0;
        boolean flag = false;
        while(numGroups-->0)
        {
            int temp = k;
            ListNode tempF = curr;
        while (temp-->0) {
            ListNode nxt = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nxt;
        }
        if(!flag)
        {
            flag = true;
            dummy.next = prev;
        }
        count++;
        if(count>1)
        {
            first.next = prev;
            tempF.next = null;
            System.out.println(first.next.val);
            first = tempF;
        }
        }
        if(len%k!=0)
        {
            first.next = curr;
        }

        return dummy.next;
    }
}
