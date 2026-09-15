// 3414. Maximum Score of Non-overlapping Intervals
// https://leetcode.com/problems/maximum-score-of-non-overlapping-intervals/
// Hard | Java | Accepted 2026-09-14
// Runtime 323 ms | Memory 171.5 MB

import java.util.*;

class Solution {
    class Result {
        long score;
        List<Integer> indices;
        public Result(long s, List<Integer> i) {
            score = s;
            indices = i;
        }
    }
    
    Result[][] dp;
    List<List<Integer>> interval;
    
    public int[] maximumWeight(List<List<Integer>> intervals) {
        interval = intervals;
        for (int i = 0; i < interval.size(); i++) {
            interval.get(i).add(i);
        }
        dp = new Result[interval.size()][5];
        interval.sort((a, b) -> Integer.compare(a.get(0), b.get(0)));
        
        List<Integer> bestIndices = recurse(0, 0).indices;
        
        // Convert List<Integer> to int[]
        int[] result = new int[bestIndices.size()];
        for (int i = 0; i < bestIndices.size(); i++) {
            result[i] = bestIndices.get(i);
        }
        Arrays.sort(result);
        return result;
    }   
    
    public Result recurse(int ind, int count) {
        if (count == 4 || ind == interval.size()) {
            return new Result(0, new ArrayList<>());
        }
        if (dp[ind][count] != null) {
            return dp[ind][count];
        } 
        
        Result skip = recurse(ind + 1, count);
        
        int l = ind + 1;
        int r = interval.size() - 1;
        int ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (interval.get(mid).get(0) > interval.get(ind).get(1)) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        
        long newScore;
        List<Integer> newIndices = new ArrayList<>();

        if (ans != -1) {
            Result take = recurse(ans, count + 1);
            newScore = take.score + interval.get(ind).get(2);
            newIndices.add(interval.get(ind).get(3));
            newIndices.addAll(take.indices);
        } else {
            newScore = interval.get(ind).get(2);
            newIndices.add(interval.get(ind).get(3));
        }

        Result choose = new Result(newScore, newIndices);
        
        if (choose.score > skip.score) {
            return dp[ind][count] = choose;
        } else if (choose.score < skip.score) {
            return dp[ind][count] = skip;
        }
        
        // Tie-breaker: pick lexicographically smaller indices list
        return dp[ind][count] = compareLists(choose.indices, skip.indices) < 0 ? choose : skip;
    }
    
    private int compareLists(List<Integer> a, List<Integer> b) {
        Collections.sort(a);
        Collections.sort(b);
        int size = Math.min(a.size(), b.size());
        for (int i = 0; i < size; i++) {
            int cmp = Integer.compare(a.get(i), b.get(i));
            if (cmp != 0) return cmp;
        }
        return Integer.compare(a.size(), b.size());
    }
}
