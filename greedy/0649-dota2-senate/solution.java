// 649. Dota2 Senate
// https://leetcode.com/problems/dota2-senate/
// Medium | Java | Accepted 2026-08-28
// Runtime 14 ms | Memory 47.5 MB

class Solution {
    public String predictPartyVictory(String senate) {
        Queue<Integer> direPos = new LinkedList<>();
        Queue<Integer> radPos = new LinkedList<>();
        for(int i = 0; i<senate.length(); i++)
        {
            if(senate.charAt(i)=='R')
            {
                radPos.add(i);
            }
            else
            {
                direPos.add(i);
            }
        }
        while(!direPos.isEmpty() && !radPos.isEmpty())
        {
            if(direPos.peek()<radPos.peek())
            {
                int temp = direPos.poll();
                radPos.poll();
                if(radPos.isEmpty())
                {
                    return "Dire";
                }
                direPos.add(temp+senate.length());
            }
            else
            {
                int temp = radPos.poll();
                direPos.poll();
                if(direPos.isEmpty())
                {
                    return "Radiant";
                }
                radPos.add(temp+senate.length());
            }
        }
        return direPos.isEmpty() ? "Radiant" : "Dire";
    }
}
