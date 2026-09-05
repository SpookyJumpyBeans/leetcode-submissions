// 57. Insert Interval
// https://leetcode.com/problems/insert-interval/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {

    public int[][] insert(int[][] intervals, int[] newInterval) {
        if(intervals.length==0)
        {
            int[][] ansss = new int[1][2];
            ansss[0] = newInterval;
            return ansss;
        }
        int count = 0;
        int[][] ans = new int[1000][2];
        int ind = 0;
        int stop = 0;
        for(int i = 0; i<intervals.length; i++)
        {
            if(intervals[i][1]<newInterval[0])
            {
                ans[ind] = intervals[i];
                ind++;
            }
            if(intervals[i][1]>=newInterval[0])
            {
                stop = i;
                break;
            }
            if(i==intervals.length-1)
            {
                stop = intervals.length;
                break;
            }
        }
        for(int j = stop; j<intervals.length; j++)
        {
            if(intervals[j][0]<=newInterval[1])
            {
                newInterval[0] = Math.min(intervals[j][0], newInterval[0]);
                newInterval[1] = Math.max(intervals[j][1], newInterval[1]);
            }
           if(intervals[j][0]>newInterval[1])
            {
                stop = j;
                break;
            }
            if(j==intervals.length-1)
            {
                stop = intervals.length;
                break;
            }
        }
         ans[ind] = newInterval;
                ind++;
        for(int k = stop; k<intervals.length; k++)
        {
            ans[ind] = intervals[k];
            ind++;
        }   
        int[][] anss = new int[ind][2];
        for(int l = 0; l<anss.length; l++)
        {
            anss[l] = ans[l];
        }
        return anss;
    }
}
