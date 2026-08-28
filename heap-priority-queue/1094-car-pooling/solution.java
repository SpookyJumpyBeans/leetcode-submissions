// 1094. Car Pooling
// https://leetcode.com/problems/car-pooling/
// Medium | Java | Accepted 2026-08-26
// Runtime 6 ms | Memory 46.4 MB

class Solution {
    public boolean carPooling(int[][] trips, int capacity) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b)->(a[0]-b[0]));
        int end = 0;
        for(int[] t : trips)
        {
            pq.add(new int[]{t[1], t[2], t[0]});
            end = Math.max(t[2], end);
            
        }
        int start = pq.peek()[0];
        int[] prefix = new int[end+1];
            for(int[] trip : trips)
            {
                for(int i = trip[1]; i<trip[2]; i++)
                {
                    prefix[i]+=trip[0];
                }
            }
            for(int i : prefix)
            {
                if(i>capacity)
                {
                    return false;
                }
            }
            return true;
    }
}

//1 7 (9)   2 4 (4)  3 4 (9)   4 5 (7)
