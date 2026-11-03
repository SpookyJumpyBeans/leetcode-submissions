// 2381. Shifting Letters II
// https://leetcode.com/problems/shifting-letters-ii/
// Medium | Java | Accepted 2026-11-03
// Runtime 6 ms | Memory 112.8 MB

class Solution {
    public String shiftingLetters(String s, int[][] shifts) {
        //Use difference array
        //First find the difference array of the shifts
        int[] arr = new int[s.length()+1];
        for(int[] ss : shifts)
        {
            int start = ss[0];
            int end = ss[1]+1;
            arr[start]+=ss[2]==0 ? -1 : 1; //If the shift is 0, subtract 1 since we move back, else add 1
            arr[end]+=ss[2]==0 ? 1 : -1; //Do the opposite for the ending of the shift
        }
        int[] res = new int[s.length()]; //Now, apply the shifts to all the characters in the string
        res[0] = ((s.charAt(0)-'a')+arr[0])%26; //First standardize the characters to match indices 0-25 by subtracting 'a'
        //Then add the shift from the difference array
        //Mod by 26 to cover for shifts that wrap around from z to a
        if(res[0]<0) //For shifts that wrap behind from a to z, simply add 26 if the result is negative
        {
            res[0]+=26;
        }
        int curr = arr[0]; //Get the running shift total
        for(int i = 1; i<s.length(); i++) //Do the same for the entire difference array (build the prefix array)
        {
            curr+=arr[i]; //Add the shift at the current index to the running shift total to update the shift amount 
            res[i] = ((s.charAt(i)-'a')+curr)%26; //Do the same shifting and wrapping for every character in the string
            if(res[i]<0)
            {
                res[i]+=26;
            }
        }
        char[] temp = new char[s.length()];
        for(int i = 0; i<res.length; i++)
        {
            temp[i] = (char)(res[i]+'a'); //Convert the int array back to characters by adding 'a'
        }
        return String.valueOf(temp); //Return the char array as a string
    }
}
