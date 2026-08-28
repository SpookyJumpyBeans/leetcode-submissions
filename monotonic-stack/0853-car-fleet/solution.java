// 853. Car Fleet
// https://leetcode.com/problems/car-fleet/
// Medium | Java | Accepted 2025-10-28
// Runtime 829 ms | Memory 59.3 MB

class Solution {
    public int carFleet(int target, int[] position, int[] speed) {
        double[][] cars = new double[position.length][2];
        for(int i = 0; i<position.length; i++)
        {
            cars[i][0] = position[i];
            cars[i][1] = (target-position[i])/(double)speed[i];
        }
        Arrays.sort(cars,  (a, b) -> Double.compare(b[0], a[0]));
        double currMax = 0;
        int countFleet = 0;
        for(int j = 0; j<position.length; j++)
        {
            System.out.println(cars[j][1]);
            if(cars[j][1]>currMax)
            {
                currMax = cars[j][1];
                countFleet++;
            }
        }
        return countFleet;
    }
}
