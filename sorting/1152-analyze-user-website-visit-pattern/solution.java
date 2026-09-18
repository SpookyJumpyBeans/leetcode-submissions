// 1152. Analyze User Website Visit Pattern
// https://leetcode.com/problems/analyze-user-website-visit-pattern/
// Medium | Java | Accepted on NeetCode 2026-09-18

class Solution {
    public record tuple(int time, String user, String web) {}
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        // Rather brute force solution but is optimal with O(NlogN) sorting and O(U*M^3) generation of combinations.
        // N is the total number of visits (length of the arrays), U is the number of unique users, and M is the maximum number of visits by a single user.
        PriorityQueue<tuple> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.time(), b.time())); //First make a pq that sorts based on timestamp in case timestamps are out of order
        for(int i = 0; i<username.length; i++)
        {
            pq.add(new tuple(timestamp[i], username[i], website[i])); //Make tuples of the three values
        }
        Map<String, List<String>> userVisit = new HashMap<>(); //This map maps websites a user visited to the user
        while(!pq.isEmpty())
        {
            tuple temp = pq.poll();
            userVisit.computeIfAbsent(temp.user(), k -> new ArrayList<>()).add(temp.web());
        }
        TreeMap<String, Set<String>> counts = new TreeMap<>(); //This treemap sorts the keys alphabetically to satisfy the tie breaking condition and it maps patterns to the number of users that have the website access pattern
        for(String user : userVisit.keySet()) //Go through every user's visited websites
        {
            List<String> temp = userVisit.get(user);
            for(int i = 0; i<temp.size()-2; i++) //Since we're looking at every possible subsequence, we want to generate every website subsequence of length 3 with i < j < k
            {
                for(int j = i+1; j<temp.size()-1; j++)
                {
                    for(int k = j+1; k<temp.size(); k++)
                    {
                        StringBuilder ss = new StringBuilder(temp.get(i)); //Create the 3 website subsequence
                        ss.append(" ");
                        ss.append(temp.get(j));
                        ss.append(" ");
                        ss.append(temp.get(k));
                        counts.computeIfAbsent(ss.toString(), l -> new HashSet<>()).add(user); //This adds the user to the set of users that have this webiste visit pattern
                    }
                }
            }
        }            
        int max = 0; //Now we do one pass through the patterns map to find the pattern with the largest set (most users that have the website visit pattern)
        String maxx = "";
        for(String temp : counts.keySet())
        {
            if(counts.get(temp).size()>max)
            {
                max = counts.get(temp).size();
                maxx = temp;
            }
        }
        String[] tempp = maxx.split(" "); //Split the string by spaces since we made the websites separated by spaces
        List<String> ans = new ArrayList<>();
        for(String t : tempp)
        {
            ans.add(t); //Add it to an arraylist
        }
        return ans; //Return
    }
}
