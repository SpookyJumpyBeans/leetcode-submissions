// 973. K Closest Points to Origin
// https://leetcode.com/problems/k-closest-points-to-origin/
// Medium | Java | Accepted 2025-12-02
// Runtime 28 ms | Memory 55.7 MB

class Solution {
    public class Pair implements Comparable<Pair>
    {
        public double distance;
        public int[] pair;
        public Pair(double dis, int[] pair)
        {
            distance = dis;
            this.pair = pair;
        }
         public int compareTo(Pair other) {
            return (int) Double.compare(this.distance, other.distance);
        }
    }
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<Pair> queue = new PriorityQueue<>();
        Map<Integer, Double> map = new HashMap<>();
        Map<Double, int[]> map2 = new HashMap<>();
        for(int i = 0; i<points.length; i++)
        {
            double res = Math.sqrt(Math.pow(points[i][0], 2) + Math.pow(points[i][1], 2));
            queue.add(new Pair(res, points[i]));
        }
        int[][] ans = new int[k][2];
        for(int i = 0; i<k; i++)
        {
            ans[i] = queue.poll().pair;
        }
        return ans;
    }

}
