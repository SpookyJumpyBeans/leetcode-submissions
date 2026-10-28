// 3019. Number of Changing Keys
// https://leetcode.com/problems/number-of-changing-keys/
// Easy | Java | Accepted 2026-10-27
// Runtime 5 ms | Memory 45.5 MB

class Solution {
    public int countKeyChanges(String s) {
        int count = 0;
        for(int i = 1; i<s.length(); i++)
        {
            if(!s.substring(i, i+1).toLowerCase().equals(s.substring(i-1, i).toLowerCase()))
            {
                count++;
            }
        }
        return count;
    }
}
