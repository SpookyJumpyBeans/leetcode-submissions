// 860. Lemonade Change
// https://leetcode.com/problems/lemonade-change/
// Easy | Java | Accepted 2026-08-28
// Runtime 3 ms | Memory 72.4 MB

class Solution {
    public boolean lemonadeChange(int[] bills) {
        int[] count = {0, 0, 0};
        for(int i : bills)
        {
            if(i==5)
            {
                count[0]++;
            }
            if(i==10)
            {
                count[1]++;
                if(count[0]<1)
                {
                    return false;
                }
                count[0]--;
            }
            if(i==20)
            {
                count[2]++;
                if(count[1]<1 && count[0]<1)
                {
                    return false;
                }
                if(count[1]>=1 && count[0]>=1)
                {
                    count[1]--;
                    count[0]--;
                    continue;
                }
                if(count[0]<3)
                {
                    return false;
                }
                if(count[0]>=3)
                {
                    count[0]-=3;
                }
            }
        }
        return true;
    }
}
