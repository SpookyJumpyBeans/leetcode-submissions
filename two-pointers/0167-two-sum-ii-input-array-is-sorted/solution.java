// 167. Two Sum II - Input Array Is Sorted
// https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
// Medium | Java | Accepted 2024-09-08
// Runtime 2 ms | Memory 47.5 MB

class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int i = 0;
        int j = numbers.length-1;
        int[] bruh = new int[2];
        while(i<j)
        {
            if(numbers[i]+numbers[j]<target)
            {
                i++;
            }
            else if(numbers[i]+numbers[j]>target)
            {
                j--;
            }
            else
            {
                bruh[0] = i+1;
                bruh[1] = j+1;
                break;
            }
        }
        return bruh;
    }
}
