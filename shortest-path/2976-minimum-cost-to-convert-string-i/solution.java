// 2976. Minimum Cost to Convert String I
// https://leetcode.com/problems/minimum-cost-to-convert-string-i/
// Medium | Java | Accepted 2026-09-27
// Runtime 106 ms | Memory 47.9 MB

class Solution {
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        //Used a memoization Dijkstra's approach
        int[][] adj = new int[26][26]; //Since there are only 26 letters, we create an adjacency matrix so we can easily replace values 
        for(int[] r : adj) 
        {
            Arrays.fill(r, Integer.MAX_VALUE); //Set all the values initially to Integer.MAX_VALUE since we're finding the minimum cost between letters
        }
        for(int i = 0; i<original.length; i++)
        {
            int ogChar = original[i] - 'a';
            int changChar = changed[i] - 'a';
            adj[ogChar][changChar] = Math.min(adj[ogChar][changChar], cost[i]); //See if the cost to travel from one char to the other is less than what we have stored in our adjacency matrix
        }
        long totalCost = 0; 
        int[][] cache = new int[26][26]; //Since we are running dijkstra's for every character in the string, we are bound to hit the same source -> target character pair
        //Store these in a cache so we don't have to calculate the same dijkstra result for pairs we've already seen
        for(int[] r : cache) //Initially fill the cache with -1
        {
            Arrays.fill(r, -1);
        }
        for(int j = 0; j<source.length(); j++) //Go through every character in the source string
        {
            int startChar = source.charAt(j)-'a'; //Starting char
            int endChar = target.charAt(j)-'a'; //The char we want to get to
            if(startChar==endChar) //If they're equal, we don't have to do anything, continue
            {
                continue; 
            }
            if(cache[startChar][endChar]!=-1) //If we've already visited this pair, we can just add what's in the cache to our total cost and continue
            {
                totalCost+=cache[startChar][endChar];
                continue;
            }
            int[] dij = new int[26]; //Array to keep track of the minimum cost to get from the start to every letter
            Arrays.fill(dij, Integer.MAX_VALUE); //Fill the array initially with Integer.MAX_VALUE
            PriorityQueue<int[]> dijk = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0])); //Store the cost to get to the current character on the priority queue
            dij[startChar] = 0; //Set the starting char cost to be 0
            dijk.add(new int[]{0, startChar}); //Put this starting char on the pq
            while(!dijk.isEmpty())
            {
                int[] curr = dijk.poll();
                int currChar = curr[1];
                int costt = curr[0];
                if(dij[currChar]<costt) //Run standard dijkstra's
                { 
                    continue;
                }
                if(currChar == endChar) //If we get to the target char
                {
                    cache[startChar][endChar] = costt; //Set this result in the cache
                    totalCost += costt; //Add this to the total cost
                    //We will never have to calculate this again
                    break;
                }
                for(int i = 0; i<26; i++)
                {
                    if(i==currChar)
                    {
                        continue;
                    }
                    if(adj[currChar][i]!=Integer.MAX_VALUE) //If this is a possible vertex to go to
                    {
                        int newCost = costt + adj[currChar][i]; //Get the cost to get to this new char
                        if(newCost<dij[i])
                        {
                            dij[i] = newCost;
                            dijk.add(new int[]{newCost, i}); //Only traverse there if the cost of getting there is smaller than what's stored in our array of minimum costs
                        }
                    }
                }
            }
            if(dij[endChar]==Integer.MAX_VALUE) //If it's not possible to get to the target character, the target character will remain as Integer.MAX_VALUE 
            //Return -1 immediately
            {
                return -1;
            }
        }
        return totalCost; //Return the total cost
    }
}
