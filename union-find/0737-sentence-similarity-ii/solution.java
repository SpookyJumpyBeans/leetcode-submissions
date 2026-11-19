// 737. Sentence Similarity II
// https://leetcode.com/problems/sentence-similarity-ii/
// Medium | Java | Accepted 2026-11-15
// Runtime 70 ms | Memory 47.8 MB

class Solution {
    public boolean areSentencesSimilarTwo(String[] sentence1, String[] sentence2, List<List<String>> similarPairs) {
        if(sentence1.length!=sentence2.length)
        {
            return false;
        }
        Map<String, List<String>> graph = new HashMap<>();
        for(int i = 0; i<similarPairs.size(); i++)
        {
            List<String> temp = similarPairs.get(i);
            graph.computeIfAbsent(temp.get(0), k -> new ArrayList<>()).add(temp.get(1));
            graph.computeIfAbsent(temp.get(1), k -> new ArrayList<>()).add(temp.get(0)); 
        } 
        //Make an undirected graph from each word to its similar pair
        for(int i = 0; i<sentence1.length; i++)
        {
            if(!check(sentence1[i], sentence2[i], graph)) //Check for every word in sentence 1 and sentence 2 to see if there is a path from one word to the other
            {
                return false;
            }
        }
        return true;
    }
    
    public boolean check(String w1, String w2, Map<String, List<String>> map)
    {
        //Run BFS
        Queue<String> bfs = new ArrayDeque<>();
        bfs.add(w1); //Add the first word to the queue
        Set<String> used = new HashSet<>(); //Keep track of used words to prevent cycles
        used.add(w1);
        while(!bfs.isEmpty())
        {
            String curr = bfs.poll();
            if(curr.equals(w2)) //If we've reached the target word (the second word)
            //Return true
            {
                return true;
            }
            List<String> neigh = map.get(curr); //Else, go through all the unvisited neighbors and add it to the queue
            if(neigh!=null)
            {
                for(int i = 0; i<neigh.size(); i++)
                {
                    if(used.add(neigh.get(i)))
                    {
                        bfs.add(neigh.get(i));
                    }
                }
            }
        }
        return false; //If we never find the target word, return false
    }
}
