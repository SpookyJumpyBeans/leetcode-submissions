// 2579. Count Total Number of Colored Cells
// https://leetcode.com/problems/count-total-number-of-colored-cells/
// Medium | Java | Accepted 2026-11-09
// Runtime 4 ms | Memory 40.9 MB

class Solution {
    public long coloredCells(int n) {    
    long temp = 1;
    for(int i = 1; i<n; i++)
    {
        temp += 4 * i;
    }
    return temp;
    }
}
