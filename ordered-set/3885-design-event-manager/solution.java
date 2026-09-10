// 3885. Design Event Manager
// https://leetcode.com/problems/design-event-manager/
// Medium | Java | Accepted 2026-09-10
// Runtime 187 ms | Memory 227.7 MB

class EventManager {
    Map<Integer, Integer> map = new HashMap<>(); //This map will hold the current valid eventId and priority
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {  //We write a lambda that ranks events first by highest priority and then by lowest id
        if(a[0]==b[0])
        {
            return Integer.compare(a[1], b[1]);
        }
        return Integer.compare(b[0], a[0]);
    });
    public EventManager(int[][] events) {
        for(int[] e : events) //Add all events to the map and to the pq
        {
            map.put(e[0], e[1]);
            pq.add(new int[]{e[1], e[0]});
        }
    }
    
    public void updatePriority(int eventId, int newPriority) { //When we update a priority, we just set the value to the new priority in the map 
    //We also add this new event/priority pair to the priority queue
        map.put(eventId, newPriority);
        pq.add(new int[]{newPriority, eventId});
    }
    
    public int pollHighest() {
        while(!pq.isEmpty()) //We iterate through the priority queue while it isn't empty
        {
           int[] temp = pq.poll(); //Get the top element
           if(map.containsKey(temp[1]) && map.get(temp[1])==temp[0]) //If this top element's eventId and priority match the most updated entry we have in the map, then we remove the eventId from the map and return the eventId
           {
            map.remove(temp[1]);
            return temp[1];
           }
           //Else we keep polling from the priority queue
        }
        return -1; //If we get here, then it means there's nothing to poll or none of the entries on the pq were up to date
    }
}

/**
 * Your EventManager object will be instantiated and called as such:
 * EventManager obj = new EventManager(events);
 * obj.updatePriority(eventId,newPriority);
 * int param_2 = obj.pollHighest();
 */
