// 2058. Find the Minimum and Maximum Number of Nodes Between Critical Points
// https://leetcode.com/problems/find-the-minimum-and-maximum-number-of-nodes-between-critical-points/
// Medium | Java | Accepted 2026-08-31
// Runtime 4 ms | Memory 106.1 MB

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
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int maxDist = Integer.MIN_VALUE;
        int firstInd = -1;
        int lastInd = -1;
        int prevInd = -1;
        int minDist = Integer.MAX_VALUE;
        ListNode dummyPrev = head;
        ListNode dummyNext = head.next;
        int count = 1;
        while(dummyPrev.next!=null)
        {
            ListNode next = dummyNext.next;
            if(next==null)
            {
                break;
            }
            if(dummyPrev.val<dummyNext.val && dummyNext.val>next.val || (dummyPrev.val>dummyNext.val && dummyNext.val<next.val))
            {
                if(firstInd==-1)
                {
                    firstInd = count;
                }
                prevInd = lastInd;
                lastInd = count;
                if(prevInd!=-1)
                {
                    minDist = Math.min(lastInd-prevInd, minDist);
                }
            }
            dummyPrev = dummyNext;
            dummyNext = dummyNext.next;
            count++;
        }
        if(firstInd==-1 && lastInd==-1 || firstInd==lastInd)
        {
            return new int[]{-1, -1};
        }
        return new int[]{minDist, lastInd-firstInd};
    }
}
