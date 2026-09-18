// 1079. Letter Tile Possibilities
// https://leetcode.com/problems/letter-tile-possibilities/
// Medium | Java | Accepted 2026-09-17
// Runtime 2 ms | Memory 42.9 MB

class Solution {
    String t;
    boolean[] used;
    int count = 0;
    public int numTilePossibilities(String tiles) {
        used = new boolean[tiles.length()]; //Maintain this used array since we're going to be doing permutations
        //Instead of just duplicates, we have to also handle duplicates, which means we need to sort the original string alphabetically
        char[] ranked = tiles.toCharArray();
        Arrays.sort(ranked);
        t = new String(ranked); //Sort the original string
        recurse(0); //Start the recursion
        return count;
    }

    public void recurse(int ind) //This ind parameter is just to check if we've reached the end of the string
    {
        if(ind==t.length()) //If we've hit the end of the string, return
        {
            return;
        }
        for(int i = 0; i<t.length(); i ++) //Else, start from i = 0 everytime since this is a permutation
        {
            if(i-1>=0 && t.charAt(i-1)==t.charAt(i) && !used[i-1]) //This skips duplicates by checking if the previous character is equal to the current character and the used value for the previous character is false 
            //This works because if the character we're on right now is a duplicate of the previous character and the used array stores false for the previous character, then that means we've already evaluated every permutation with the previous character and already marked its used index back to false from true
            {
                continue;
            } 
            if(used[i]) //If this current index is already used, keep incrementing
            {
                continue;
            }
            count++; //Else just add 1 to the count since we're counting permutations of every length
            used[i] = true; //Set the used array to true since we're backtracking
            recurse(ind+1); //Recurse by adding 1 to the index parameter (this basically keeps track of the size of the string without actually simulating the appending of the character to the string)
            used[i] = false; //Backtrack and mark the current index as false
        }
    }
}
