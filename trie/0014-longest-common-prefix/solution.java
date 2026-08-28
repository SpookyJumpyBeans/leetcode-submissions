// 14. Longest Common Prefix
// https://leetcode.com/problems/longest-common-prefix/
// Easy | Java | Accepted 2025-10-02
// Runtime 3 ms | Memory 43.3 MB

class Solution {
    public String longestCommonPrefix(String[] strs) {
        int minLen = 100000;
        for(int i = 0; i<strs.length; i++)
        {
            minLen = Math.min(strs[i].length(), minLen);
        }
        String[][] arr = new String[strs.length][minLen];
        for(int j = 0; j<strs.length; j++)
        {
            String temp = strs[j];
            for(int k = 0; k<minLen; k++)
            {
                if(k<temp.length())
                {
                    arr[j][k] = temp.substring(k,k+1);
                }
            }
        }
        String result = "";
        for(int z=0; z<minLen;z++){
            boolean same = true;
            String letter = "";
            letter = arr[0][z];
            for(int g = 1; g<strs.length;g++){
                if(!letter.equals(arr[g][z])){
                    same = false;
                    break;
                }

            } 
            if(same == false){
                break;
            }
            result += letter;

        }
        return result;
    }
}
