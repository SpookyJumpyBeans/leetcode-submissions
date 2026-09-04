// 698. Partition to K Equal Sum Subsets
// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
// Medium | Java | Accepted 2026-09-03
// Runtime 4 ms | Memory 43 MB

class Solution {
    int groups;
    int[] n;
    boolean ans = false;
    int sum = 0;
    boolean[] visited;
    public boolean canPartitionKSubsets(int[] nums, int k) {
        if(nums.length<k) //If there are more groups than the length of the array, auto false
        {
            return false;
        }
        //Backtracking solution with pruning + early exit
        Arrays.sort(nums); //Sort the array so we are always starting with smaller numbers
        visited = new boolean[nums.length]; //Keep a visited array since we need to start from 0 sometimes and need to not reuse the same indexes
        n = nums;
        groups = k;
        for(int i : nums)
        {
            sum+=i;
        } 
        //First calculate the sum of the entire array
        //If this sum isn't divisible by k, then auto false
        if(sum%k!=0)
        {
        return false;
        }
        sum/=k; //We recurse based on the sum that we can split the sum into k groups 
        recurse(0, 0, 0); //Start with a count of 0, 0 groups, and a starting index of 0
        return ans;
    }

    public void recurse(int count, int group, int ind)
    {
        if(group==groups) //If the number of groups equals k, then set the ans to true and return
        {
            ans = true;
            return;
        }
        for(int i = ind; i<n.length; i++) //We start the for loop at ind (ind is either 0 or i+1)
        {
            if(ans) //The second a branch causes ans to be true, return immediately
            {
                return;
            }
            if(visited[i]) //If the index is already used, continue
            {
                continue;
            }
            if(count+n[i]>sum) //If adding the number at index i causes the sum to be greater than the target sum, return immediately
            {
                return;
            }
            count+=n[i];
            if(count==sum) //If adding the number at i equates the sum, then recurse 
            {
                visited[i] = true;
                recurse(0, group+1, 0);
                visited[i] = false;
            }
            else
            {
                visited[i] = true;
                recurse(count, group, i+1);
                visited[i] = false;
            }
            count-=n[i];
            if(count==0)
            {
                return;
            }
        }  
    }
    // 1 2 2 3 3 4 5
}
