// 283. Move Zeroes
// https://leetcode.com/problems/move-zeroes/
// Easy | Java | Accepted 2022-08-07
// Runtime 84 ms | Memory 43.7 MB

class Solution {
    public void moveZeroes(int[] nums) {
        int joe = 0;
        for(int i = 0; i<nums.length-1;i++)
        {
            if(nums[i]==0&&nums[i+1]!=0)
            {
                nums[i] = nums[i+1];
                nums[i+1] = 0;
            }
            else if(nums[i]==0&&nums[i+1]==0)
            {
                joe = i;
                for(int j = i; j<nums.length-1; j++)
                {
                    joe++;
                    if(nums[joe]!=0)
                    {
                        int temp = nums[joe];
                        nums[i] = temp;
                        nums[joe] = 0;
                        break;
                    }
                }
                }
            }
        }
    }
