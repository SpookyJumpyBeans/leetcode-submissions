// 332. Reconstruct Itinerary
// https://leetcode.com/problems/reconstruct-itinerary/
// Hard | Java | Accepted 2026-01-07
// Runtime 5 ms | Memory 47.3 MB

class Solution {
    Map<String, PriorityQueue<String>> map = new HashMap<>();
    List<String> ans = new ArrayList<>();
    public List<String> findItinerary(List<List<String>> tickets) {
        for(int i = 0; i<tickets.size(); i++)
        {
            List<String> temp = tickets.get(i);
            if(!map.containsKey(temp.get(0)))
            {
                map.put(temp.get(0), new PriorityQueue<>());
            }
            map.get(temp.get(0)).add(temp.get(1));
        }
        List<String> b = new ArrayList<>();
        b.add("JFK");
        recurse("JFK");
        return ans.reversed();
    }

    public void recurse(String node)   
    {
        PriorityQueue<String> t = map.get(node);
        while(t!=null && !t.isEmpty())
        {
            String temp = t.poll();
            recurse(temp);
        }
        ans.add(node);
    }
}
