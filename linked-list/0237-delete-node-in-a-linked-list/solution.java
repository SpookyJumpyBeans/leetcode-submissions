// 237. Delete Node in a Linked List
// https://leetcode.com/problems/delete-node-in-a-linked-list/
// Medium | Java | Accepted 2022-08-16
// Runtime 0 ms | Memory 43.8 MB

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public void deleteNode(ListNode node) {
    node.val = node.next.val;
    node.next = node.next.next;
    
}
}
