// 1871. Jump Game VII
// https://leetcode.com/problems/jump-game-vii/
// Medium | Java | Accepted 2026-09-03
// Runtime 14 ms | Memory 51.1 MB

class Solution {
    public boolean canReach(String s, int minJump, int maxJump) {
        if(s.charAt(s.length()-1)=='1'||s.charAt(0)=='1')
        {
            return false;
        }
        Queue<Integer> bfs = new LinkedList<>();
        bfs.add(0);
        int farthestReached = 0;
        while(!bfs.isEmpty())
        {
            int ind = bfs.poll();
            if(ind==s.length()-1)
            {
                return true;
            }
            for(int i = Math.max(ind+minJump, farthestReached+1); i<=Math.min(ind+maxJump, s.length()-1); i++)
            {
                if(s.charAt(i)=='0')
                {
                    bfs.add(i);
                }
            }
            farthestReached = Math.min(ind+maxJump, s.length()-1);
        }
        return false;
    }
}
