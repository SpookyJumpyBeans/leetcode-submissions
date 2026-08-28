// 1386. Cinema Seat Allocation
// https://leetcode.com/problems/cinema-seat-allocation/
// Medium | Java | Accepted 2026-08-19
// Runtime 31 ms | Memory 53.9 MB

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
       Map<Integer, Set<Integer>> map = new HashMap<>();
       for(int[] i : reservedSeats)
       {
        map.computeIfAbsent(i[0], k -> new HashSet<>()).add(i[1]);
       }
       int ans = 0;
       int missingRow = map.size();
       for(Map.Entry<Integer, Set<Integer>> e : map.entrySet())
       {
        Set<Integer> temp = e.getValue();
        if(!temp.contains(2)&&!temp.contains(3)&&!temp.contains(4)&&!temp.contains(5))
        {
            temp.add(4);
            ans++;
        }
        if(!temp.contains(4)&&!temp.contains(5)&&!temp.contains(6)&&!temp.contains(7))
        {
            temp.add(6);
            ans++;
        }
        if(!temp.contains(6)&&!temp.contains(7)&&!temp.contains(8)&&!temp.contains(9))
        {
            ans++;
        }
       }
       ans+=(n-missingRow)*2;
       return ans;
    }
}
