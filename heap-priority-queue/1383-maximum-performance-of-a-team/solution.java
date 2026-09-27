// 1383. Maximum Performance of a Team
// https://leetcode.com/problems/maximum-performance-of-a-team/
// Hard | Java | Accepted 2026-09-26
// Runtime 52 ms | Memory 65.9 MB

class Solution {
    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        PriorityQueue<int[]> eff = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));
        //To maximize the efficiencies, use a max heap and sort the efficiencies as well as the index that that efficiency corresponds to
        for(int i = 0; i<n; i++)
        {
            eff.add(new int[]{efficiency[i], i}); 
        }
        long totalSum = 0; //This is the current sum of all the speeds
        long max = 0; //This is the answer 
        PriorityQueue<Integer> sp = new PriorityQueue<>(); //Make a max K heap that contains the k largest speeds
        //INTUITION: Since all the efficiencies are sorted from largest to smallest, to maximize the performance, we need to maximize the sum of the speeds (since the minimum efficiency is already fixed)
        //This is done using the sp min heap
        //Simply see if the totalSum of the top speeds times the current efficiency is larger than the best performance we have stored in max
        while(!eff.isEmpty())
        {
          int[] currEff = eff.poll();
          sp.add(speed[currEff[1]]); //Add to the heap
          totalSum+=speed[currEff[1]]; //Add to the sum of speeds
          if(sp.size()>k) //If the heap is larger than k
          {
            int lowSpeed = sp.poll(); //Poll the smallest speed
            totalSum-=lowSpeed; //Subtract from the sum of speeds
          }
          max = Math.max(totalSum*currEff[0], max); //Check if this efficiency times the current sum of speeds is greater than max
          
        }
        return (int) (max%1000000007);
    }
}
