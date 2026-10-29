// 645. Set Mismatch
// https://leetcode.com/problems/set-mismatch/
// Easy | Java | Accepted 2026-10-28
// Runtime 18 ms | Memory 47.7 MB

class Solution {
    public int[] findErrorNums(int[] nums) {
        Arrays.sort(nums); //First sort the array
        List<Integer> missing = new ArrayList<>(); //This will hold all the values minus the repeated one
        //We essentially do 2 passes
        //The first pass is to find the duplicate and the second is to find the missing value
        int start = 1;
        int mis = 1;
        int dupe = 1;
        for(int i = 0; i<nums.length;i++)
        {
            if(i<nums.length-1 && nums[i]==nums[i+1]) //If two adjacent numbers are the same, we've found the duplicate
            {
                dupe = nums[i];
            }
            else
            {
                missing.add(nums[i]); //Else, add the non duplicate sorted value to the arraylist
            }
        }
        missing.add(Integer.MAX_VALUE); //At the end, add a dummy max value value in the case that the missing value is the last value
        for(int i = 0; i<missing.size(); i++)
        {
            if(start!=missing.get(i)) //Since the first missing value will mismatch with what value we are currently at, we can break immediately after finding a mismatch 
            //Start represents the value we should have at that index
            {
                mis = start;
                break;
            }
            start++;
        }
        return new int[]{dupe, mis}; 

    }
}
