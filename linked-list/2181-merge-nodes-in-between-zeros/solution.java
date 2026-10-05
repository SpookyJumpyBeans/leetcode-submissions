// 2181. Merge Nodes in Between Zeros
// https://leetcode.com/problems/merge-nodes-in-between-zeros/
// Medium | Java | Accepted 2026-10-04
// Runtime 5 ms | Memory 225.6 MB

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
    public ListNode mergeNodes(ListNode head) {
        //Done in Space O(1) without making new list nodes
        //We will basically change the value of every 0 node to the sum of the section that that 0 covers
        //The last 0 node will simply be ignored
        ListNode start = head; //This is the start of the resulting linked list (since the first node is always 0)
        ListNode prev = null; //We only need to keep track of the previous node so we can set the second to last 0 node to point to null since we drop the last 0 node
        while(head!=null)
        {
            int sum = 0;
            ListNode curr = head; //Set the current node to the 0 node the head is at
            if(head.next!=null) //If head.next isn't null, then we can simply add all the values between this starting 0 node and the next 0 node we see
            {
                while(head.next.val!=0)
                {
                    head = head.next;
                    sum+=head.val; //Add the non zero values to sum
                }    
                curr.val = sum; //Set the current 0 node's value to the sum
                curr.next = head.next; //Set the current 0 node's next to the head.next (head stops right before we hit another 0, so head.next will set the current 0 node which is now changed to the sum value to the next 0 node (that we will change into a sum later)
                prev = curr; //Update the prev pointer
            }
            else //If head.next is null, we want to remove this last 0 node since it has no number nodes after it
            {
                prev.next = null; //Set it to null
            }
            head = head.next; //Increment the head pointer to point to the next 0 (or null)
        }
        return start; //Return the start
    }
}
