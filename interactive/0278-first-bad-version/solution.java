// 278. First Bad Version
// https://leetcode.com/problems/first-bad-version/
// Easy | Java | Accepted 2022-08-06
// Runtime 17 ms | Memory 38.9 MB

/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

public class Solution extends VersionControl {
    public int firstBadVersion(int m) {
        int first = 1;
        int last = m;
        while(first<last)
        {
            int mid = first+(last-first)/2;
            if(isBadVersion(mid)==false)
            {
                first = mid + 1;
            }
            else
            {
                last = mid;
            }
        }
        return first;
    }
}
/*
   int start = 1;
        int end = m;
    while (start < end) {
        int mid = start + (end-start) / 2;
        if (!isBadVersion(mid)) start = mid + 1;
        else end = mid;            
    }        
    return start;
}
*/
