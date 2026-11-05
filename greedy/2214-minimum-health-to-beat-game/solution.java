// 2214. Minimum Health to Beat Game
// https://leetcode.com/problems/minimum-health-to-beat-game/
// Medium | Java | Accepted 2026-11-04
// Runtime 3 ms | Memory 81.5 MB

class Solution {
    public long minimumHealth(int[] damage, int armor) {
        //Use your armor on the level with the most damage
        long res = 0;
        int max = 0;
        for(int i : damage)
        {
            max = Math.max(max, i);
            res+=i;
        }
        if(max<=armor) //If the level with the most damage is <= your armor, then it will shield you from all the damage
        {
            res-=max;
        }
        else
        {
            res-=armor; //Else it will shield you from armor amounts of damage
        } 
        return res+1; //To end with health greater than 0 add 1 to the end
    }
}
