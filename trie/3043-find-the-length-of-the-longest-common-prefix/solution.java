// 3043. Find the Length of the Longest Common Prefix
// https://leetcode.com/problems/find-the-length-of-the-longest-common-prefix/
// Medium | Java | Accepted 2026-08-13
// Runtime 75 ms | Memory 86.5 MB

class Solution {
    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        Set<Integer> set = new HashSet<>();
        for(int i : arr1)
        {
           while(i!=0)
           {
            if(!set.contains(i))
            {
                set.add(i);
            }
            i/=10;
           }
        }
        int longest = 0;
        for(int j : arr2)
        {
          while(j!=0)
          {
            if(set.contains(j))
            {
                longest = Math.max(longest, String.valueOf(j).length());
                break;
            }
            j/=10;
          }
        }
    
        return longest;
    }
}
