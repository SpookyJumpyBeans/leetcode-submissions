// 982. Triples with Bitwise AND Equal To Zero
// https://leetcode.com/problems/triples-with-bitwise-and-equal-to-zero/
// Hard | Java | Accepted 2026-07-16
// Runtime 282 ms | Memory 50.8 MB

class Solution {
    public int countTriplets(int[] nums) {
    Map<Integer, Integer> map = new HashMap<>();
    for(int i = 0; i<nums.length; i++)
    {
        for(int j = 0; j<nums.length; j++)
        {
            int temp = nums[i] & nums[j];
            map.put(temp, map.getOrDefault(temp, 0)+1);
        }
    }
    System.out.println(map);
    int sum = 0;
    for(int t : map.keySet())
    {
        for(int k = 0; k<nums.length; k++)
        {
            int temp2 = t&nums[k];
            if(temp2==0)
            {
                sum+=map.get(t);
            }
        }
    }
    return sum;
}
}
