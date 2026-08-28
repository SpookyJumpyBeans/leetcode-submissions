// 1603. Design Parking System
// https://leetcode.com/problems/design-parking-system/
// Easy | Java | Accepted 2026-08-20
// Runtime 8 ms | Memory 46.4 MB

class ParkingSystem {
    int[] space = new int[4];

    public ParkingSystem(int big, int medium, int small) {
        space[1] = big;
        space[2] = medium;
        space[3] = small;
    }
    
    public boolean addCar(int carType) {
        if(space[carType]<=0)
        {
            return false;
        }
        space[carType]--;
        return true;
    }
}

/**
 * Your ParkingSystem object will be instantiated and called as such:
 * ParkingSystem obj = new ParkingSystem(big, medium, small);
 * boolean param_1 = obj.addCar(carType);
 */
