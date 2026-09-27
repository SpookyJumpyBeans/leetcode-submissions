// 2390. Removing Stars From a String
// https://leetcode.com/problems/removing-stars-from-a-string/
// Medium | Java | Accepted 2026-09-27
// Runtime 16 ms | Memory 47.8 MB

class Solution {
    public String removeStars(String s) {
        StringBuilder ans = new StringBuilder();
        //Start at the back
        //Every star is a character that we remove
        //First find sequences of *'s
        //Then for the number of *'s we must remove that many characters starting from the end of the * sequence
        //If we encounter more *'s before running out of characters we have to remove, add to the number of chars we must remove
        for(int i = s.length()-1; i>=0; i--) 
        {
            if(s.charAt(i)!='*') //If this character isn't a star, we can simply append it to the string
            //Instead of deleting parts of the string we just move the index to add only the characters we need to add
            {
                ans.append(s.charAt(i));
                continue;
            }
            int numStars = 0;
            while(s.charAt(i)=='*') //While the current is a star, decrement the index and increment the numStars
            {
                numStars++;
                i--;
            }
            while(numStars>0) //Now that we counted the number of contiguous *'s we need to remove that many characters from the string starting at where the continugous * sequence ended
            {
                if(s.charAt(i)!='*') //If the character is a letter, decrease the number of stars we need to remove
                {
                    numStars--;
                }
                else //Else, it's a * which means we need to remove another character
                {
                    numStars++;
                }
                i--; //Decrement the index
            }
            i++; //This is to ensure the next index starts at the next character that wasn't removed
        }
        return ans.reverse().toString(); //Reverse the string since we went from right to left
    }
}
