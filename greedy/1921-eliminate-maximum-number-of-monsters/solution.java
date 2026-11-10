// 1921. Eliminate Maximum Number of Monsters
// https://leetcode.com/problems/eliminate-maximum-number-of-monsters/
// Medium | Java | Accepted 2026-11-09
// Runtime 25 ms | Memory 80.5 MB

class Solution {
    public int eliminateMaximum(int[] dist, int[] speed) {
        double[] arr = new double[dist.length];
        for(int i = 0; i<dist.length; i++)
        {
            arr[i] = (double) dist[i]/speed[i];
        }
        Arrays.sort(arr); //Sort the times it will take the monster to reach the city so we greedily eliminate the monsters that will reach the city fastest first 
        double start = 0;
        int count = 0;
        for(int i = 0; i<arr.length; i++)
        {
            if(start<arr[i]) // While our current time tracked is less than the time it will take the monster to get to the city, we can eliminate the monster
            {
                count++; //Increment the count of monsters eliminated
                start++; //Increment the current time
            }
            else
            {
                return count; //Return the count of monsters if the monster reaches the city <= our current time
            }
        }
        return count;
    }
}
