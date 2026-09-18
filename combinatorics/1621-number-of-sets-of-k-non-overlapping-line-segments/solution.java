// 1621. Number of Sets of K Non-Overlapping Line Segments
// https://leetcode.com/problems/number-of-sets-of-k-non-overlapping-line-segments/
// Medium | Java | Accepted 2026-09-16
// Runtime 179 ms | Memory 135.7 MB

class Solution {
    int[][][] dp;
    int points;
    int numSeg;
    public int numberOfSets(int n, int k) {
        dp = new int[n][k+1][2];
        //3D DP
        //Since we are tracking 3 different states, the current coordinate, the number of segments made thus far, and whether we are currently drawing a segment or not, we need a 3D Cache
        for(int[][] dd : dp)
        {
            for(int[] d : dd)
            {
                Arrays.fill(d, -1);
            }
        }
        points = n;
        numSeg = k;
        return recurse(0, 0, false);
    }

    public int recurse(int coord, int segs, boolean isDrawing)
    {
        if(segs==numSeg && !isDrawing) //Only if the number of segments equalst the desired number of segments and we currently aren't drawing do we return 1 as we've found a valid configuration
        {
            return 1;
        }
        if(coord==points || segs>numSeg) //If we've reached the end of the coordinates or the number of segments we have is too many, then we want to return 0 as it's not a valid configuration
        {
            return 0;
        }
        if(dp[coord][segs][isDrawing?0:1]!=-1) //If we've already explored this branch, return the cached value
        {
            return dp[coord][segs][isDrawing?0:1];
        }
        int numWays = 0;
        if(isDrawing) //If we're currently drawing a segment, 2 options
        {
            int stop = recurse(coord, segs+1, false); //Stop drawing the segment and increment the number fo segments we have (we increment when we stop drawing a segment instead of when we start)
            //Also set isDrawing to false since we just finished this segment
            int cont = recurse(coord+1, segs, true); //Continue drawing the segment, which just means incrementing the coordinate
            numWays += (stop+cont)%1000000007;
        }
        else //If we're currently not drawing a segment
        { 
            int start = recurse(coord+1, segs, true); //We can start drawing the segment by incrementing the coordinate and setting isDrawing to true
            int skip = recurse(coord+1, segs, false); //Skip and create a gap in the drawing by incrementing the coordinate and setting isDrawing to false
            numWays += (start+skip)%1000000007;
        }
        //Add together everything to numWays and modulo by 10^9 + 7 
        return dp[coord][segs][isDrawing ? 0 : 1] = numWays; //Return the cached result
    }
}
