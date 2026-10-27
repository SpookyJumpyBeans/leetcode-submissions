// 445. Add Two Numbers II
// https://leetcode.com/problems/add-two-numbers-ii/
// Medium | Java | Accepted 2026-10-26
// Runtime 3 ms | Memory 45.7 MB

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
        //Idea, reverse both lists, then add the two listnode values that align up until one list is null 
        //Then, add the carry with the resulting listnode values until the other (longer) list is null
        //Then, finally account for the possibility of a carry at the very end
        ListNode rev1 = l1; 
        ListNode prev1 = null; 
        while(rev1!=null) //Reverse the first linked list
        {
            ListNode temp = rev1.next;
            rev1.next = prev1;
            prev1 = rev1;
            rev1 = temp;
        }
        ListNode rev2 = l2;
        ListNode prev2 = null; 
        while(rev2!=null) //Reverse the second linked list
        {
            ListNode temp = rev2.next;
            rev2.next = prev2;
            prev2 = rev2;
            rev2 = temp;
        }
        ListNode ans = null; //This creates the resulting (reversed) list after adding the two linked lists together
        int carry = 0; //Carry to keep track of summations over 10
        ListNode tempp = null; //This is to track the start of the reversed list
        while(prev2!=null && prev1!=null)
        {
            int curr = prev2.val + prev1.val + carry; //Add the two listnode values and the carry
            if(curr<10) //If the value is less than 10
            {
                carry = 0; //Set carry to 0
            }
            if(curr>=10) //If the sum is greater than or equal to 10
            {
                carry = 1; //Set carry to 1
                curr%=10; //Mod curr by 10 so we only keep the digit in the one's position
            }
            if(ans==null) //If this is the first position
            {
                ans = new ListNode(curr); //Set ans to a new listnode with curr as the value
                tempp = ans; //Set the tempp pointer to ans (the start of the reversed resulting list)
                //Since ans is going to be edited to make the resulting list, tempp gets set to the start of ans once and never touched again
            }
            else
            {
                ans.next = new ListNode(curr); //Else, make the new digit node and increment the ans pointer to the new digit
                ans = ans.next;
            }
            prev1 = prev1.next; //Increment both listnode pointers
            prev2 = prev2.next;
        }
        while(prev1!=null || prev2!=null) //If the lists are different lengths, one list will be null and the other won't
        //This finishes the summations for the final part of the unfinished list
        {
            if(prev1!=null)
            {
                int num = prev1.val+carry;
                //Same logic as the previous while loop but checking which list is the one that isn't null
                if(num<10)
                {
                    carry = 0;
                }
                if(num>=10)
                {
                    carry = 1;
                    num%=10;
                }
                ans.next = new ListNode(num);
                ans = ans.next;
                prev1 = prev1.next;
            }
            if(prev2!=null)
            {
                int num = prev2.val+carry;
                if(num<10)
                {
                    carry = 0;
                }
                if(num>=10)
                {
                    carry = 1;
                    num%=10;
                }
                ans.next = new ListNode(num);
                ans = ans.next;
                prev2 = prev2.next;
            }
        }
        if(carry==1) //Now that both lists have been exhausted, check if the final carry is 1
        {
            ans.next = new ListNode(1); //If it is, we have to add an extra node to the very end
            ans = ans.next; //Ans points to the final value (its next value points to nothing)
        }
        ListNode prev = null; //Reverse the final list
        while(tempp!=null)
        {
            ListNode temp = tempp.next;
            tempp.next = prev;
            prev = tempp;
            tempp = temp;
        }
        return prev; //Return the reversed list
    }
}
