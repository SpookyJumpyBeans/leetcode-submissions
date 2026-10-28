// 1642. Furthest Building You Can Reach
// https://leetcode.com/problems/furthest-building-you-can-reach/
// Medium | Java | Accepted 2026-10-28
// Runtime 22 ms | Memory 73 MB

class Solution {
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        PriorityQueue<Integer> jumps = new PriorityQueue<>((a, b) -> Integer.compare(a, b)); //This is a min heap, which keeps track of the l (number of ladders) largest jumps in the heights of two buildings
        //The greedy approach is to dedicate the largest jumps we've seen thus far to ladders and use bricks for all the other smaller jumps
        for(int i = 0; i<heights.length-1; i++)
        {
            if(heights[i]<heights[i+1])
            {
                jumps.add(heights[i+1]-heights[i]); //We add the jump height to the priority queue
                if(jumps.size()>ladders) //If the priority queue is now larger than the number of ladders we have, we need to use bricks now
                {
                    int small = jumps.poll(); //Pop off the smallest jump off the priority queue
                    int left = bricks-small; //Check to see if we have enough bricks to even make the jump
                    if(left<0)
                    {
                        return i; //If we don't, return the building we're currently on
                    }
                    bricks-=small; //Else, we subtract the number of bricks used
                }
            }
            if(i==heights.length-2) //If we made it to the second to last building and we're able to cross into the next building, return the last building index
            {
                return heights.length-1;
            }
        }
        return 0; //Return 0 (The case where there is 1 building)
    }
}
