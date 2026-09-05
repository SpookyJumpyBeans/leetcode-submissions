// 1143. Longest Common Subsequence
// https://leetcode.com/problems/longest-common-subsequence/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {
    String t1;
    String t2;
    int[][] strings;
    public int longestCommonSubsequence(String text1, String text2) {
        t1 =  text2;
        t2 = text1;
        strings = new int[text2.length()][text1.length()];
        for(int i = 0; i<strings.length; i++)
        {
            for(int j = 0; j<strings[0].length; j++)
            {
                strings[i][j] = -1;
            }
        }
        return recurse(0, 0);
    }
    public int recurse(int ind1, int ind2)
    {
        if(ind1 >= t1.length() || ind2 >= t2.length())
        {
            return 0;
        }
         System.out.println(t1.charAt(ind1) + " " + t2.charAt(ind2));
         System.out.println(strings[ind1][ind2]);
        if(strings[ind1][ind2]>-1)
        {
            return strings[ind1][ind2];
        }
        if(t1.charAt(ind1)==t2.charAt(ind2))
        {
            strings[ind1][ind2] =  1 + recurse(ind1+1, ind2+1);
        }
        else
        {
            strings[ind1][ind2] = Math.max(recurse(ind1, ind2+1), recurse(ind1+1, ind2));
        }
        return strings[ind1][ind2];
    }
}
