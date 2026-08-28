// 1486. XOR Operation in an Array
// https://leetcode.com/problems/xor-operation-in-an-array/
// Easy | C++ | Accepted 2022-09-17
// Runtime 2 ms | Memory 5.9 MB

class Solution {
public:
    int xorOperation(int n, int start) {
        int joe[n];
        joe[0] = start;
        int keepTrack = start;
        for(int i = 1; i<n; i++)
        {
            joe[i] = joe[i-1] + 2;
            keepTrack = keepTrack ^ joe[i];
        }
        return keepTrack;
    }
};
