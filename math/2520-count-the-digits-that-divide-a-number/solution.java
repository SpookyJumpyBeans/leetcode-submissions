// 2520. Count the Digits That Divide a Number
// https://leetcode.com/problems/count-the-digits-that-divide-a-number/
// Easy | Java | Accepted 2026-10-27
// Runtime 0 ms | Memory 41.7 MB

class Solution {
    public int countDigits(int num) {
        int temp = num;
        int count = 0;
        while(temp>0)
        {
            int curr = temp%10;
            if(num%curr==0)
            {
                count++;
            }
            temp/=10;
        }
        return count;
    }
}
