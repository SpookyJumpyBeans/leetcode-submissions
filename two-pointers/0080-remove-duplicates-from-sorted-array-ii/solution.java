// 80. Remove Duplicates from Sorted Array II
// https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/
// Medium | Java | Accepted 2026-09-17
// Runtime 1 ms | Memory 48.6 MB

class Solution {
    public int removeDuplicates(int[] nums) {
        int start = 0;
        int count = 0;
        //My answer has a pointer (start) that walks from the start of the nums array that replaces values based on whether they appear 2 or more times or 1 time
        for(int i = 0; i<nums.length; i++)
        {
            int temp = i;
            int currNum = nums[i];
            while(temp+1<nums.length && nums[temp]==nums[temp+1]) //While this value is the same as the value after, increment the temp counter
            {
                temp++;
            }
            if(temp-i+1>1) //This temp keeps track of how many duplicates there are
            //If there's more than 2, then set the nums at start and start+1 equal to the currNum we're on
            //Increment the start pointer and add 2 to the count of non duplicate nums
            {
                nums[start] = currNum;
                nums[start+1] = currNum;
                start+=2;
                count+=2;
            }
            else if(temp-i+1<=1) //For when the number doesn't have any duplicates
            {
                nums[start] = currNum;
                start++;
                count++;
            } 
            i = temp; //Set the i equal to the last index of the current value's duplicate
        }
        return count; //Return the count
    }
}
