// 92. Reverse Linked List II
// https://leetcode.com/problems/reverse-linked-list-ii/
// Medium | C++ | Accepted on NeetCode 2026-09-04

/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode() : val(0), next(nullptr) {}
 *     ListNode(int x) : val(x), next(nullptr) {}
 *     ListNode(int x, ListNode *next) : val(x), next(next) {}
 * };
 */
class Solution {
public:
    ListNode* reverseBetween(ListNode* head, int left, int right) {
        ListNode dummy(0);
        dummy.next = head;
        ListNode* temp = &dummy;
        for(int i = 0; i<left-1; i++)
        {
            temp = temp->next;
        }
        ListNode* curr = temp->next;
        for(int i = 0; i<right-left; i++)
        {
            ListNode* nex = curr->next; //gets next node for curr
            curr->next = nex->next;     //skips the node directly in front of curr
            nex->next = temp->next; //sets the skipped nodes next to the curr
            temp->next = nex; //sets the before node to nex
        }
        return dummy.next;
    }
};
