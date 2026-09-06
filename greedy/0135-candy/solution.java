// 135. Candy
// https://leetcode.com/problems/candy/
// Hard | Java | Accepted 2026-09-06
// Runtime 82 ms | Memory 54.8 MB

class Solution {
    public int candy(int[] ratings) {
        //My original intuition was to use a priority queue that sorted the children by their ratings while also attaching their position in the array
        //Runtime: O(NlogN)
        PriorityQueue<int[]> ratin = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        for(int i = 0; i<ratings.length; i++){
            ratin.add(new int[]{ratings[i], i}); //Sort the children from lowest to highest rating
        }
        int count = 0;
        int[] candies = new int[ratings.length]; //Keep a candies array to keep track
        while(!ratin.isEmpty()) 
        {
            int[] kid = ratin.poll(); //Poll the current kid
            int rating = kid[0]; //Extract their rating and their index
            int index = kid[1];
            int candy = 0; //The amount of candies to give to the current kid
            if(index+1<ratings.length && ratings[index+1]<rating) //If their rating is larger than the kid to their right, then set the amount of candies to the amount of candies the kid to their right got
            {
                candy = candies[index+1];
            }
            if(index-1>=0 && ratings[index-1]<rating) //If their rating is larger than the kid to their left, then set the amount of candies to whatever's larger, the candy amount or the amount of candies the kid to their left got
            {
                candy = Math.max(candy, candies[index-1]);
            }
            candies[index] = candy+1; //Add 1 since we need to have more candies than the amount of candies associated with our neighbors
            count+=candy+1; //Add that to the global candy count
        }
        return count;
    }
}

//There's an O(N) solution that requires a two pass greedy approach:

/*
class Solution {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        
        // Step 1: Initialize all children with 1 candy
        for (int i = 0; i < n; i++) {
            candies[i] = 1;
        }
        
        // Step 2: Left-to-right pass
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }
        
        // Step 3: Right-to-left pass and calculate total
        int totalCandies = candies[n - 1]; // add the last one beforehand
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
            totalCandies += candies[i];
        }
        
        return totalCandies;
    }
}
*/
