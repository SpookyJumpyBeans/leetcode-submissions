// 1608. Special Array With X Elements Greater Than or Equal X
// https://leetcode.com/problems/special-array-with-x-elements-greater-than-or-equal-x/
// Easy | Java | Accepted 2026-09-26
// Runtime 2 ms | Memory 42.8 MB

class Solution {
    public int specialArray(int[] nums) {
    for(int i = 0; i<=nums.length; i++) //Nums is max length 100
    //So we only have to loop from possible candidates (0 to nums.length)
    {
        int count = 0;
        for(int j : nums)
        {
            if(j>=i) //If this number is >= the x value we're testing
            {
                count++; //Increment count
            }
        }
        if(count==i) //If the count equals the x value return immediately
        {
            return i;
        }
    }
    return -1; //Return -1
    }
}
