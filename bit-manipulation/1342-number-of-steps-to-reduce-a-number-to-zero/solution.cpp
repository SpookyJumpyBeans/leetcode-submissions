// 1342. Number of Steps to Reduce a Number to Zero
// https://leetcode.com/problems/number-of-steps-to-reduce-a-number-to-zero/
// Easy | C++ | Accepted 2022-09-17
// Runtime 0 ms | Memory 6 MB

class Solution {
public:
    int numberOfSteps (int num) {
  return num == 0 ? 0 : log2(num) + bitset<32>(num).count();
}
};
