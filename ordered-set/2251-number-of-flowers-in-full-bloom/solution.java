// 2251. Number of Flowers in Full Bloom
// https://leetcode.com/problems/number-of-flowers-in-full-bloom/
// Hard | Java | Accepted 2026-08-11
// Runtime 38 ms | Memory 113.7 MB

class Solution {
    int[] s;
    int[] e;
    public int[] fullBloomFlowers(int[][] flowers, int[] people) {
        //KEY OBSERVATION: The number of flowers at any given time is equal to the number of flowers that have already bloomed - the number of flowers that have wilted
        int[] starts = new int[flowers.length]; //Make two arrays, one tracking the starts and one tracking the ends
        int[] ends = new int[flowers.length];
        for(int i = 0; i<flowers.length; i++)
        {
            starts[i] = flowers[i][0]; 
            ends[i] = flowers[i][1];
        }
        Arrays.sort(starts); //Sort them for binary search
        Arrays.sort(ends);
        s = starts;
        e = ends;
        int[] ans = new int[people.length]; //Answer array
        int ind = 0;
        for(int j : people) //
        {
            int amtBegin = binarySearchBegin(j);
            int amtEnd = binarySearchEnd(j);
            ans[ind] = amtBegin-amtEnd;

            ind++;
        }
        return ans;
    }

    public int binarySearchBegin(int targ)
    {
        if(targ>s[s.length-1])
        {
            return s.length;
        }
        int l = 0;
        int r = s.length-1;
        int ans = -1;
        while(l<=r)
        {
            int mid = (l+r)/2;
            if(s[mid]>targ)
            {
                r = mid-1;
            }
            else if(s[mid]<=targ)
            {
                l = mid+1;
                ans = mid;
            }
        }
        return ans+1;
    }
    public int binarySearchEnd(int targ)
    {
        if(targ<e[0])
        {
            return 0;
        }
        int l = 0;
        int r = e.length-1;
        int ans = -1;
        while(l<=r)
        {
            int mid = (l+r)/2;
            if(e[mid]<targ)
            {
                l = mid+1;
                ans = mid;
            }
            else if(e[mid]>=targ)
            {
                r = mid-1;
            }
        }
        return ans+1;
    }
}
