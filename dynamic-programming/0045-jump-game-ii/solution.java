// 45. Jump Game II
// https://leetcode.com/problems/jump-game-ii/
// Medium | Java | Accepted 2026-01-12
// Runtime 17 ms | Memory 47.6 MB

class Solution {
    public int jump(int[] nums) {
        int ind = 0;
        int count = 0;
        while(ind<nums.length-1)
        {
            int jumpDis = nums[ind];
            int max = 0;
            int newInd = ind;
            count++;
            for(int i = 0; i<=jumpDis; i++)
            {
                if(nums[ind+i]+ind+i>=nums.length-1)
                {
                    if(i==0)
                    {
                        return count;
                    }
                    else
                    {
                        return count+1;
                    }
                }
                if(ind+i<nums.length && nums[ind+i]+ind+i>max)
                {
                    max = nums[ind+i]+ind+i;
                    newInd = ind+i;
                }
            }
            ind = newInd;
            System.out.println(ind);
        }
        return count;
    }
}
