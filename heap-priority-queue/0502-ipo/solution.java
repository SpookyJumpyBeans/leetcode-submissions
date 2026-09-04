// 502. IPO
// https://leetcode.com/problems/ipo/
// Hard | Java | Accepted 2026-09-04
// Runtime 99 ms | Memory 141.5 MB

class Solution {
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int[][] list = new int[profits.length][2];
        for(int i = 0; i<profits.length; i++)
        {
            list[i] = new int[]{capital[i], profits[i]};
        }
        Arrays.sort(list, (a, b) -> Integer.compare(a[0], b[0]));
        int startingCap = w;
        PriorityQueue<Integer> projects = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        int ind = 0;
        while(k-->0)
        {
            while(ind<list.length && startingCap>=list[ind][0])
            {
                projects.add(list[ind][1]);
                ind++;
            }
            if(projects.isEmpty())
            {
                break;
            }
            int proj = projects.poll();
            startingCap+=proj;
        }
        return startingCap;
    }
}
