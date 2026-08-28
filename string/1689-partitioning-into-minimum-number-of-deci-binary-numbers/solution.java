// 1689. Partitioning Into Minimum Number Of Deci-Binary Numbers
// https://leetcode.com/problems/partitioning-into-minimum-number-of-deci-binary-numbers/
// Medium | Java | Accepted 2022-08-21
// Runtime 3 ms | Memory 42.5 MB

class Solution {
    public int minPartitions(String n) {
        for(char max = '9'; max>'0';max--)
        {
           if(n.indexOf(max)>=0)
           {
              return max - '0';
           }
            
        }
return 0;
    }
}
