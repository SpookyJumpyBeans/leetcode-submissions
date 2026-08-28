// 1342. Number of Steps to Reduce a Number to Zero
// https://leetcode.com/problems/number-of-steps-to-reduce-a-number-to-zero/
// Easy | Java | Accepted 2022-09-17
// Runtime 1 ms | Memory 41.4 MB

class Solution {
    public int numberOfSteps(int num) {
        int count = 0;
        while(num>0)
        {
            if(num%2==0)
            {
                num/=2;
            }
            else
            {
                num-=1;
            }
            count++;
        }
        return count;
    }
}
