// 134. Gas Station
// https://leetcode.com/problems/gas-station/
// Medium | Java | Accepted 2026-01-15
// Runtime 2 ms | Memory 119.5 MB

class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int count = 0;
        int start = 0;
        int total = 0;
        for(int i = 0; i<gas.length; i++)
        {
            count+=gas[i]-cost[i];
            total+=gas[i]-cost[i];
            if(count<0)
            {
                count = 0;
                start = i+1;
            }
        }
        return total < 0 ? -1 : start;
    }
}
