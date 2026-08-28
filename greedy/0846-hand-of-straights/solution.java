// 846. Hand of Straights
// https://leetcode.com/problems/hand-of-straights/
// Medium | Java | Accepted 2026-01-29
// Runtime 45 ms | Memory 48.6 MB

class Solution {
    public boolean isNStraightHand(int[] hand, int groupSize) {
        if(hand.length%groupSize!=0)
        {
            return false;
        }
        Map<Integer, Integer> map = new HashMap<>();
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for(int i = 0; i<hand.length; i++)
        {
            if(!map.containsKey(hand[i]))
            {
                map.put(hand[i], 0);
                q.add(hand[i]);
            }
            map.put(hand[i], map.get(hand[i])+1);
        }
        while(!q.isEmpty())
        {
            int start = q.poll();
            map.put(start, map.get(start)-1);
                for(int i = 0; i<groupSize-1; i++)
                {
                    if(!map.containsKey(start+i+1) || (map.containsKey(start+i+1) && map.get(start+i+1)<=0))
                    {
                        return false;
                    }
                    map.put(start+i+1, map.get(start+i+1)-1);
                    if(map.get(start+i+1)<=0)
                    {
                        q.poll();
                    }
                }
                if(map.get(start)>0)
                {
            q.add(start);
                }
        }
        return true;
    }
}
