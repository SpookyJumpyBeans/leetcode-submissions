// 1. Two Sum
// https://leetcode.com/problems/two-sum/
// Easy | Java | Accepted 2026-08-12
// Runtime 2 ms | Memory 47.3 MB

//3Sum Intuition
/*
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[][] pairs = new int[nums.length][2];
        for(int i = 0; i<nums.length; i++)
        {
            pairs[i] = new int[]{nums[i], i};
        }
        Arrays.sort(pairs, (a, b) -> Integer.compare(a[0], b[0]));
        int i = 0;
        int j = nums.length-1;
        while(i<j)
        {
            int add = pairs[i][0] + pairs[j][0];
            if(add==target)
            {
                return new int[]{pairs[i][1], pairs[j][1]};
            }
            if(add>target)
            {
                j--;
            }
            if(add<target)
            {
                i++;
            }
        }
        return new int[0];
    }
}
*/
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(); //Make a map that stores the complements of each num in nums
        for(int i = 0; i<nums.length; i++) //Go through nums
        {
            int complement = target - nums[i]; //Find the complement or other number that adds up to target
            if(map.containsKey(complement)) //If this number is in the map, then these are the 2 numbers that add up to target
            { 
                return new int[]{map.get(complement), i}; //Return the array with the complements index and the current index 
            } 
            map.put(nums[i], i); //Add the current num in with its matching index, don't put the complement because this num could be the complement of another number down the road
        }
        return new int[0];
    }
}
