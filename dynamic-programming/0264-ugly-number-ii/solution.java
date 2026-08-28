// 264. Ugly Number II
// https://leetcode.com/problems/ugly-number-ii/
// Medium | Java | Accepted 2026-02-07
// Runtime 55 ms | Memory 46.9 MB

class Solution {
    public int nthUglyNumber(int n) {
        PriorityQueue<Long> heap = new PriorityQueue<>();
        Set<Long> seen = new HashSet<>();

        heap.add(1L);
        seen.add(1L);

        long ugly = 1;

        for (int i = 0; i < n; i++) {
            ugly = heap.poll();

            if (seen.add(ugly * 2)) heap.add(ugly * 2);
            if (seen.add(ugly * 3)) heap.add(ugly * 3);
            if (seen.add(ugly * 5)) heap.add(ugly * 5);
        }

        return (int) ugly;
    }
}
