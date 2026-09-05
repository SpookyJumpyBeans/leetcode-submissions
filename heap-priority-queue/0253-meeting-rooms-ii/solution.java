// 253. Meeting Rooms II
// https://leetcode.com/problems/meeting-rooms-ii/
// Medium | java | Accepted on NeetCode 2026-09-04

/**
 * Definition of Interval:
 * public class Interval {
 *     public int start, end;
 *     public Interval(int start, int end) {
 *         this.start = start;
 *         this.end = end;
 *     }
 * }
 */

class Solution {
    public int minMeetingRooms(List<Interval> intervals) {
    int[] start = new int[intervals.size()];
    int[] end = new int[intervals.size()];
    int ind = 0;
    for(Interval interval : intervals)
    {
        start[ind] = interval.start;
        end[ind] = interval.end;
        ind++;
    }
    Arrays.sort(start);
    Arrays.sort(end);
    int s = 0;
    int e = 0;
    int count  = 0; 
    int ans = 0;
    while(e<end.length && s<end.length)
    {
        if(start[s]<end[e])
        {
            count++;
            s++;
        }
        else
        {
            e++;
            count--;
        }
        ans = Math.max(ans, count);
    }
    return ans;
    }

}
