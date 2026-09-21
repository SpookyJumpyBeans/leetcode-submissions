// 3208. Alternating Groups II
// https://leetcode.com/problems/alternating-groups-ii/
// Medium | Java | Accepted 2026-09-20
// Runtime 5 ms | Memory 95.9 MB

class Solution {
    public int numberOfAlternatingGroups(int[] colors, int k) {
        int[] wrapAround = new int[colors.length*2]; //Make an array that appends the original array to the end of the original array
        for(int i = 0; i<wrapAround.length; i++)
        {
            wrapAround[i] = colors[i%colors.length];
        }
        int count = 1; //Count keeps track of the length of the current streak of alternating colors
        //Start count at 1 since a singular color is a streak of 1
        int ans = 0; //Answer starts at 0
        for(int i = 1; i<colors.length+k-1; i++) //The last valid window is from index n-1 to n-1+(k-1), which is n+k-2
        //So set the end of our alternating color check loop to stop right after the end of the last valid window (n+k-2)
        {
            if(wrapAround[i-1]!=wrapAround[i]) //If two adjacent colors are opposite, extend the current length of alternating colors
            {
                count++;
            }
            if(wrapAround[i-1]==wrapAround[i]) //If they are the same, restart the count at 1
            { 
                count = 1;
            }
            if(count>=k) //If the current length of alternating colors is >= k, then we can add 1 to the answer
            //From here, every single color we add to the streak of alternating colors adds 1 to the ans
            //This simulates the sliding window without having to check every window from scratch
            {
                ans++;
            }
        }
        return ans;
    }
}
