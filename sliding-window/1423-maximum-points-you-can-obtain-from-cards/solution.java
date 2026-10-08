// 1423. Maximum Points You Can Obtain from Cards
// https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/
// Medium | Java | Accepted 2026-10-07
// Runtime 3 ms | Memory 61.3 MB

class Solution {
    public int maxScore(int[] cardPoints, int k) {
      //Can't use DP as it's too slow since the array can be of length 10^5
      //Use sliding window of size cardPoints.length-k+1
      //This sliding window represents the sum of the inner array (or the possible arrays that result after taking k cards from the beginning/end of the array)
      //Find the maximum sum after calculating the sum across all possible sliding windows
      int[] pref = new int[cardPoints.length+1];
      int tot = 0; //We need to know the total sum of the entire array because we're going to subtract the sliding window's sum from the total sum of the entire array
      for(int i = 0; i<cardPoints.length; i++)
      {
        tot += cardPoints[i]; 
        pref[i+1] = pref[i] + cardPoints[i]; //Create a prefix array for quick summation of array ranges
      }
      int max = 0;
      for(int i = 0; i<=k; i++) //This for loop represents taking 0 cards from the front to k cards from the front
      {
        int end = cardPoints.length-(k-i); //The end then takes whatever cards are remaining after taking i cards from the front
        max = Math.max(tot - (pref[end] - pref[i]), max); //The result after taking i cards from the front and k-i cards from the back is pref[end] - pref[i] (the sum of the resulting array) subtracted from the total sum of the entire array
      }
      return max;
    }
}
