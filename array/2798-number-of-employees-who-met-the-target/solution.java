// 2798. Number of Employees Who Met the Target
// https://leetcode.com/problems/number-of-employees-who-met-the-target/
// Easy | Java | Accepted 2026-10-24
// Runtime 1 ms | Memory 43.6 MB

class Solution {
    public int numberOfEmployeesWhoMetTarget(int[] hours, int target) {
        int count = 0;
        for(int i : hours)
        {
            if(i>=target)
            {
                count++;
            }
        }
        return count;
    }
}
