// 1720. Decode XORed Array
// https://leetcode.com/problems/decode-xored-array/
// Easy | Java | Accepted 2022-09-17
// Runtime 1 ms | Memory 43.5 MB

class Solution {
    public int[] decode(int[] encoded, int first) {
        int[] arr = new int[encoded.length+1];
        arr[0] = first;
        for(int i = 1; i<=encoded.length; i++)
        {
            arr[i] = arr[i-1] ^ encoded[i-1];
        }
        return arr;
    }
}
