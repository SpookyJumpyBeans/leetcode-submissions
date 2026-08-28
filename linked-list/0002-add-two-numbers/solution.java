// 2. Add Two Numbers
// https://leetcode.com/problems/add-two-numbers/
// Medium | Java | Accepted 2025-11-12
// Runtime 1 ms | Memory 46.3 MB

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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        int length = 0;
        ListNode dummy = new ListNode(-1);
        ListNode temp = dummy;
        ListNode add1 = l1;
        ListNode add2 = l2;
        while(add1!=null && add2!=null)
        {
            int add = add1.val + add2.val + carry;
            int val = add%10;
            carry = (add-10) >= 0 ? 1 : 0;
            temp.next = new ListNode(val);
            temp = temp.next;
            add1 = add1.next;
            add2 = add2.next;
        }
        if(add1 == null)
        {
            while(add2!=null)
            {
            int add = add2.val + carry;
            int val = add%10;
            carry = (add-10) >= 0 ? 1 : 0;
           temp.next = new ListNode(val);
            temp = temp.next;
            add2 = add2.next;
            }
        }
        if(add2 == null)
        {
            while(add1!=null)
            {
            int add = add1.val + carry;
            int val = add%10;
            carry = (add-10) >= 0 ? 1 : 0;
           temp.next = new ListNode(val);
            temp = temp.next;
            add1 = add1.next;
            }
        }
        if(carry==1)
        {
            temp.next = new ListNode(1);
        }
        return dummy.next;
    }
}
