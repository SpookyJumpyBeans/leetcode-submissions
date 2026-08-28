// 2029. Stone Game IX
// https://leetcode.com/problems/stone-game-ix/
// Medium | Java | Accepted 2026-08-16
// Runtime 3 ms | Memory 114.8 MB

class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] counts = new int[3];
        for(int i : stones)
        {
            counts[i%3]++;
        }
        boolean flag = true;
        if(counts[0]%2==0)
        {
        if(counts[1]>0 && counts[2]>0)
        {
            return true;
        }
        return false;
        }
        if(counts[1]-counts[2]>2 ||counts[2]-counts[1]>2)
        {
            return true;
        }
        return false;
    }
}
