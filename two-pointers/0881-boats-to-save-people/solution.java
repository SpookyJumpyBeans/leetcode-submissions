// 881. Boats to Save People
// https://leetcode.com/problems/boats-to-save-people/
// Medium | Java | Accepted 2026-08-24
// Runtime 806 ms | Memory 58.2 MB

class Solution {
    public int numRescueBoats(int[] people, int limit) {
        //Two pointer question
        //Pair the lightest and heaviest people together if possible
        //If the lightest and heaviest pairing is over the limit
        //Decrement the second pointer
        //The second a pairing is under the limit, then that's the optimal heaviest and lightest pairing possible so immediately finish exploring
        //If a pairing never manages to get under the limit, then the two pointers will eventually equal each other (the lightest person), so just keep a tracker to detect if 
        List<Integer> p = new ArrayList<>();
        for(int person : people)
        {
            p.add(person);
        }
        Collections.sort(p);
        int count = 0;
        while(p.size()>0)
        {
            int p1 = 0;
            int p2 = p.size()-1;
            while(p1<p2)
            {
                if(p.get(p1)+p.get(p2)>limit)
                {
                    p2--;
                }
                else if(p.get(p1)+p.get(p2)<=limit)
                {
                    count++;
                    p.remove(p1);
                    p.remove(p2-1);
                    break;
                }
            }
            if(p1==p2)
            {
                p.remove(p1);
                count++;
            }
        }
        return count;
    }
}
