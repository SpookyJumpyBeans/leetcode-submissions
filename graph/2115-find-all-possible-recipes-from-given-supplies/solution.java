// 2115. Find All Possible Recipes from Given Supplies
// https://leetcode.com/problems/find-all-possible-recipes-from-given-supplies/
// Medium | Java | Accepted 2026-10-16
// Runtime 60 ms | Memory 48 MB

class Solution {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
       Map<String, Integer> needed = new HashMap<>();
       Map<String, List<String>> dag = new HashMap<>();
       //DAG question
       for(int i = 0; i<recipes.length; i++)
       {
            List<String> ing = ingredients.get(i);
            needed.put(recipes[i], ing.size());
            for(int j = 0; j<ing.size(); j++)
            {
                dag.computeIfAbsent(ing.get(j), k -> new ArrayList<>()).add(recipes[i]);
            }
       }
       Queue<String> kahn = new ArrayDeque<>();
       for(int i = 0; i<supplies.length; i++)
       {
        kahn.add(supplies[i]);
       }
       ArrayList<String> ans = new ArrayList<>();
       while(!kahn.isEmpty())
       {
        String curr = kahn.poll();
        List<String> parents = dag.get(curr);
        if(parents==null)
        {
            continue;
        }
        for(int i = 0; i<parents.size(); i++)
        {
            String parent = parents.get(i);
            needed.put(parent, needed.get(parent)-1);
            if(needed.get(parent)==0)
            {
                ans.add(parent);
                needed.remove(parent);
                kahn.add(parent);
            }
        }
       }
       return ans;
    }
}
