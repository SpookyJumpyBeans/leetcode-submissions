// 1762. Buildings With an Ocean View
// https://leetcode.com/problems/buildings-with-an-ocean-view/
// Medium | Java | Accepted on NeetCode 2026-09-20

class Solution {
    public int[] findBuildings(int[] heights) {
        //Start from the very last building and work from right to left
        //Keep track of the tallest building seen thus far
        //As long as the current building is taller than the tallest building seen thus far, the current building will have a clear view
        int currMax = heights[heights.length-1]; //Set it to the last building, since this building by default will have a clear view
        ArrayList<Integer> indices = new ArrayList<>();
        indices.add(heights.length-1); //Add the last index to the arraylist of visible indices
        for(int i = heights.length-2; i>=0; i--)
        {
            if(heights[i]>currMax) //As long as this building is taller than the tallest building seen thus far, we can add its index to the arraylist at position 0 to sort it
            {
                indices.add(0, i);
                currMax = heights[i]; //Update the tallest building seen thus far
            }
        }
        int[] ans = new int[indices.size()]; //Transfer from arraylist to array
        for(int i = 0; i<indices.size(); i++)
        {
            ans[i] = indices.get(i);
        }
        return ans;
    }
}
