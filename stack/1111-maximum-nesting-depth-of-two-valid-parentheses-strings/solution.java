// 1111. Maximum Nesting Depth of Two Valid Parentheses Strings
// https://leetcode.com/problems/maximum-nesting-depth-of-two-valid-parentheses-strings/
// Medium | Java | Accepted 2026-10-02
// Runtime 2 ms | Memory 45.5 MB

class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        //Intuition
        //To minimize the maximum nesting depth of the two parentheses strings, we want to keep the difference in depth between the two strings as small as possible
        //This means we should alternate every time one string is a depth of 1 greater than the other 
        int depthA = 0;
        int depthB = 0;
        int prev = 0;
        int[] arr = new int[seq.length()];
        for(int i = 0; i<seq.length(); i++)
        {
            if(depthA>depthB) //If the depth of the first string > depth of the second string
            { 
                if(seq.charAt(i)=='(') //We want to dedicate this new parentheses to the second string to balance out the depth of the two strings
                {
                    arr[i] = 1;
                    depthB++;
                }                   
                if(seq.charAt(i)==')') //We want to close the parenthese belonging to the first string and decrease the depth of the first string
                {
                    arr[i] = 0;
                    depthA--;
                }
            }
            else //Do the opposite if the depth of the first string < depth of the second string
            {
                if(seq.charAt(i)=='(')
                {
                    arr[i] = 0;
                    depthA++;
                }                   
                if(seq.charAt(i)==')')
                {
                    arr[i] = 1;
                    depthB--;
                }
            }
        }
        return arr; //return the arr
    }
}
