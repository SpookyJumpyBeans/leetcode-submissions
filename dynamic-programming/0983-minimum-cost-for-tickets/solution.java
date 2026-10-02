// 983. Minimum Cost For Tickets
// https://leetcode.com/problems/minimum-cost-for-tickets/
// Medium | Java | Accepted 2026-10-01
// Runtime 0 ms | Memory 43 MB

class Solution {
    int[] dp;
    public int mincostTickets(int[] days, int[] costs) {
        //1D DP, only need the index of the current array (Index i in the DP array represents the minimum cost to travel for all days from this index to the end of the array)
        //We are minimizing the cost so the cost isn't the parameter of the recursion
        dp = new int[days.length];
        Arrays.fill(dp, -1); //First fill the array with -1
        return recurse(days, costs, 0, -1, -1); //Do the recursion
        //We also need to keep track of how many valid days the current ticket covers
        //The currDay and endDay keep track of that 
        //That way we skip days that are covered by the ticket we most recently bought
    }
    
    public int recurse(int[] days, int[] costs, int ind, int currDay, int endDay)
    {
        if(ind==days.length) //If we've reached the end of the array, return 0
        {
            return 0;
        }
        if(days[ind]<=endDay) //If the previous ticket covers the current day, recurse but move the index forward one, keeping the interval of valid days covered by the previous ticket the same
        {
            return recurse(days, costs, ind+1, currDay, endDay);
        }
        if(dp[ind]!=-1) //Return cached value
        {
            return dp[ind];
        }
        int min = Integer.MAX_VALUE;
        int one = costs[0] + recurse(days, costs, ind+1, days[ind], days[ind]); //Recurse with all 3 options, buying a day/week/month pass
        int week = costs[1] + recurse(days, costs, ind+1, days[ind], days[ind]+6);
        int month = costs[2] + recurse(days, costs, ind+1, days[ind], days[ind]+29);
        min = Math.min(min, Math.min(one, Math.min(week, month))); //Find the minimum cost of the 3 options
        return dp[ind] = min; //Return it
    }   
}
