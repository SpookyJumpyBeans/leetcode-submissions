// 3829. Design Ride Sharing System
// https://leetcode.com/problems/design-ride-sharing-system/
// Medium | Java | Accepted 2026-09-10
// Runtime 61 ms | Memory 48.3 MB

class RideSharingSystem {
    Queue<Integer> drivers = new LinkedList<>();
    Queue<Integer> riders = new LinkedList<>();
    Set<Integer> cancelRider = new HashSet<>();
    public RideSharingSystem() {
        
    }
    
    public void addRider(int riderId) {
        riders.add(riderId);
        cancelRider.add(riderId);
    }
    
    public void addDriver(int driverId) {
        drivers.add(driverId);
    }
    
    public int[] matchDriverWithRider() {
        while(!drivers.isEmpty() && !riders.isEmpty())
        {
            if(cancelRider.contains(riders.peek()))
            {
                return new int[]{drivers.poll(), riders.poll()};
            }
            riders.poll();
        }
        return new int[]{-1, -1};
    }
    
    public void cancelRider(int riderId) {
        if(cancelRider.contains(riderId))
        {
            cancelRider.remove(riderId);
        }
    }
}

/**
 * Your RideSharingSystem object will be instantiated and called as such:
 * RideSharingSystem obj = new RideSharingSystem();
 * obj.addRider(riderId);
 * obj.addDriver(driverId);
 * int[] param_3 = obj.matchDriverWithRider();
 * obj.cancelRider(riderId);
 */
