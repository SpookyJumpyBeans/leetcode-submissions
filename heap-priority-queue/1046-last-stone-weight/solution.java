// 1046. Last Stone Weight
// https://leetcode.com/problems/last-stone-weight/
// Easy | Java | Accepted 2025-11-29
// Runtime 1 ms | Memory 43 MB

class Solution {
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());
        for(int i = 0; i<stones.length; i++)
        {
            max.add(stones[i]);
        }

        while(max.size()>1)
        {
 int stone1 = max.remove();
            int stone2 = max.remove();
            if(stone1==stone2)
            {
                continue;
            }
            if(stone1!=stone2)
            {
                max.add(Math.abs(stone1-stone2));
            }
        }
        if(max.size()==0)
        {
            return 0;
        }
        return max.peek();
    }
}
