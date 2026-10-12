// 2326. Spiral Matrix IV
// https://leetcode.com/problems/spiral-matrix-iv/
// Medium | Java | Accepted 2026-10-11
// Runtime 6 ms | Memory 134.3 MB

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
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        //Same logic as the original sprial matrix, just if the head == null, fill that index with -1
        int top = 0;
        int right = n-1;
        int bottom = m-1;
        int left = 0;
        int[][] matrix = new int[m][n];
        while(top<=bottom && left<=right)
        {
            for(int i = left; i<=right; i++)
            {
                if(head==null)
                {
                    matrix[top][i] = -1;
                }
                else
                {
                    matrix[top][i] = head.val;
                    head = head.next;
                }
            }
            top++;
            if(top>bottom) //Also check boundaries after every loop so we break early when the conditions aren't met 
            //If we don't break early the loops further down will overwrite the values we have
            {
                break;
            }
            for(int i = top; i<=bottom; i++)
            {
                if(head==null)
                {
                    matrix[i][right] = -1;
                }
                else
                {
                    matrix[i][right] = head.val;
                    head = head.next;
                }
            }
            right--;
            if(right<left)
            {
                break;
            }
            for(int i = right; i>=left; i--)
            {
                if(head==null)
                {
                    matrix[bottom][i] = -1;
                }
                else
                {
                    matrix[bottom][i] = head.val;
                    head = head.next;
                }
            }
            bottom--;
            if(top>bottom)
            {
                break;
            }
            for(int i = bottom; i>=top; i--)
            {
               if(head==null)
                {
                    matrix[i][left] = -1;
                }
                else
                {
                    matrix[i][left] = head.val;
                    head = head.next;
                }
            }
            left++;
            if(right<left)
            {
                break;
            }
        }
        return matrix;
    }
}
