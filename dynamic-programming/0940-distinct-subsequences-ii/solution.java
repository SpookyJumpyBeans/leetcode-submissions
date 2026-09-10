// 940. Distinct Subsequences II
// https://leetcode.com/problems/distinct-subsequences-ii/
// Hard | Java | Accepted 2026-09-09
// Runtime 2 ms | Memory 43.3 MB

class Solution {
    int MOD = 1000000007;
    int[] dp;
    int[] seen;
    String ss;
    public int distinctSubseqII(String s) {
    dp = new int[s.length()+1]; //Set up the DP array
    //This question for memo is different because we want to start at the last index of the string and go inwards towards 0
    //Intuition: 
    //If there is 0 chars in the string, the answer is 0 (But let's make the empty string the base case with 1 possible subsequence: "")
    //Every time we add a new character, we multiply the previous answer by 2
    //Ex: "a" = 1 * 2 = 2 subsequences ("", "a")
    //Since we counted the empty subsequence, we want to subtract 1 from the answer at the end
    //Additionally, if we see a character we've already seen earlier in the string before, we want to subtract all these duplicate subsequences
    //The duplicate subsequences are made by adding the duplicate character to all the subsequences directly before the last time we saw this character 
    //So we want to subtract dp[seen[s.charAt(ind-1)]-1] from dp[ind] if the character at ind was seen before (seen[s.charAt(ind-1)]!=-1) since this subtracts all the duplicate subsequences that were created by appending the same character again
    //Then, set the seen array for this character to the current index since the most recent sighting is currently
    //Anytime we have to subtract, we have to add MOD and then MOD it, since subtraction can go under 0
    dp[0] = 1;
    seen = new int[26];
    Arrays.fill(seen, -1);
    ss = s;
    return (recurse(s.length())-1+MOD)%MOD;
    }

    public int recurse(int ind)
    {
        if(ind==0)
        {
            return 1;
        }
        if(dp[ind]!=0)
        {
            return dp[ind];
        }
        dp[ind] = (2 * recurse(ind-1))%MOD;
        if(seen[ss.charAt(ind-1)-'a']!=-1)
        {
            dp[ind] = (dp[ind] - recurse(seen[ss.charAt(ind-1)-'a']-1)+MOD)%MOD;
        }
        seen[ss.charAt(ind-1)-'a'] = ind;
        return dp[ind];
    }
}
