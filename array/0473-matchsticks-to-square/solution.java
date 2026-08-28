// 473. Matchsticks to Square
// https://leetcode.com/problems/matchsticks-to-square/
// Medium | Java | Accepted 2026-08-26
// Runtime 2 ms | Memory 43.4 MB

class Solution {
    boolean[] visited;
    int[] m;
    int target = 0;
    
    public boolean makesquare(int[] matchsticks) {
        Arrays.sort(matchsticks);
        m = new int[matchsticks.length];
        int totalSum = 0;
        
        // Reverse array and calculate sum simultaneously
        for(int i = 0; i < matchsticks.length; i++) {
            m[i] = matchsticks[matchsticks.length - 1 - i];
            totalSum += matchsticks[i];
        }
        
        if (totalSum % 4 != 0) return false;
        target = totalSum / 4;
        
        // O(1) Impossible Piece Check
        if (m[0] > target) return false; 
        
        visited = new boolean[matchsticks.length];
        return recurse(0, 0, 0);
    }

    public boolean recurse(int sidesFormed, int currentLength, int startIndex) {
        if (sidesFormed == 3) return true;
        if (currentLength == target) return recurse(sidesFormed + 1, 0, 0);
        
        for (int i = startIndex; i < m.length; i++) {
            if (!visited[i] && currentLength + m[i] <= target) {
                visited[i] = true;
                
                if (recurse(sidesFormed, currentLength + m[i], i + 1)) return true;
                
                // CRITICAL FIX: Always backtrack before breaking or skipping!
                visited[i] = false;
                
                // 1. "Empty Side" Short-Circuit
                if (currentLength == 0) break;
                
                // 2. Duplicate Skipping
                while (i + 1 < m.length && m[i] == m[i + 1]) {
                    i++; // Fast-forward past identical matchsticks
                }
            }
        }
        return false;
    }
}
