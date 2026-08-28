// 875. Koko Eating Bananas
// https://leetcode.com/problems/koko-eating-bananas/
// Medium | Java | Accepted 2025-10-29
// Runtime 14 ms | Memory 45.3 MB

class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = 0;
        for(int i = 0; i<piles.length; i++)
        {
            right = Math.max(piles[i],right);
        }
        while(left<=right)
        {
            int mid = (left + right)/2;
            long hours = 0;
            for(int j = 0; j<piles.length; j++)
            {
                if(mid>=piles[j])
                {
                    hours++;
                }
                else
                {
                    if(piles[j]%mid==0)
                    {
                        hours += piles[j]/mid;
                    }
                    else
                    {
                        hours += (piles[j]/mid) + 1;
                    }
                }
            }
            if(hours>h)
            {
                left = mid+1;
            }
            else
            {
                right = mid-1;
            }
        }
        return (left + right)/2 + 1;
    }
}
