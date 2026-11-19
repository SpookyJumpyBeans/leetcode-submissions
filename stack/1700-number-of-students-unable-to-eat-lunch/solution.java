// 1700. Number of Students Unable to Eat Lunch
// https://leetcode.com/problems/number-of-students-unable-to-eat-lunch/
// Easy | Java | Accepted 2026-11-18
// Runtime 0 ms | Memory 42.9 MB

class Solution {
    public int countStudents(int[] students, int[] sandwiches) {
        int c0 = 0;
        int c1 = 0;
        for(int i : students)
        {
            if(i==0)
            {
                c0++;
            }
            else
            {
                c1++;
            }
        }
        int n = sandwiches.length;
        for(int i = 0; i<sandwiches.length; i++)
        {
            if(sandwiches[i]==0)
            {
                if(c0<=0)
                {
                    return n - i;
                }
                c0--;
            }
            else
            {
                if(c1<=0)
                {
                    return n - i;
                }
                c1--;
            }
        }
        return 0;
    }
}
