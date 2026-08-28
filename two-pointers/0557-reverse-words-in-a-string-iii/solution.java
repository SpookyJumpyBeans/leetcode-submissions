// 557. Reverse Words in a String III
// https://leetcode.com/problems/reverse-words-in-a-string-iii/
// Easy | Java | Accepted 2022-08-06
// Runtime 1619 ms | Memory 495.9 MB

class Solution {
    public String reverseWords(String s) {
        int start = s.length()-1;
        s+=" ";
        String joe = "";
        int j = s.indexOf(" ");
        if(j<0)
        {
            while(start>=0)
            {
                joe+=s.substring(start, start+1);
                start--;
            }
            return joe;
        }
        else
        {
        while(start>=0)
        {
                        start+=j;
            int k = j;
            while(j-1>=0)
            {
            joe+=s.substring(j-1,j);
                j--;
            }
            if(s.length()>0)
            {
            joe+=s.substring(k,k+1);
            }
            s = s.substring(k+1);
            j = s.indexOf(" ");
        }
            return joe.substring(0,joe.length()-1);
        }
    }
}
