// 56. Merge Intervals
// https://leetcode.com/problems/merge-intervals/
// Medium | Java | Accepted 2026-08-13
// Runtime 6 ms | Memory 48.7 MB

class Solution {
    public int[][] merge(int[][] intervals) {
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        for(int[] i : intervals)
        {
            minHeap.add(i);
        }
        ArrayList<int[]> ans = new ArrayList<>();
        while(!minHeap.isEmpty())
        {
        int[] interval = minHeap.poll();
        while(!minHeap.isEmpty()&&minHeap.peek()[0]<=interval[1])
        {
            interval = new int[]{interval[0], Math.max(minHeap.peek()[1], interval[1])};
            minHeap.poll();
        }
        ans.add(interval);
        }
        int[][] anss = new int[ans.size()][2];
        for(int i = 0; i<ans.size(); i++)
        {
            anss[i] = ans.get(i);
        }
        return anss;
    }
}
