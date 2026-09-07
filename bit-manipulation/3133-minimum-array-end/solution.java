// 3133. Minimum Array End
// https://leetcode.com/problems/minimum-array-end/
// Medium | Java | Accepted 2026-09-06
// Runtime 1 ms | Memory 42.6 MB

class Solution {
    public long minEnd(int n, int x) {
        //Essentially, for all the positions of x where there are 0s, we want to fill them with the bits of n-1 starting from right to left
        //Ex: 
        //x = 0101010
        //n-1 = 101
        //Answer: 0111011
        n--; //Make n equal to n-1 immediately
        long ans = x; //Need to make x long to avoid integer overflow
        int c = 0; //Global bit position tracker
        while(n>0)
        {
            int bit = n&1; //Extract the LSB in n-1
            long temp = ans >> c; //Move the ans to the next position we left off at
            while((temp&1)!=0) //while the temp's LSB isn't 0, we want to keep right shifting it
            {
                temp>>=1;
                c++; //We increment our global bit position to skip past all these 1s
            }
            ans |= ((long)bit << c);  //Once we hit the first 0 in temp, we want to shift the LSB of n-1 by the global bit position tracker to align it with ans again
            n>>=1; //Right shift to get to the next bit in n-1
            c++; //Add 1 to the global bit position tracker
        }
        return ans;
    }
}
