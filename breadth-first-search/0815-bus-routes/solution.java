// 815. Bus Routes
// https://leetcode.com/problems/bus-routes/
// Hard | Java | Accepted 2026-10-12
// Runtime 36 ms | Memory 104.2 MB

class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        // Base case: If we are already at the destination, it takes 0 buses.
        if (source == target) {
            return 0;
        }
        
        // Maps a specific bus stop to a list of bus IDs (the indices in the routes array) that visit it.
        Map<Integer, ArrayList<Integer>> stopToBusesMap = new HashMap<>(); 
        
        for (int busId = 0; busId < routes.length; busId++) {
            int[] stopsOnBus = routes[busId];
            for (int j = 0; j < stopsOnBus.length; j++) {
                // For every stop this bus visits, add this busId to the stop's list
                stopToBusesMap.computeIfAbsent(stopsOnBus[j], k -> new ArrayList<>()).add(busId);
            }
        }
        
        // The queue holds the physical bus stops we need to evaluate.
        Queue<Integer> queue = new ArrayDeque<>(); 
        
        // Tracks which buses we have already ridden so we don't get on them again.
        boolean[] visitedBuses = new boolean[routes.length];
        
        // Tracks which physical stops we have already evaluated so we don't loop back to them.
        Set<Integer> visitedStops = new HashSet<>();
        
        // Initialize our BFS with the starting location
        visitedStops.add(source);
        queue.add(source);
        
        int busesTaken = 0; 
        
        while (!queue.isEmpty()) {
           // Take a snapshot of all stops we can currently reach. 
           // Everything in this 'wave' costs the same number of buses.
           int levelSize = queue.size(); 
           busesTaken++;
           
           for (int i = 0; i < levelSize; i++) {
                int currentStop = queue.poll();
                ArrayList<Integer> busesAtCurrentStop = stopToBusesMap.get(currentStop);
                
                // If this stop isn't mapped to any buses, skip it
                if (busesAtCurrentStop == null) {
                    continue;
                }
                
                // Look at every bus you can catch from this physical stop
                for (int j = 0; j < busesAtCurrentStop.size(); j++) {
                    int nextBusId = busesAtCurrentStop.get(j);
                    
                    // If we've already evaluated this bus route, skip it to prevent infinite loops
                    if (visitedBuses[nextBusId]) {
                        continue;
                    }
                    
                    // Mark the bus as taken
                    visitedBuses[nextBusId] = true;
                    
                    // Look at every stop this new bus visits
                    for (int nextStop : routes[nextBusId]) {
                        if (nextStop == target) {
                            return busesTaken; // Target found, return the current wave count
                        }
                        
                        // If we haven't visited this stop before, add it to the queue for the next wave
                        if (visitedStops.add(nextStop)) {
                            queue.add(nextStop);
                        }
                    }
                }
           }
        }
        
        // If the queue empties and we never found the target, it's unreachable
        return -1;
    }
}
