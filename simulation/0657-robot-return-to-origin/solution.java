// 657. Robot Return to Origin
// https://leetcode.com/problems/robot-return-to-origin/
// Easy | Java | Accepted 2026-11-06
// Runtime 11 ms | Memory 45.3 MB

class Solution {
    public boolean judgeCircle(String moves) {
        int x = 0;
        int y = 0;
        for(int i = 0; i<moves.length(); i++)
        {
            if(moves.charAt(i)=='R')
            {
                x++;
            }
            if(moves.charAt(i)=='L')
            {
                x--;
            }
            if(moves.charAt(i)=='U')
            {
                y++;
            }
            if(moves.charAt(i)=='D')
            {
                y--;
            }
        }
        return x == 0 && y == 0;
    }
}
