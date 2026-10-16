// 369. Plus One Linked List
// https://leetcode.com/problems/plus-one-linked-list/
// Medium | Java | Accepted 2026-10-16
// Runtime 0 ms | Memory 42.4 MB

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
    public ListNode plusOne(ListNode head) {
        //Create a dummy node before the first value of head to account for a carry of 1
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        //We want to find the right most digit that isn't 9
        //That means every digit after this right most digit that isn't 9 must be 9
        //We simply set all the values after this right most digit that isn't 9 to 0 and increment this value by 1
        //Ex: 1399
        //Right most digit that isn't 9 is 3
        //Add 1 to 3 
        //Set all values after 3 to 0
        //Result: 1400
        ListNode rightNonNine = dummy;
        while(head!=null) //Get the rightmost non nine value
        {
            if(head.val!=9)
            {
                rightNonNine = head;
            }
            head = head.next;
        }
        //Increment the value of the rightmost non nine
        rightNonNine.val++;
        rightNonNine = rightNonNine.next; //Now make all values after this value 0
        while(rightNonNine!=null)
        {
            rightNonNine.val = 0;
            rightNonNine = rightNonNine.next;
        }
        return dummy.val == 0 ? dummy.next : dummy; //If the dummy value remains 0, there's no carry so return dummy.next
    }
}
