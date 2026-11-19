// 6. Zigzag Conversion
// https://leetcode.com/problems/zigzag-conversion/
// Medium | Java | Accepted 2026-11-11
// Runtime 4 ms | Memory 46 MB

class Solution {
    public String convert(String s, int numRows) {
        if(numRows==s.length() || numRows==1) //Special case for when numRows is the same number as the length of s or if numRows is equal to 1
        //These will both result in the same exact zigzag string as s
        {
            return s;
        }
        StringBuilder temp = new StringBuilder();
        int next = 2*(numRows-1); //This is the start of the next value for each row
        //Each zigzag is going to contian 2 *(numRows-1) values
        //numRows values going down, and then numRows-2 values going back up to the next section
        for(int i = 0; i<numRows; i++)
        {
            //For each row from 0 to numRows-1
            int start = i; //They start with the first character at index i in s
            int count = 1; //This keeps track of where the end of the section will be
            while(start<s.length()) 
            {
                temp.append(s.charAt(start)); //First add the value at the current index
                if(i!=0 && i!=numRows-1 && count*next-i<s.length()) //If i isn't the first or last row, there's going to be another value that we need to add for this row (2 values per section)
                {
                 temp.append(s.charAt(count*next-i)); //This next value is going to be at the end of the current section subtracted by the row number we're currently at
                } 
                start+=next; //Go to the next section's starting value for this row
                count++; //Increment count so we go to the end of the next section
            }
        }
        return temp.toString();
    }
}
