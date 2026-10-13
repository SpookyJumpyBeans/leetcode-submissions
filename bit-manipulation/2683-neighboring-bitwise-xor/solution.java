// 2683. Neighboring Bitwise XOR
// https://leetcode.com/problems/neighboring-bitwise-xor/
// Medium | Java | Accepted 2026-10-12
// Runtime 2 ms | Memory 199.5 MB

class Solution {
    public boolean doesValidArrayExist(int[] derived) {
        // Initialize our running XOR sum with the first element of the derived array
        int start = derived[0];
        
        // Loop through the rest of the elements in the derived array
        for(int i = 1; i < derived.length; i++) {
            // XOR the current element with our running sum.
            // Since derived[i] = original[i] ^ original[i+1], doing this across the 
            // whole array effectively XORs every element from the original array twice.
            start ^= derived[i];
        }
        
        // Because x ^ x = 0, every element in the hypothetical 'original' array cancels itself out.
        // If a valid original array exists, the final XOR sum of all derived elements MUST be 0.
        return start == 0;
    }
}
